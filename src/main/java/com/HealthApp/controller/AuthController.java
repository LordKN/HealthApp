package com.HealthApp.controller;

import com.HealthApp.model.Credential;
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
    public String login (@RequestBody Credential credential) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(credential.username(), credential.password()));

        if (authentication.isAuthenticated()) {
            System.out.println("Login success");
            return "Login success";
        }
        System.out.println("Login failed");
        return "Login failed";
    }
}
