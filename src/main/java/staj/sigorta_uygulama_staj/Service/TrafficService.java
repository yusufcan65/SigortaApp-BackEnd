package staj.sigorta_uygulama_staj.Service;

import staj.sigorta_uygulama_staj.DTO.Request.CreateTrafficPolicyRequest;
import staj.sigorta_uygulama_staj.DTO.Response.PolicyResponse;
import staj.sigorta_uygulama_staj.Entities.Cars;
import staj.sigorta_uygulama_staj.Entities.Policy;

import java.util.List;

public interface TrafficService {

    Policy CalculateOfferPolicy(CreateTrafficPolicyRequest createPolicyRequest);
    Double calculateInsuranceValue(Double value);
    List<PolicyResponse> getAll();
    List<PolicyResponse> getByOfferList();
    List<PolicyResponse> getByPolicyList();
    List<PolicyResponse> getAllByCustomerId(long customer_id);

    //araç litelemek ve seçmek için kullanıcak metotlar
    List<String> getBrands();
    List<String> getModelsByBrand(String brand);
    List<Integer> getModelYearsByBrandAndModel(String brand ,String model);
    Cars getIdByBrandModelAndModelYear(String brand, String model, Integer modelYear);
}
