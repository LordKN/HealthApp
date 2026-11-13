package com.HealthApp.controller;

import com.HealthApp.model.*;
import com.HealthApp.repo.AdminRepository;
import com.HealthApp.repo.ClientRepository;
import com.HealthApp.repo.CoachRepository;
import com.HealthApp.repo.RefreshSessionRepository;
import com.HealthApp.service.JwtService;
import com.HealthApp.service.MyUserDetailService;
import com.HealthApp.service.RefreshSessionService;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest (controllers = AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private AuthenticationManager authenticationManager;

    @MockitoBean
    private RefreshSessionService refreshSessionService;

    @MockitoBean
    private ClientRepository clientRepo;

    @MockitoBean
    private CoachRepository coachRepo;

    @MockitoBean
    private AdminRepository adminRepo;

    @MockitoBean
    private RefreshSessionRepository refreshSessionRepository;

    @MockitoBean
    private MyUserDetailService myUserDetailService;



    @Test
    @DisplayName("Test Log in return 200")
    void testLogin_whenValidCredentialProvided_returnResponseEntityOk() throws Exception {
        // Arrange

        // Create a fake UserPrinciple object so we can tell it what to return
        UserPrincipal principal = mock(UserPrincipal.class);

        when(principal.getId()).thenReturn(1L);
        when(principal.getRole()).thenReturn(Role.CLIENT);

        // Create a real RefreshSession object for easier set up
        RefreshSession session = new RefreshSession();

        session.setSessionId(UUID.randomUUID());
        session.setCurrentTokenId(UUID.randomUUID());

        session.setUserId(principal.getId());
        session.setRole(principal.getRole());
        session.setCreatedAt(Instant.now());
        session.setExpiresAt(Instant.now().plus(7, ChronoUnit.DAYS));
        session.setRevoked(false);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                principal,              // this principal is an Object
                null,
                List.of(new SimpleGrantedAuthority("ROLE_CLIENT"))
        );

        when(refreshSessionService.createSession(principal.getId(), principal.getRole())).thenReturn(session);

        String refreshToken = "mock-refresh-token";
        String accessToken = "mock-access-token";

        when(jwtService.generateRefreshToken(session.getSessionId(), session.getCurrentTokenId(), session.getExpiresAt()))
                .thenReturn(refreshToken);
        when(jwtService.generateToken("someone@example.com")).thenReturn(accessToken);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "username": "someone@example.com",
                            "password": "rightpassword"
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(accessToken))
                .andExpect(jsonPath("$.role").value("ROLE_CLIENT"))
                .andExpect(header().string(HttpHeaders.SET_COOKIE,
                        containsString("refreshToken=" + refreshToken)));


        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(refreshSessionService).createSession(1L, Role.CLIENT);
    }

    @Test
    @DisplayName("Test Log in return 401")
    void testLogin_whenInvalidCredentialProvided_returnResponseEntityUnauthorized() throws Exception {
        // Arrange
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Invalid credentials"));

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "username": "someone@example.com",
                            "password": "wrongpassword"
                        }
                        """))
                .andExpect(status().isUnauthorized());

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    @DisplayName("Test refresh return 403")
    void testRefresh_whenNoRefreshTokenProvided_returnResponseEntityUnauthorized() throws Exception {
        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/auth/refresh"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Test refresh client return 200")
    void testRefresh_whenValidClientRefreshTokenProvided_returnResponseEntityOk() throws Exception {
        // Arrange
        String refreshToken = "mock-refresh-token";
        String email = "someone@example.com";

        Client client = new Client();
        client.setEmail(email);

        when(clientRepo.findById(1L)).thenReturn(Optional.of(client));


        String newRefreshToken = "mock-new-refresh-token";
        String newAccessToken = "mock-new-accessToken";

        UUID sessionId = UUID.randomUUID();
        UUID tokenId = UUID.randomUUID();

        when(jwtService.extractSessionId(refreshToken)).thenReturn(sessionId);
        when(jwtService.extractTokenId(refreshToken)).thenReturn(tokenId);

        // Create a real RefreshSession object for easier set up
        RefreshSession session = new RefreshSession();

        session.setSessionId(sessionId);
        session.setCurrentTokenId(tokenId);

        session.setUserId(1L);
        session.setRole(Role.CLIENT);
        session.setCreatedAt(Instant.now());
        session.setExpiresAt(Instant.now().plus(7, ChronoUnit.DAYS));
        session.setRevoked(false);


        when(refreshSessionService.refreshSession(sessionId, tokenId)).thenReturn(session);
        when(jwtService.generateRefreshToken(session.getSessionId(), session.getCurrentTokenId(), session.getExpiresAt()))
                .thenReturn(newRefreshToken);
        when(jwtService.generateToken(email)).thenReturn(newAccessToken);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/auth/refresh")
                .cookie(new Cookie("refreshToken", refreshToken)))  // Send cookie with request
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(newAccessToken))
                .andExpect(jsonPath("$.role").value("CLIENT"));

        verify(clientRepo).findById(1L);
    }

    @Test
    @DisplayName("Test refresh return 200")
    void testRefresh_whenValidCoachRefreshTokenProvided_returnResponseEntityOk() throws Exception {
        // Arrange
        String refreshToken = "mock-refresh-token";
        String email = "someone@example.com";

        Coach coach = new Coach();
        coach.setEmail(email);

        when(coachRepo.findById(1L)).thenReturn(Optional.of(coach));


        String newRefreshToken = "mock-new-refresh-token";
        String newAccessToken = "mock-new-accessToken";

        UUID sessionId = UUID.randomUUID();
        UUID tokenId = UUID.randomUUID();

        when(jwtService.extractSessionId(refreshToken)).thenReturn(sessionId);
        when(jwtService.extractTokenId(refreshToken)).thenReturn(tokenId);

        // Create a real RefreshSession object for easier set up
        RefreshSession session = new RefreshSession();

        session.setSessionId(sessionId);
        session.setCurrentTokenId(tokenId);

        session.setUserId(1L);
        session.setRole(Role.COACH);
        session.setCreatedAt(Instant.now());
        session.setExpiresAt(Instant.now().plus(7, ChronoUnit.DAYS));
        session.setRevoked(false);


        when(refreshSessionService.refreshSession(sessionId, tokenId)).thenReturn(session);
        when(jwtService.generateRefreshToken(session.getSessionId(), session.getCurrentTokenId(), session.getExpiresAt()))
                .thenReturn(newRefreshToken);
        when(jwtService.generateToken(email)).thenReturn(newAccessToken);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/auth/refresh")
                        .cookie(new Cookie("refreshToken", refreshToken)))  // Send cookie with request
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(newAccessToken))
                .andExpect(jsonPath("$.role").value("COACH"));

        verify(coachRepo).findById(1L);
    }

    @Test
    @DisplayName("Test refresh return 200")
    void testRefresh_whenValidAdminRefreshTokenProvided_returnResponseEntityOk() throws Exception {
        // Arrange
        String refreshToken = "mock-refresh-token";
        String email = "someone@example.com";

        Admin admin = new Admin();
        admin.setEmail(email);

        when(adminRepo.findById(1L)).thenReturn(Optional.of(admin));


        String newRefreshToken = "mock-new-refresh-token";
        String newAccessToken = "mock-new-accessToken";

        UUID sessionId = UUID.randomUUID();
        UUID tokenId = UUID.randomUUID();

        when(jwtService.extractSessionId(refreshToken)).thenReturn(sessionId);
        when(jwtService.extractTokenId(refreshToken)).thenReturn(tokenId);

        // Create a real RefreshSession object for easier set up
        RefreshSession session = new RefreshSession();

        session.setSessionId(sessionId);
        session.setCurrentTokenId(tokenId);

        session.setUserId(1L);
        session.setRole(Role.ADMIN);
        session.setCreatedAt(Instant.now());
        session.setExpiresAt(Instant.now().plus(7, ChronoUnit.DAYS));
        session.setRevoked(false);


        when(refreshSessionService.refreshSession(sessionId, tokenId)).thenReturn(session);
        when(jwtService.generateRefreshToken(session.getSessionId(), session.getCurrentTokenId(), session.getExpiresAt()))
                .thenReturn(newRefreshToken);
        when(jwtService.generateToken(email)).thenReturn(newAccessToken);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/auth/refresh")
                        .cookie(new Cookie("refreshToken", refreshToken)))  // Send cookie with request
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(newAccessToken))
                .andExpect(jsonPath("$.role").value("ADMIN"));

        verify(adminRepo).findById(1L);
    }

    @Test
    @DisplayName("Test logout on empty refresh token")
    void testLogout_whenNoRefreshTokenProvided_returnResponseEntityOk() throws Exception {
        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/auth/logout"))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.SET_COOKIE, containsString(""))
                );
    }

    @Test
    @DisplayName("Test logout on valid refresh token")
    void testLogout_whenValidRefreshToken() throws Exception {

        // Arrange
        String refreshToken = "mock-refresh-token";
        UUID sessionId = UUID.randomUUID();
        UUID tokenId = UUID.randomUUID();

        when(jwtService.extractSessionId(refreshToken)).thenReturn(sessionId);
        when(jwtService.extractTokenId(refreshToken)).thenReturn(tokenId);

        // Act
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/auth/logout")
                        .cookie(new Cookie("refreshToken", refreshToken))) // Send cookie with request
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.SET_COOKIE, containsString(""))
                );

        // Assert
        verify(refreshSessionService).revokeSession(sessionId, tokenId);
    }
}
