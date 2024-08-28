package staj.sigorta_uygulama_staj.Controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import staj.sigorta_uygulama_staj.DTO.Request.CreateTrafficPolicyRequest;
import staj.sigorta_uygulama_staj.DTO.Response.PolicyResponse;
import staj.sigorta_uygulama_staj.Entities.Cars;
import staj.sigorta_uygulama_staj.Entities.Policy;
import staj.sigorta_uygulama_staj.Service.TrafficService;

import java.util.List;

@RestController
@RequestMapping("/traffic")
public class TrafficController {

    private final TrafficService trafficService;


    public TrafficController(TrafficService trafficService){
        this.trafficService = trafficService;
    }


    @PostMapping("/calculate_offer")
    public ResponseEntity<Policy> calculateOffer(@RequestBody CreateTrafficPolicyRequest createPolicyRequest) {

        Policy policy = trafficService.CalculateOfferPolicy(createPolicyRequest);

        return ResponseEntity.ok(policy);
    }

    @PostMapping("/calculatePrim/{value}")
    public Double CalculatePrim(@PathVariable Double value){
        return this.trafficService.calculateInsuranceValue(value);
    }



    @GetMapping
    public List<PolicyResponse> getAll(){
        return this.trafficService.getAll();
    }

    @GetMapping("/getlistbycustomerid/{customerId}")
    public List<PolicyResponse> getListByCustomerId(@PathVariable long customerId){
        return trafficService.getAllByCustomerId(customerId);
    }

    @GetMapping("/ListOfOffer")
    public List<PolicyResponse> getByOffer(){
        return trafficService.getByOfferList();
    }
    @GetMapping("/ListOfPolicy")
    public List<PolicyResponse> getByPolicy(){
        return trafficService.getByPolicyList();
    }

    //araç verilerini çekmek için kullanılan metotlar burada frontende gönderiliyor
    @GetMapping("/brands")
    public ResponseEntity<List<String>> getBrands(){
       List<String> brands = trafficService.getBrands();
       return ResponseEntity.ok(brands);
    }

    @GetMapping("models/{brand}")
    public ResponseEntity<List<String>> getModelsByBrand(@PathVariable String brand){
       List<String> models = trafficService.getModelsByBrand(brand);
       return ResponseEntity.ok(models);
    }

    @GetMapping("/modelYears/{brand}/{model}")
    public ResponseEntity<List<Integer>> getModelYearsByBrandAndModel(@PathVariable String brand, @PathVariable String model){
       List<Integer> modelYears = trafficService.getModelYearsByBrandAndModel(brand,model);
       return ResponseEntity.ok(modelYears);
   }
    @GetMapping("/car/{brand}/{model}/{modelYear}")
    public Cars getByBrandModelAndModelYear(@PathVariable String brand, @PathVariable String model, @PathVariable Integer modelYear){
       return trafficService.getIdByBrandModelAndModelYear(brand,model,modelYear);
   }



}
