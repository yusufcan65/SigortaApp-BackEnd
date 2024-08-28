package staj.sigorta_uygulama_staj.Service;

import staj.sigorta_uygulama_staj.DTO.Request.CreateCustomerRequest;
import staj.sigorta_uygulama_staj.DTO.Request.UpdateCustomerRequest;
import staj.sigorta_uygulama_staj.DTO.Response.CustomerResponse;
import staj.sigorta_uygulama_staj.DTO.Response.CustomerResponseUpdate;
import staj.sigorta_uygulama_staj.Entities.Customer;

import java.util.List;

public interface CustomerService {

    void CreateCustomer(CreateCustomerRequest createCustomerRequest);
    List<CustomerResponse> getAll();
    Customer getCustomerByIdNumber(String id_number);

    Customer updateCustomer(UpdateCustomerRequest updateCustomerRequest);
    void deleteCustomer(Long id);
    CustomerResponseUpdate getCustomerById(Long id);
    List<CustomerResponse> getByUserId(long user_id);
    Customer getByName(String name);
    Customer getById(long id);
    Customer getByCustomerNumber(Integer customerNumber);
}
