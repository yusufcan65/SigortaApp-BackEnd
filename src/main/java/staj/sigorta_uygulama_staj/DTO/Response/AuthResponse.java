package staj.sigorta_uygulama_staj.DTO.Response;

import lombok.Data;

@Data
public class AuthResponse {

    private Long userId;

    private String accessToken;
    private String refreshToken;
}
