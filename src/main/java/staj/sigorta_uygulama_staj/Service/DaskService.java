package staj.sigorta_uygulama_staj.Service;

import staj.sigorta_uygulama_staj.DTO.Request.CreateDaskPolicyRequest;
import staj.sigorta_uygulama_staj.Entities.Policy;

public interface DaskService {

    Policy CalculateOfferPolicy(CreateDaskPolicyRequest createPolicyRequest);
    Double calculateInsuranceValue(Double squareMeter, int floorNumber,int numberBuildFloor,String damageState, int buildingAge);
    void deleteDaskPolicy(long id);
}
