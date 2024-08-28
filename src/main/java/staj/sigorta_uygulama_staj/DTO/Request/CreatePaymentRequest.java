package staj.sigorta_uygulama_staj.DTO.Request;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreatePaymentRequest {

    @Size(min = 16, max = 16, message = "card number must be 16 digits ")
    private String card_number;
    private String card_owner;
    private String expiry_date; // AY YIL
    private String cvv;


}
