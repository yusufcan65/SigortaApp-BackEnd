package staj.sigorta_uygulama_staj.Service.Impl;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import staj.sigorta_uygulama_staj.DTO.Request.LoginRequest;
import staj.sigorta_uygulama_staj.DTO.Request.UpdateUserRequest;
import staj.sigorta_uygulama_staj.DTO.Request.UserRequest;
import staj.sigorta_uygulama_staj.DTO.Response.CustomerResponse;
import staj.sigorta_uygulama_staj.DTO.Response.LoginResponse;
import staj.sigorta_uygulama_staj.DTO.Response.RegisterResponse;
import staj.sigorta_uygulama_staj.DTO.Response.UserResponse;
import staj.sigorta_uygulama_staj.Entities.Role;
import staj.sigorta_uygulama_staj.Entities.Users;
import staj.sigorta_uygulama_staj.Exception.UserException.UserAlreadyExistsException;
import staj.sigorta_uygulama_staj.Exception.UserException.loginUnSuccesfulException;
import staj.sigorta_uygulama_staj.Repository.UserRepository;
import staj.sigorta_uygulama_staj.Service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static staj.sigorta_uygulama_staj.Entities.Role.USER;


@Service
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public RegisterResponse Register(UserRequest userRequest) {
        RegisterResponse registerResponse = new RegisterResponse();
        if (userRepository.findByUsername(userRequest.getUsername()).isPresent()) {
            registerResponse.setMessage("bu username zaten kayıtlı lütfen farklı bir username deneyin ");
            throw new UserAlreadyExistsException("bu username zaten kayıtlı lütfen farklı bir username deneyin");

        }
        Users user = new Users();

        user.setName(userRequest.getName());
        user.setSurname(userRequest.getSurname());
        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        String encodedPassword = passwordEncoder.encode(userRequest.getPassword());
        user.setPassword(encodedPassword);
        user.getRoles().add(USER);
        userRepository.save(user);
        registerResponse.setMessage("user succesfuly registered!!!!!");

        return registerResponse;

    }
    @Override
    public List<UserResponse> getAll() {
        List<Users> userList = userRepository.findAll();

        return userList.stream().map(Users -> new UserResponse(Users)).collect(Collectors.toList());
    }

    @Override
    public LoginResponse Login(LoginRequest loginRequest) {
        Optional<Users> user = this.getOneUserByUsername(loginRequest.getUsername());
        if(user.isEmpty() || !user.get().getPassword().equals(loginRequest.getPassword())  ){
             throw new loginUnSuccesfulException("şifre veya parola hatalı");
        }
        LoginResponse userResponse = new LoginResponse();
        userResponse.setId(user.get().getId());
        return userResponse;
    }



    @Override
    public Optional<Users> getOneUserByUsername(String username) {
        return userRepository.findByUsername(username);

    }

    @Override
    public Users getByUserId(Long id){
        return userRepository.findById(id).orElseThrow();
    }

    @Override
    public Users updateUser(UpdateUserRequest request, Long id) {

        Users user = getByUserId(id);
        if(user != null){
            user.setName(request.getName());
            user.setSurname(request.getSurname());
            user.setEmail(request.getEmail());
            return userRepository.save(user);
        }
        else{
           return null;
        }

    }

    @Override
    public void deleteUser(Long id) {
         userRepository.deleteById(id);
    }

    @Override
    public UserResponse getByUpdateUserId(Long id) {
        Users user = userRepository.findById(id).orElseThrow();
        UserResponse userResponse = new UserResponse(user);
        userResponse.setName(user.getName());
        userResponse.setSurname(user.getSurname());
        userResponse.setEmail(user.getEmail());
        return userResponse;
    }

    public List<CustomerResponse> getCustomerByUserId(Long id){
        return null;
    }

    @Override
    public List<UserResponse> getAllUsers(){
        List<Users> usersList = this.userRepository.findByRolesContaining(Role.USER);
        return usersList.stream().map(UserResponse::new).collect(Collectors.toList());
    }
}
