package staj.sigorta_uygulama_staj.Service;

import staj.sigorta_uygulama_staj.DTO.Request.LoginRequest;
import staj.sigorta_uygulama_staj.DTO.Request.UpdateUserRequest;
import staj.sigorta_uygulama_staj.DTO.Request.UserRequest;
import staj.sigorta_uygulama_staj.DTO.Response.LoginResponse;
import staj.sigorta_uygulama_staj.DTO.Response.RegisterResponse;
import staj.sigorta_uygulama_staj.DTO.Response.UserResponse;
import staj.sigorta_uygulama_staj.Entities.Users;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserResponse> getAll();

    LoginResponse Login(LoginRequest loginRequest);
    RegisterResponse Register(UserRequest userRequest);

    Optional<Users> getOneUserByUsername(String UserName);

    Users getByUserId(Long id);
    Users updateUser(UpdateUserRequest userRequestd,Long id);
    void deleteUser(Long id);
    UserResponse getByUpdateUserId(Long id);

    // role göre listeleme yapacak
    List<UserResponse> getAllUsers();



}
