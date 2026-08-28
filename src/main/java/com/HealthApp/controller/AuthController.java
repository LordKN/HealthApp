package com.HealthApp.controller;

import com.HealthApp.dto.Credential;
import com.HealthApp.dto.LoginResponse;
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

    @PostMapping("/api/auth/login")
    public LoginResponse login (@RequestBody Credential credential) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(credential.username(), credential.password()));
        String message = "Login success";
        String role = "invalid";

        if (authentication.isAuthenticated()) {
            System.out.println("Login success");
            role = authentication.getAuthorities()
                    .iterator()
                    .next()
                    .getAuthority();
        }
        else {
            System.out.println("Login failed");
            message = "Login failed";
        }

        return new LoginResponse(message, role);
    }
}
