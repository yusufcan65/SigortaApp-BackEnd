package staj.sigorta_uygulama_staj.DTO.Request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Data
public class CreateCustomerRequest {


    @NotBlank(message = "Ad alanı boş olamaz")
    private String name;
    @NotBlank(message = "Soyad alanı boş olamaz")
    private String surname;
    @NotBlank(message = "TC Numarası alanı boş olamaz")
    @Size(min = 11, max = 11, message = "TC Kimlik numarası 11 haneli olmalıdır")
    private String id_number;//tc kimlik no
    @NotBlank(message = "Doğum Tarihi alanı boş olamaz")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birth_date;
    @NotBlank(message = "Şehir alanı boş olamaz")
    private String city;
    @NotBlank(message = "İlçe alanı boş olamaz")
    private String district;
    @NotBlank(message = "Telefon numarası alanı  boş olamaz")
    @Size(min = 11, max = 11)
    private String phone_number;
    private Long userId;
    @NotBlank(message = "Email alanı boş olamaz")
    @Email(message = "geçerli bir email yaziniz")
    private String email;
}
