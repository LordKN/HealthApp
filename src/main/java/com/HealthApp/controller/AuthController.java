package com.HealthApp.controller;

import com.HealthApp.dto.Credential;
import com.HealthApp.dto.LoginResponse;
import com.HealthApp.exception.InvalidRefreshTokenException;
import com.HealthApp.model.*;
import com.HealthApp.repo.AdminRepository;
import com.HealthApp.repo.ClientRepository;
import com.HealthApp.repo.CoachRepository;
import com.HealthApp.service.JwtService;
import com.HealthApp.service.RefreshSessionService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.security.SignatureException;
import java.time.Duration;
import java.util.UUID;

@RestController
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RefreshSessionService refreshSessionService;

    @Autowired
    private ClientRepository clientRepo;

    @Autowired
    private CoachRepository coachRepo;

    @Autowired
    private AdminRepository adminRepo;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

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

            UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();

            RefreshSession session = refreshSessionService.createSession(principal.getId(), principal.getRole());
            String refreshToken = jwtService.generateRefreshToken(session.getSessionId(), session.getCurrentTokenId(), session.getExpiresAt());

            ResponseCookie responseCookie = ResponseCookie
                    .from("refreshToken", refreshToken)
                    .httpOnly(true)
                    .secure(false)
                    .path("/api/auth")
                    .maxAge(Duration.ofDays(7))
                    .sameSite("Lax")
                    .build();

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                    .body(new LoginResponse(token, role));

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse(null, null));
        }
    }

    @PostMapping("/api/auth/refresh")
    public ResponseEntity<?> refresh(@CookieValue(name = "refreshToken", required = false) String refreshToken) {
        if (refreshToken == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .build();
        }
        UUID sessionId = jwtService.extractSessionId(refreshToken);
        UUID tokenId = jwtService.extractTokenId(refreshToken);
        RefreshSession refreshSession = refreshSessionService.refreshSession(sessionId, tokenId);
        String newRefreshToken = jwtService.generateRefreshToken(refreshSession.getSessionId(), refreshSession.getCurrentTokenId(), refreshSession.getExpiresAt());
        String email = retrieveEmail(refreshSession);
        String newAccessToken = jwtService.generateToken(email);
        ResponseCookie responseCookie = ResponseCookie
                .from("refreshToken", newRefreshToken)
                .httpOnly(true)
                .secure(false)
                .path("/api/auth")
                .sameSite("Lax")
                .maxAge(Duration.ofDays(7))
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(new LoginResponse(newAccessToken, refreshSession.getRole().toString()));

    }

    public String retrieveEmail (RefreshSession session) {
        switch (session.getRole()) {
            case CLIENT -> {
                Client client = clientRepo.findById(session.getUserId())
                        .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
                return client.getEmail();
            }

            case COACH -> {
                Coach coach = coachRepo.findById(session.getUserId())
                        .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
                return coach.getEmail();
            }

            case ADMIN -> {
                Admin admin = adminRepo.findById(session.getUserId())
                        .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
                return admin.getEmail();
            }

            default -> {
                throw new IllegalArgumentException("Invalid role: " + session.getRole());
            }
        }
    }

    @PostMapping("/api/auth/logout")
    public ResponseEntity<Void> logout (@CookieValue(name = "refreshToken", required = false) String refreshToken) {
        if (refreshToken != null) {
            try {
                UUID sessionId = jwtService.extractSessionId(refreshToken);
                UUID tokenId = jwtService.extractTokenId(refreshToken);

                refreshSessionService.revokeSession(sessionId, tokenId);
            } catch (InvalidRefreshTokenException |
                     ExpiredJwtException |
                     MalformedJwtException |
                     SignatureException e  ) {
                logger.warn("Logout attempted with invalid refresh token");
            }
        }



        ResponseCookie clearCookie = ResponseCookie
                .from("refreshToken", "")
                .httpOnly(true)
                .secure(false)
                .path("/api/auth")
                .maxAge(Duration.ZERO)
                .sameSite("Lax")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, clearCookie.toString())
                .build();
    }
}
