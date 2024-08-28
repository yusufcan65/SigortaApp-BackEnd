package staj.sigorta_uygulama_staj.DTO.Request;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UpdateUserRequest {

    private Long id;
    private String name;
    private String surname;
    private String password;
    private String email;
    private String username;
}
