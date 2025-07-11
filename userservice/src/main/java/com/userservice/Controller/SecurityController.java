package com.userservice.Controller;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.userservice.DTO.LoginRequest;
import com.userservice.DTO.LoginResponse;
import com.userservice.Entities.User;
import com.userservice.SecurityService.JwtUtilService;
import com.userservice.Service.EmailVerificationService;
import com.userservice.Service.TokensService;
import com.userservice.Service.UserService;

@RestController
@RequestMapping("/auth")
public class SecurityController {
    @Autowired
    private EmailVerificationService emailVerificationService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokensService tokensService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtilService jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;


    @GetMapping("/confirm-account")
    public ResponseEntity<?> confirmAccount(@RequestParam("token") String token) {
        return emailVerificationService.verifyEmail(token);
    }

    @PostMapping("/send/email")
    public ResponseEntity<?> verifyEmail(@RequestBody String email) {
        return emailVerificationService.sendEmail(email);
        // return ResponseEntity.ok().body("Email Sent Successfully");
    }


    
    // create User
    @PostMapping("/signup")
    public ResponseEntity<?> createUser(@RequestBody User u) {

        boolean val = tokensService.verified(u.getEmail());

        if(!val){
            return ResponseEntity.badRequest().body(Map.of("message","Email has not been verified."));
        }

        String id = UUID.randomUUID().toString();
        u.setId(id);
        u.setPassword(passwordEncoder.encode(u.getPassword()));
        // eService.sendEmail(u);
        this.userService.saveUser(u);

        return ResponseEntity.ok().body(Map.of("message","User saved Successfully.."));
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            String token = jwtUtil.generateToken(auth.getName());

            return new LoginResponse("Login successful", token);

        } catch (AuthenticationException e) {
            return new LoginResponse("Invalid username or password", null);
        }
    }
}
