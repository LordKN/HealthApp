package com.HealthApp.controller;

import com.HealthApp.dto.Credential;
import com.HealthApp.dto.LoginResponse;
import com.HealthApp.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/api/auth/login")
    public LoginResponse login (@RequestBody Credential credential) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(credential.username(), credential.password()));
        String token = "";
        String role = "invalid";

        if (authentication.isAuthenticated()) {
            System.out.println("Login success");
            token = jwtService.generateToken(credential.username());
            System.out.println("token generated: " + token);
            role = authentication.getAuthorities()
                    .iterator()
                    .next()
                    .getAuthority();
        }
        else {
            System.out.println("Login failed");
            token = "Login failed";
        }

        return new LoginResponse(token, role);
    }
}
