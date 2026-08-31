package com.HealthApp.controller;

import com.HealthApp.dto.Credential;
import com.HealthApp.dto.LoginResponse;
import com.HealthApp.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
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
    public ResponseEntity<LoginResponse> login (@RequestBody Credential credential) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(credential.username(), credential.password()));
            System.out.println("Login success");
            String token = jwtService.generateToken(credential.username());

            String role = authentication.getAuthorities()
                    .iterator()
                    .next()
                    .getAuthority();

            return ResponseEntity.ok(new LoginResponse(token, role));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse(null, null));
        }
    }
}
