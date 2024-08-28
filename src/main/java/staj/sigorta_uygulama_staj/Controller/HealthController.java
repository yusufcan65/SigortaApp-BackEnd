package staj.sigorta_uygulama_staj.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import staj.sigorta_uygulama_staj.DTO.Request.CreateHealthPolicyRequest;
import staj.sigorta_uygulama_staj.DTO.Response.PolicyResponse;
import staj.sigorta_uygulama_staj.Entities.Policy;
import staj.sigorta_uygulama_staj.Service.HealthService;

import java.util.List;


@RestController
@RequestMapping("/health")
public class HealthController {
        private final HealthService healthService;

        public HealthController( HealthService healthService){
            this.healthService = healthService;
        }



    @PostMapping("/calculate_offer")
    public ResponseEntity<Policy> calculateOffer(@RequestBody CreateHealthPolicyRequest createHealthPolicyRequest) {

        Policy policy = healthService.CalculateOfferPolicy(createHealthPolicyRequest);

        return ResponseEntity.ok(policy);
    }




    @GetMapping
    public List<PolicyResponse> getAll(){return this.healthService.getAll();}

}
