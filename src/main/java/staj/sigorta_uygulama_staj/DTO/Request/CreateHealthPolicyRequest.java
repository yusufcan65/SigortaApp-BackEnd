package staj.sigorta_uygulama_staj.DTO.Request;

import lombok.Data;

@Data
public class CreateHealthPolicyRequest {

    private long userId;
    private long customerId;
    private String smokeStatus ;
    private String sporStatus;
    private String operationStatus;
    private String chronicDiseaseStatus;
}
