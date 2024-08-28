package staj.sigorta_uygulama_staj.DTO.Request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateCustomerRequest {

    private Long id;

    private String name;
    private String surname;
    private String id_number;//tc kimlik no
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birth_date;
    private String city;
    private String district;
    private String phone_number;
    private String email;
}
