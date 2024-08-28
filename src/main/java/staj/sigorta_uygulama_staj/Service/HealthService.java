package staj.sigorta_uygulama_staj.Service;

import staj.sigorta_uygulama_staj.DTO.Request.CreateHealthPolicyRequest;
import staj.sigorta_uygulama_staj.DTO.Response.PolicyResponse;
import staj.sigorta_uygulama_staj.Entities.Policy;

import java.time.LocalDate;
import java.util.List;

public interface HealthService {

    Policy CalculateOfferPolicy(CreateHealthPolicyRequest createHealthPolicyRequest);
    double calculateInsuranceValue(LocalDate birthDate, String smokeStatus, String sporStatus, String operationStatus, String chronicDiseaseStatus);
    List<PolicyResponse> getAll();
    Policy getByPolicyId(Long id);



}
