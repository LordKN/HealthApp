package com.HealthApp.service;

import com.HealthApp.model.Admin;
import com.HealthApp.model.Client;
import com.HealthApp.model.Coach;
import com.HealthApp.repo.AdminRepository;
import com.HealthApp.repo.ClientRepository;
import com.HealthApp.repo.CoachRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MyUserDetailServiceTest {

    @Mock
    private CoachRepository coachRepo;

    @Mock
    private ClientRepository clientRepo;

    @Mock
    private AdminRepository adminRepo;

    @InjectMocks
    private MyUserDetailService myUserDetailService;

    @Test
    @DisplayName("Test load user by username (admin)")
    void testLoadUserByUsername_whenAdminEmailProvided_returnUserDetailsObject() {

        // Arrange
        String email = "admin@example.com";
        Admin admin = new Admin();
        admin.setEmail(email);

        when(adminRepo.findByEmail(email))
                .thenReturn(Optional.of(admin));

        // Act
        UserDetails u = myUserDetailService.loadUserByUsername(email);

        // Assert
        assertEquals(email, u.getUsername());
    }

    @Test
    @DisplayName("Test load user by username (client)")
    void testLoadUserByUsername_whenClientEmailProvided_returnUserDetailsObject() {

        // Arrange
        String email = "client@example.com";
        Client client = new Client();
        client.setEmail(email);

        when(clientRepo.findByEmail(email))
                .thenReturn(Optional.of(client));

        // Act
        UserDetails u = myUserDetailService.loadUserByUsername(email);

        // Assert
        assertEquals(email, u.getUsername());
    }

    @Test
    @DisplayName("Test load user by username (coach)")
    void testLoadUserByUsername_whenCoachEmailProvided_returnUserDetailsObject() {

        // Arrange
        String email = "coach@example.com";
        Coach coach = new Coach();
        coach.setEmail(email);

        when(coachRepo.findByEmail(email))
                .thenReturn(Optional.of(coach));

        // Act
        UserDetails u = myUserDetailService.loadUserByUsername(email);

        // Assert
        assertEquals(email, u.getUsername());
    }
}
