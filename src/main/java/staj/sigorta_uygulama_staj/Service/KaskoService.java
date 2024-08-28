package staj.sigorta_uygulama_staj.Service;

import staj.sigorta_uygulama_staj.DTO.Request.CreateKaskoPolicyRequest;
import staj.sigorta_uygulama_staj.DTO.Response.PolicyResponse;
import staj.sigorta_uygulama_staj.Entities.Cars;
import staj.sigorta_uygulama_staj.Entities.Policy;

import java.util.List;

public interface KaskoService {

    Policy CalculateOfferPolicy(CreateKaskoPolicyRequest createKaskoPolicyRequest);
    Double calculateInsuranceValue(Double value);

    List<PolicyResponse> getAll();
    Policy getByPolicyId(Long id);


    List<PolicyResponse> getByOfferList();
    List<PolicyResponse> getByPolicyList();
    List<PolicyResponse> getAllByCustomerId(long customer_id);

    // araç verileirni dbden çekmek için gerekli metotlar burada tanımlanmış
    List<String> getBrands();
    List<String> getModelsByBrand(String brand);
    List<Integer> getModelYearsByBrandAndModel(String brand ,String model);
    Cars getIdByBrandModelAndModelYear(String brand, String model, Integer modelYear);



}
