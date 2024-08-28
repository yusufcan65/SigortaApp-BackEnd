package staj.sigorta_uygulama_staj.Service.Impl;

import org.springframework.stereotype.Service;
import staj.sigorta_uygulama_staj.DTO.Request.CreateTrafficPolicyRequest;
import staj.sigorta_uygulama_staj.DTO.Response.PolicyResponse;
import staj.sigorta_uygulama_staj.Entities.*;
import staj.sigorta_uygulama_staj.Repository.CarRepository;
import staj.sigorta_uygulama_staj.Repository.PoliciedCarsRepository;
import staj.sigorta_uygulama_staj.Repository.PolicyRepository;
import staj.sigorta_uygulama_staj.Service.CustomerService;
import staj.sigorta_uygulama_staj.Service.TrafficService;
import staj.sigorta_uygulama_staj.Service.UserService;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class TrafficServiceImpl implements TrafficService {

    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final PolicyRepository policyRepository;
    private final CarRepository carRepository;
    private final PoliciedCarsRepository policiedCarsRepository;
    private final CustomerService customerService;
    private final UserService userService;
    public TrafficServiceImpl(PolicyRepository policyRepository, CarRepository carRepository,
                              CustomerService customerService,UserService userService,
                              PoliciedCarsRepository policiedCarsRepository){
        this.policyRepository=policyRepository;
        this.carRepository = carRepository;
        this.customerService = customerService;
        this.userService = userService;
        this.policiedCarsRepository = policiedCarsRepository;
    }

    //ortak metot
    @Override
    public Policy CalculateOfferPolicy(CreateTrafficPolicyRequest createTrafficPolicyRequest) {

            Users user = this.userService.getByUserId(createTrafficPolicyRequest.getUserId());
            Customer customer = this.customerService.getById((createTrafficPolicyRequest.getCustomerId()));
            Cars car = createTrafficPolicyRequest.getCar();
            System.out.println("araç yok");
            if (car.getId() != null) {
                System.out.println("araç yok gelemdi");
                car = this.carRepository.findById(car.getId())
                        .orElseThrow(() -> new RuntimeException("Car not found"));
            }
            PoliciedCars policiedCars = new PoliciedCars();

                Policy policy = new Policy();
                policy.setCustomerNumber(customer.getCustomerNumber());
                policy.setUser(user);
                policy.setCustomer(customer);
                policy.setBranch_code("310");
                policy.setStatus("T");
                policy.setTanzim_date(LocalDate.now());
                policy.setFinish_date(calculateFinishDate(LocalDate.now()));
                policy.setPolicyNumber(generateUniquePolicyNumber());
                policy.setPrim(calculateInsuranceValue(car.getInsuranceValue()));
                policy.setRemainderTime(15);

                policiedCars.setPlateCode(createTrafficPolicyRequest.getPlateCode());
                policiedCars.setPlateCityCode(createTrafficPolicyRequest.getPlateCityCode());
                policiedCars.setEngineNumber(createTrafficPolicyRequest.getEngineNumber());
                policiedCars.setFrameNumber(createTrafficPolicyRequest.getFrameNumber());

                policiedCars.setPolicy_number(policy.getPolicyNumber());
                policiedCars.setCarId(car.getId());

                this.policiedCarsRepository.save(policiedCars);

                this.policyRepository.save(policy);
                startCounterUpdate(policy.getRemainderTime(), policy.getId());

                return policy;

    }
    // bu metot gelen aracın değerini hesaplayıp bana bir değer döndürecek kabul edilirse teklif olaark kayıt edilecek
    @Override
    public Double calculateInsuranceValue(Double value){
        value = value * 0.0117;
        return value;
    }



    private LocalDate calculateFinishDate(LocalDate startDate) {
        return startDate.plusDays(15);
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


    public List<PolicyResponse> getAll() {
        List<Policy> policyList = this.policyRepository.findAll();
        return policyList.stream().map(policy -> new PolicyResponse(policy)).collect(Collectors.toList());
    }

    // ortak metot
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

    public Policy getByPolicyId(Long id){
         return policyRepository.findById(id).orElseThrow();
    }

    @Override
    public List<PolicyResponse> getByOfferList() {
        List<Policy> policyList = policyRepository.findByStatus("T");

        List<PolicyResponse> policyResponses = policyList.stream().map(policy -> new PolicyResponse(policy)).collect(Collectors.toList());

        return policyResponses;
    }

    @Override
    public List<PolicyResponse> getByPolicyList() {
        List<Policy> policyList = policyRepository.findByStatus("P");

        List<PolicyResponse> policyResponses = policyList.stream().map(policy -> new PolicyResponse(policy)).collect(Collectors.toList());

        return policyResponses;
    }

    @Override
    public List<PolicyResponse> getAllByCustomerId(long customer_id){

        List<Policy> policy = policyRepository.findByCustomerId(customer_id);

        List<PolicyResponse> policyResponses = policy.stream().map(policy1 -> new PolicyResponse(policy1)).collect(Collectors.toList());

        return policyResponses;
    }


    //araç listelemek için kullanılan bazı metotlar burada yer alıyor
    public List<String> getBrands(){
        return carRepository.findDistinctBrands();
    }

    public List<String> getModelsByBrand(String brand){
        return carRepository.findByBrand(brand);
    }

    public List<Integer> getModelYearsByBrandAndModel(String brand ,String model){
        return carRepository.findDistinctModelYearByBrandAndModel(brand,model);
    }

    public Cars getIdByBrandModelAndModelYear(String brand, String model, Integer modelYear){

        return carRepository.findByBrandAndModelAndModelYear(brand,model,modelYear);

    }





}
