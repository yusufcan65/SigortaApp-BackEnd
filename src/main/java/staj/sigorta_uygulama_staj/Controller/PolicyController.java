package staj.sigorta_uygulama_staj.Controller;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import staj.sigorta_uygulama_staj.DTO.Response.PolicyResponse;
import staj.sigorta_uygulama_staj.Service.PolicyService;

import java.util.List;

@RestController
@RequestMapping("/policy")

public class PolicyController {

    private final PolicyService policyService;

    public PolicyController(PolicyService policyService) {
        this.policyService = policyService;
    }

    @GetMapping()
    public List<PolicyResponse> getAll() {
        return policyService.getAll();
    }

    // müşteriye ait poliçeleri listeleyen metot
    @GetMapping("/listById/{customerId}")
    public List<PolicyResponse> getListByCustomerId(@PathVariable long customerId) {
        return policyService.getAllByCustomerId(customerId);
    }

    //sadece teklifleri listeleyen metot
    @GetMapping("/ListOffer")
    public List<PolicyResponse> getByOffer() {
        return policyService.getByOfferList();
    }

    @GetMapping("/ListPolicy")
    public List<PolicyResponse> getByPolicy() {
        return policyService.getByPolicyList();
    }
    @GetMapping("/lastOffers/{userId}")
    public List<PolicyResponse> getLastTenOffers(@PathVariable String userId){
        Long userIdLong;
        try {
            userIdLong = Long.parseLong(userId);
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid user ID");
        }
        return policyService.getTopTenOffer(userIdLong);
    }
    @GetMapping("/lastPolicies/{userId}")
    public List<PolicyResponse> getLastTenPolicies(@PathVariable String userId){
        Long userIdLong;
        try {
            userIdLong = Long.parseLong(userId);
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid user ID");
        }
        return policyService.getTopTenPolicy(userIdLong);
    }

    @GetMapping("/listByUserId/{userId}")
    public List<PolicyResponse> getByUserId(@PathVariable long userId){
        return policyService.getByUserId(userId);
    }


    @GetMapping("/ratio")
    public double getPTRatio() {
        return policyService.calculatePTRatio();
    }
}
