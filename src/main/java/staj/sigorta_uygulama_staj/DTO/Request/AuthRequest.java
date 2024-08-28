package staj.sigorta_uygulama_staj.DTO.Request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class AuthRequest {


    private String username;
    private String password;
}
