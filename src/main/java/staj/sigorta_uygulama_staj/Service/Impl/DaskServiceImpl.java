package staj.sigorta_uygulama_staj.Service.Impl;

import org.springframework.stereotype.Service;
import staj.sigorta_uygulama_staj.DTO.Request.CreateDaskPolicyRequest;
import staj.sigorta_uygulama_staj.Entities.*;
import staj.sigorta_uygulama_staj.Repository.HomeRepository;
import staj.sigorta_uygulama_staj.Repository.PolicyRepository;
import staj.sigorta_uygulama_staj.Service.CustomerService;
import staj.sigorta_uygulama_staj.Service.DaskService;
import staj.sigorta_uygulama_staj.Service.UserService;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class DaskServiceImpl implements DaskService {

    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private final PolicyRepository policyRepository;
    private final HomeRepository homeRepository;
    private final UserService userService;
    private final CustomerService customerService;

    public DaskServiceImpl(PolicyRepository policyRepository, HomeRepository homeRepository,
                           UserService userService, CustomerService customerService) {
        this.policyRepository = policyRepository;
        this.homeRepository = homeRepository;
        this.userService = userService;
        this.customerService = customerService;
    }

    @Override
    public Policy CalculateOfferPolicy(CreateDaskPolicyRequest createDaskPolicyRequest) {

        Users user = this.userService.getByUserId(createDaskPolicyRequest.getUserId());
        Customer customer = this.customerService.getById(createDaskPolicyRequest.getCustomerId());

        Home home = new Home();
        Policy policy = new Policy();
        policy.setPolicyNumber(generateUniquePolicyNumber());
        policy.setUser(user);
        policy.setCustomer(customer);
        policy.setCustomerNumber(customer.getCustomerNumber());
        policy.setBranch_code("199");
        policy.setStatus("T");
        policy.setRemainderTime(15);
        policy.setTanzim_date(LocalDate.now());
        policy.setFinish_date(calculateFinishDate(LocalDate.now()));
        policy.setPrim(calculateInsuranceValue(
                createDaskPolicyRequest.getSquareMeter()
                ,createDaskPolicyRequest.getFloorNumber()
                ,createDaskPolicyRequest.getNumberBuildFloor()
                ,createDaskPolicyRequest.getDamageState()
                ,createDaskPolicyRequest.getBuildingAge()
        ));
        home.setAddressCode(createDaskPolicyRequest.getAddressCode());
        home.setFloorNumber(createDaskPolicyRequest.getFloorNumber());
        home.setSquareMeter(createDaskPolicyRequest.getSquareMeter());
        home.setNumberBuildFloor(createDaskPolicyRequest.getNumberBuildFloor());
        home.setDamageState(createDaskPolicyRequest.getDamageState());
        home.setBuildingAge(createDaskPolicyRequest.getBuildingAge());
        home.setBuildStyle(createDaskPolicyRequest.getBuildStyle());
        home.setPolicyNumber(policy.getPolicyNumber());

        this.homeRepository.save(home);
        this.policyRepository.save(policy);
        startCounterUpdate(policy.getRemainderTime(), policy.getId());
        return policy;

    }
    private LocalDate calculateFinishDate(LocalDate startDate) {
        return startDate.plusDays(15);
    }

    private Integer generateUniquePolicyNumber() {
        Random random = new Random();
        int policy_number;
        boolean exists;

        do {
            policy_number = 10000000 + random.nextInt(90000000); // 8 haneli rastgele sayı oluştur
            exists = policyRepository.existsByPolicyNumber(policy_number); // Mevcut olup olmadığını kontrol et
        } while (exists);

        return policy_number;
    }

    public Double calculateInsuranceValue(Double squareMeter, int floorNumber,int numberBuildFloor,String damageState, int buildingAge){

        Double baseValue = squareMeter * 27; // Metrekare başına bir değer

        // Kat numarasına göre düzenleme
        if (floorNumber <= 2) {
            baseValue += 500; // Alt katlar daha riskli
        } else if (floorNumber > 5 && floorNumber <10) {
            baseValue += 300; // Üst katlar daha az riskli
        } else if (floorNumber> 15) {
            baseValue +=100;
        }

        // Bina kat sayısına göre düzenleme
        if (numberBuildFloor < 5) {
            baseValue += 500; // Yüksek binalar daha riskli
        } else if (numberBuildFloor > 5 && numberBuildFloor < 10 ) {
            baseValue += 750;
        } else if (numberBuildFloor > 15) {
            baseValue += 1000;
        }

        // Hasar durumuna göre düzenleme
        if (damageState.equalsIgnoreCase("Hasarsız")) {
            baseValue *= 1.1; // Hasarlı binalar için bedel %50 artar
        } else if (damageState.equalsIgnoreCase("Az Hasarlı")) {
            baseValue *= 1.3; // Hasarsız binalar için bedel %10 düşer
        }
        else if (damageState.equalsIgnoreCase("Orta Hasarlı")) {
            baseValue *= 1.5; // Hasarsız binalar için bedel %10 düşer
        }
        else if (damageState.equalsIgnoreCase("Ağır Hasarlı")) {
            baseValue *= 1.7; // Hasarsız binalar için bedel %10 düşer
        }

        // Yapılış yılına göre düzenleme
        baseValue =baseValue + buildingAge*1.2;

        // Son değer
        return baseValue;


    }
    //burada kullanılan metotlar oluşturulan teklifin poliçeleştirilmediği durumda 15 gün sonra silinmesini sağlıyor

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
                        deleteDaskPolicy(policyId);
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

    @Override
    public void deleteDaskPolicy(long id) {
        this.policyRepository.deleteById(id);
    }
}
