package staj.sigorta_uygulama_staj.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import staj.sigorta_uygulama_staj.DTO.Request.CreateDaskPolicyRequest;
import staj.sigorta_uygulama_staj.Entities.Policy;
import staj.sigorta_uygulama_staj.Service.DaskService;


@RestController
@RequestMapping("/dask")
public class DaskController {
        private final DaskService daskService;


        public DaskController( DaskService daskService){
            this.daskService = daskService;
        }



        @PostMapping("/calculate_offer")
        public ResponseEntity<Policy> calculateOffer(@RequestBody CreateDaskPolicyRequest createDaskPolicyRequest) {

            Policy policy = daskService.CalculateOfferPolicy(createDaskPolicyRequest);
            return ResponseEntity.ok(policy);
        }

}
