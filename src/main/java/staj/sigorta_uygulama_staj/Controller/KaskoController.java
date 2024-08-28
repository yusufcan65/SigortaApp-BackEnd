package staj.sigorta_uygulama_staj.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import staj.sigorta_uygulama_staj.DTO.Request.CreateKaskoPolicyRequest;
import staj.sigorta_uygulama_staj.DTO.Response.PolicyResponse;
import staj.sigorta_uygulama_staj.Entities.Cars;
import staj.sigorta_uygulama_staj.Entities.Policy;
import staj.sigorta_uygulama_staj.Service.KaskoService;

import java.util.List;


@RestController
@RequestMapping("/kasko")
public class KaskoController {
        private final KaskoService kaskoService;


        public KaskoController(KaskoService kaskoService){
            this.kaskoService = kaskoService;
        }



       @PostMapping("/calculate_offer")
       public ResponseEntity<Policy> calculateOffer(@RequestBody CreateKaskoPolicyRequest createKaskoPolicyRequest) {

           Policy policy = kaskoService.CalculateOfferPolicy(createKaskoPolicyRequest);

           return ResponseEntity.ok(policy);
       }

       @GetMapping
        public List<PolicyResponse> getAll(){
            return this.kaskoService.getAll();
        }


        @GetMapping("/getlistbycustomerid/{customer_id}")
        public List<PolicyResponse> getListByCustomerId(@PathVariable long customer_id){
            return kaskoService.getAllByCustomerId(customer_id);
        }

        @GetMapping("/ListOfOffer")
        public List<PolicyResponse> getByOffer(){
            return kaskoService.getByOfferList();
        }
        @GetMapping("/ListOfPolicy")
        public List<PolicyResponse> getByPolicy(){
            return kaskoService.getByPolicyList();
        }

        // araç bilgilerini dbden çekmek içinn derekli metotlar

        @GetMapping("/brands")
        public ResponseEntity<List<String>> getBrands(){
            List<String> brands = kaskoService.getBrands();
            return ResponseEntity.ok(brands);
        }

        @GetMapping("models/{brand}")
        public ResponseEntity<List<String>> getModelsByBrand(@PathVariable String brand){
            List<String> models = kaskoService.getModelsByBrand(brand);
            return ResponseEntity.ok(models);
        }

        @GetMapping("/modelYears/{brand}/{model}")
        public ResponseEntity<List<Integer>> getModelYearsByBrandAndModel(@PathVariable String brand, @PathVariable String model){
            List<Integer> modelYears = kaskoService.getModelYearsByBrandAndModel(brand,model);
            return ResponseEntity.ok(modelYears);
        }
        @GetMapping("/car/{brand}/{model}/{modelYear}")
        public Cars getByBrandModelAndModelYear(@PathVariable String brand, @PathVariable String model, @PathVariable Integer modelYear){
            return kaskoService.getIdByBrandModelAndModelYear(brand,model,modelYear);
        }
}
