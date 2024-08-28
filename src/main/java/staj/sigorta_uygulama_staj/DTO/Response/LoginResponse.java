package staj.sigorta_uygulama_staj.DTO.Response;

import lombok.Data;

@Data
public class LoginResponse {

    private long id;
    private String name;
    private String surname;
    private String password;
    private String email;
    private String username;


}
