package staj.sigorta_uygulama_staj.Service;

import staj.sigorta_uygulama_staj.DTO.Response.PolicyResponse;
import staj.sigorta_uygulama_staj.Entities.Policy;

import java.util.List;


public interface PolicyService {

    Policy getByPolicyId(Long policy_id);
    void deletePolicy(long policyId);
    List<PolicyResponse> getAll();
    List<PolicyResponse> getByUserId(long userId);
    List<PolicyResponse> getByOfferList();
    List<PolicyResponse> getByPolicyList();
    List<PolicyResponse> getAllByCustomerId(long customer_id);
    List<PolicyResponse> getTopTenOffer(long userId);//son oluşturulan 10 teklifi listeler
    List<PolicyResponse> getTopTenPolicy(long userId);//son oluşturulan 10 poliçeyi listeler
     double calculatePTRatio();

}
