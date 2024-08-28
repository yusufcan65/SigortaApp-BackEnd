package staj.sigorta_uygulama_staj.DTO.Response;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import staj.sigorta_uygulama_staj.Entities.Customer;

import java.time.LocalDate;
import java.util.Date;

@Data
public class CustomerResponseUpdate {

    private String name;
    private String surname;
    private String id_number;//tc kimlik numarası
    private LocalDate birth_date;
    private String city;
    private String district;
    private String phone_number;
    private String email;

    public CustomerResponseUpdate(Customer customer){
        this.name= customer.getName();
        this.surname= customer.getSurname();
        this.phone_number=customer.getPhone_number();
        this.email = customer.getEmail();
        this.district = customer.getDistrict();
        this.city = customer.getCity();
        this.id_number =customer.getId_number();
        this.birth_date = customer.getBirth_date();

    }
}
