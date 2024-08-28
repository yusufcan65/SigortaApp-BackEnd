package staj.sigorta_uygulama_staj.DTO.Response;


import lombok.Data;
import staj.sigorta_uygulama_staj.Entities.Users;

@Data
public class UserResponse {

    private long id;
    private String name;
    private String surname;
    private String password;
    private String email;
    private String username;

    public UserResponse(Users user) {
        this.id = user.getId();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.username = user.getUsername();
    }
}
