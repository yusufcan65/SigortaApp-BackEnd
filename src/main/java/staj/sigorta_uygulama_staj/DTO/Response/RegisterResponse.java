package staj.sigorta_uygulama_staj.DTO.Response;

import lombok.Data;

@Data
public class RegisterResponse {
    private String message;
    private String name;
    private String surname;
    private String password;
    private String email;
    private String username;


}
