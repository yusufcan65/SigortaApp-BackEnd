package staj.sigorta_uygulama_staj.Service.Impl;

import org.springframework.stereotype.Service;
import staj.sigorta_uygulama_staj.DTO.Response.PolicyResponse;
import staj.sigorta_uygulama_staj.Entities.Policy;
import staj.sigorta_uygulama_staj.Repository.PolicyRepository;
import staj.sigorta_uygulama_staj.Service.PolicyService;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class PolicyServiceImpl implements PolicyService {

    private final PolicyRepository policyRepository;


    public PolicyServiceImpl( PolicyRepository policyRepository) {
        this.policyRepository = policyRepository;
    }

    @Override
    public Policy getByPolicyId(Long policy_id) {
        return policyRepository.findById(policy_id).orElseThrow();
    }

    @Override
    public void deletePolicy(long policyId) {
        this.policyRepository.deleteById(policyId);
    }

    @Override
    public List<PolicyResponse> getAll() {
        List<Policy> policyList = this.policyRepository.findAll();
        return policyList.stream().map(PolicyResponse::new).collect(Collectors.toList());
    }

    @Override
    public List<PolicyResponse> getByUserId(long userId) {
        List<Policy> policyList = this.policyRepository.findByUserId(userId);
        return policyList.stream().map(PolicyResponse::new).collect(Collectors.toList());
    }

    //sadece teklifleri listeleyen metot
    @Override
    public List<PolicyResponse> getByOfferList() {
        List<Policy> policyList = policyRepository.findByStatus("T");

        List<PolicyResponse> policyResponses = policyList.stream().map(policy -> new PolicyResponse(policy)).collect(Collectors.toList());

        return policyResponses;
    }
    //sadece poliçeleri listeleyen metot
    @Override
    public List<PolicyResponse> getByPolicyList() {
        List<Policy> policyList = policyRepository.findByStatus("P");
        List<PolicyResponse> policyResponses = policyList.stream().map(PolicyResponse::new).collect(Collectors.toList());
        return policyResponses;
    }
    // müşterinin tekliflerini listeleyen metot
    @Override
    public List<PolicyResponse> getAllByCustomerId(long customer_id){

        List<Policy> policy = policyRepository.findByCustomerId(customer_id);

        List<PolicyResponse> policyResponses = policy.stream().map(policy1 -> new PolicyResponse(policy1)).collect(Collectors.toList());

        return policyResponses;
    }


    // son 10 teklif
    @Override
    public List<PolicyResponse> getTopTenOffer(long userId){
        List<Policy> policyList = this.policyRepository.findByStatus("T");

        List<Policy> filteredPolicyList = policyList.stream()
                .filter(Policy-> Policy.getUser().getId().equals(userId))
                .sorted((p1,p2) -> Long.compare(p2.getId(), p1.getId()))
                .limit(10)
                .collect(Collectors.toList());

        List<PolicyResponse> policyResponses = filteredPolicyList.stream()
                .map(policy -> new PolicyResponse(policy))
                .collect(Collectors.toList());

        return policyResponses;
    }
    @Override
    public List<PolicyResponse> getTopTenPolicy(long userId){
        //Pageable topTen = PageRequest.of(0, 10);
        List<Policy> policyList = this.policyRepository.findByStatus("P");

        List<Policy> filteredPolicyList = policyList.stream()
                .filter(Policy-> Policy.getUser().getId().equals(userId))
                .sorted((p1,p2) -> Long.compare(p2.getId(), p1.getId()))
                .limit(10)
                .collect(Collectors.toList());

        List<PolicyResponse> policyResponses = filteredPolicyList.stream()
                .map(policy -> new PolicyResponse(policy))
                .collect(Collectors.toList());

        return policyResponses;
    }

    public double calculatePTRatio() {
        long countT = policyRepository.countByOffers();
        long countP = policyRepository.countByStatusP();

        if (countT == 0) {
            return 0; // Bölme hatası olmaması için, eğer T yoksa oran 0 döndürülür.
        }

        return (double) countP / countT;
    }

}
