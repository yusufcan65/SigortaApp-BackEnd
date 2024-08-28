package staj.sigorta_uygulama_staj.DTO.Response;

import lombok.Data;
import staj.sigorta_uygulama_staj.Entities.Customer;

@Data
public class CustomerResponse {

    private long id;
    private String name;
    private String surname;
    private String phone_number;
    private Integer customerNumber;
    private String id_number;
    private String email;

    public CustomerResponse(Customer customer){
        this.id = customer.getId();
        this.name= customer.getName();
        this.surname= customer.getSurname();
        this.phone_number=customer.getPhone_number();
        this.customerNumber=customer.getCustomerNumber();
        this.email = customer.getEmail();
        this.id_number = customer.getId_number();
    }
}
