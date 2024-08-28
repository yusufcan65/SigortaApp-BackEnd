package staj.sigorta_uygulama_staj.Controller;


import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import staj.sigorta_uygulama_staj.DTO.Request.LoginRequest;
import staj.sigorta_uygulama_staj.DTO.Request.UpdateUserRequest;
import staj.sigorta_uygulama_staj.DTO.Request.UserRequest;
import staj.sigorta_uygulama_staj.DTO.Response.LoginResponse;
import staj.sigorta_uygulama_staj.DTO.Response.RegisterResponse;
import staj.sigorta_uygulama_staj.DTO.Response.UserResponse;
import staj.sigorta_uygulama_staj.Entities.Users;
import staj.sigorta_uygulama_staj.Exception.UserException.UserAlreadyExistsException;
import staj.sigorta_uygulama_staj.Service.UserService;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public List<UserResponse> getAll(){
       return userService.getAll();
    }

    @GetMapping("/{username}")
    public Optional<Users> getOneByUsername(@PathVariable String username){
        return userService.getOneUserByUsername(username);
    }
    @PostMapping("/login")
    @ResponseStatus(code = HttpStatus.OK)
    public LoginResponse login(@RequestBody LoginRequest loginRequest){
        return this.userService.Login(loginRequest);
    }

    @GetMapping("/logout")
    public void logout(HttpSession session) {
        // Oturumu geçersiz kıl
        session.invalidate();
    }

    @PostMapping("/register")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<RegisterResponse> Register(@RequestBody  UserRequest userRequest){
        try {
            RegisterResponse response = userService.Register(userRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (UserAlreadyExistsException e) {
            RegisterResponse errorResponse = new RegisterResponse();
            errorResponse.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        }
    }

    @PutMapping("/updateuser/{userId}")
    public Users updateUser(@RequestBody UpdateUserRequest userRequest , @PathVariable long userId){
        return this.userService.updateUser(userRequest,userId);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable Long id){
          this.userService.deleteUser(id);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<UserResponse> getByUpdateUserId(@PathVariable long id){
        UserResponse userResponse = this.userService.getByUpdateUserId(id);
        return ResponseEntity.ok(userResponse);
    }

    @GetMapping("/userList")
    public List<UserResponse> getAllUserList(){
        return this.userService.getAllUsers();
    }
}
