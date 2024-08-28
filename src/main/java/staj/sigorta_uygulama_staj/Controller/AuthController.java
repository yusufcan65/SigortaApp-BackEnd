package staj.sigorta_uygulama_staj.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import staj.sigorta_uygulama_staj.DTO.Request.AuthRequest;
import staj.sigorta_uygulama_staj.DTO.Request.UserRequest;
import staj.sigorta_uygulama_staj.DTO.Response.RegisterResponse;
import staj.sigorta_uygulama_staj.Entities.Role;
import staj.sigorta_uygulama_staj.Security.JwtTokenUtil;
import staj.sigorta_uygulama_staj.Security.UserDetailsImpl;
import staj.sigorta_uygulama_staj.Service.UserService;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;

    @Autowired
    public AuthController(UserService userService, JwtTokenUtil jwtTokenUtil, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody AuthRequest authRequest) {





        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );

            // Kullanıcı bilgilerini al
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            String username = authentication.getName();
            Long userId = ((UserDetailsImpl) authentication.getPrincipal()).getId();

            Set<Role> roles = userDetails.getAuthorities().stream()
                    .map(auth -> Role.valueOf(auth.getAuthority()))
                    .collect(Collectors.toSet());

            // Token'ı oluştur
            String token = jwtTokenUtil.generateToken(username, userId,roles);

            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("id", userId.toString());
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid username or password"));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody UserRequest userRequest) {
        RegisterResponse registerResponse = userService.Register(userRequest);
        return ResponseEntity.ok(registerResponse);
    }
}
