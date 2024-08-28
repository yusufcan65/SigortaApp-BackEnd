package staj.sigorta_uygulama_staj.Service.Impl;

import org.springframework.stereotype.Service;
import staj.sigorta_uygulama_staj.DTO.Request.CreateHealthPolicyRequest;
import staj.sigorta_uygulama_staj.DTO.Response.PolicyResponse;
import staj.sigorta_uygulama_staj.Entities.Customer;
import staj.sigorta_uygulama_staj.Entities.Health;
import staj.sigorta_uygulama_staj.Entities.Policy;
import staj.sigorta_uygulama_staj.Entities.Users;
import staj.sigorta_uygulama_staj.Repository.HealthRepository;
import staj.sigorta_uygulama_staj.Repository.PolicyRepository;
import staj.sigorta_uygulama_staj.Service.CustomerService;
import staj.sigorta_uygulama_staj.Service.HealthService;
import staj.sigorta_uygulama_staj.Service.UserService;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class HealthServiceImpl implements HealthService {

    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final PolicyRepository policyRepository;
    private final HealthRepository healthRepository;
    private final CustomerService customerService;
    private final UserService userService;
    public HealthServiceImpl(PolicyRepository policyRepository, HealthRepository healthRepository,
                             CustomerService customerService, UserService userService){
        this.policyRepository=policyRepository;
        this.healthRepository = healthRepository;
        this.customerService = customerService;
        this.userService = userService;
    }

    //ortak metot  Teklif oluşturan metot
    @Override
    public Policy CalculateOfferPolicy(CreateHealthPolicyRequest createHealthPolicyRequest) {

        Users user = this.userService.getByUserId(createHealthPolicyRequest.getUserId());
        Customer customer = this.customerService.getById(createHealthPolicyRequest.getCustomerId());
        Health health = new Health();

        Policy policy = new Policy();

        policy.setBranch_code("610");
        policy.setCustomer(customer);
        policy.setUser(user);
        policy.setStatus("T");
        policy.setTanzim_date(LocalDate.now());
        policy.setFinish_date(calculateFinishDate(LocalDate.now()));
        policy.setPolicyNumber(generateUniquePolicyNumber());
        policy.setCustomerNumber(customer.getCustomerNumber());
        policy.setRemainderTime(15);

        policy.setPrim(calculateInsuranceValue(customer.getBirth_date(),
                createHealthPolicyRequest.getSmokeStatus(),
                createHealthPolicyRequest.getSporStatus(),
                createHealthPolicyRequest.getOperationStatus(),
                createHealthPolicyRequest.getChronicDiseaseStatus()));
        // oluşan health nesnesi verileri

        //health.setCustomer(customer);
        System.out.println("customer date"+customer.getBirth_date());

        int age = calculateAgeCustomer(customer.getBirth_date());
        System.out.println("Calculated Age: " + age); // Debug ifadesi
        health.setAge(age);

        health.setSporStatus(createHealthPolicyRequest.getSporStatus());
        health.setOperationStatus(createHealthPolicyRequest.getOperationStatus());
        health.setSmokeStatus(createHealthPolicyRequest.getSmokeStatus());
        health.setChronicDiseaseStatus(createHealthPolicyRequest.getChronicDiseaseStatus());
        health.setPolicy_number(policy.getPolicyNumber());

        System.out.println("Health Entity: " + health); // Debug ifadesi
        this.policyRepository.save(policy);
        this.healthRepository.save(health);
        startCounterUpdate(policy.getRemainderTime(), policy.getId());

        return policy;

    }

    //doğum gününe göre yaş hesaplayan metot
    public int calculateAgeCustomer(LocalDate birthDate){
        LocalDate currentDate = LocalDate.now();

        // Doğrudan yaşı hesaplar
        Period period = Period.between(birthDate, currentDate);

        int age = period.getYears();

        return age;
    }

    public List<PolicyResponse> getAll() {
        List<Policy> policyList = this.policyRepository.findAll();
        return policyList.stream().map(policy -> new PolicyResponse(policy)).collect(Collectors.toList());
    }


    @Override
    public double calculateInsuranceValue(LocalDate birthDate, String smokeStatus, String sporStatus, String operationStatus, String chronicDiseaseStatus){

        int age = calculateAgeCustomer(birthDate);
        Double primValue = 1000.0;

        // Yaş faktörü
        if (age < 30) {
            primValue *= 1.1;
        } else if (age < 50) {
            primValue *= 1.3;
        } else {
            primValue *= 1.5;
        }

        // Sigara içme durumu
        if ("evet".equalsIgnoreCase(smokeStatus)) {
            primValue *= 1.7;
        } else {
            primValue *= 1.1;
        }

        // Spor yapma durumu
        if ("hayır".equalsIgnoreCase(sporStatus)) {
            primValue *= 1.3;
        } else {
            primValue *= 0.95;
        }

        // Geçmiş operasyon durumu
        if ("evet".equalsIgnoreCase(operationStatus)) {
            primValue *= 1.5;
        } else {
            primValue *= 1.05;
        }

        // Kronik hastalık durumu
        if ("evet".equalsIgnoreCase(chronicDiseaseStatus)) {
            primValue *= 1.8;
        } else {
            primValue *= 1.063;
        }

        return primValue;
    }

    // ortak metot poliçe numarası oluşturan metot
    public Integer generateUniquePolicyNumber() {
        Random random = new Random();
        int policy_number;
        boolean exists;

        do {
            policy_number = 10000000 + random.nextInt(90000000); // 8 haneli rastgele sayı oluştur
            exists = policyRepository.existsByPolicyNumber(policy_number); // Mevcut olup olmadığını kontrol et
        } while (exists);

        return policy_number;
    }

    //ortak metot Teklifin bitiş tarihini hesaplayan metot
    private LocalDate calculateFinishDate(LocalDate startDate) {

        return startDate.plusDays(15);
    }


    public Optional<Health> getByHealth(long health_id){
        return healthRepository.findById(health_id);
    }

    public Policy getByPolicyId(Long id){
         return policyRepository.findById(id).orElseThrow();
    }


    public void startCounterUpdate(int counter, long policyId) {
        Runnable updateTask = new Runnable() {
            private int localCounter = counter;

            @Override
            public void run() {
                if (localCounter > 0) {
                    localCounter--;
                    // Veritabanında counter'ı güncelle
                    updateCounterInDatabase(localCounter, policyId);

                    if (localCounter == 0) {
                        policyRepository.deleteById(policyId);
                        scheduler.shutdown(); // İş tamamlandığında scheduler'ı durdurun
                    }
                }
            }
        };

        // Her gün (24 saat) bir kere çalışacak şekilde zamanlayın
        scheduler.scheduleAtFixedRate(updateTask, 0, 1, TimeUnit.DAYS);
    }

    private void updateCounterInDatabase(int counter, long policyId) {
        // Veritabanında counter'ı güncelle
        Policy policy = this.policyRepository.findById(policyId).orElseThrow();
        policy.setRemainderTime(counter);
        this.policyRepository.save(policy);
    }


}
