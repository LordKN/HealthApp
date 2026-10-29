package com.HealthApp.service;

import com.HealthApp.model.Coach;
import com.HealthApp.repo.CoachRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CoachServiceTest {

    @Mock
    private CoachRepository repo;

    @InjectMocks
    private CoachService service;

    @Test
    @DisplayName("Test get coach by id")
    void testFindCoachById_whenCoachIdIsProvided_returnCoachObject() {
        // Arrange
        Coach coach = new Coach();
        coach.setId(1L);
        when(repo.findById(1L))
                .thenReturn(Optional.of(coach));

        // Act
        Coach c = service.findCoachById(1L);

        // Assert
        assertEquals(1L, c.getId());

        verify(repo).findById(1L);
    }

    @Test
    @DisplayName("Test get coach by email")
    void testFindCoachByEmail_whenCoachEmailIsProvided_returnCoachObject() {
        // Arrange
        Coach coach = new Coach();
        String email = "coach@example.com";
        coach.setEmail(email);
        when(repo.findByEmail(email))
                .thenReturn(Optional.of(coach));

        // Act
        Coach c = service.findCoachByEmail(email);

        // Assert
        assertEquals("coach@example.com", c.getEmail());

        verify(repo).findByEmail(email);

        // Assert
    }
}
