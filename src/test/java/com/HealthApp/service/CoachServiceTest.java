package com.HealthApp.service;

import com.HealthApp.model.Coach;
import com.HealthApp.model.Role;
import com.HealthApp.model.Specialty;
import com.HealthApp.repo.CoachRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
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
    }

    @Test
    @DisplayName("Test save coach")
    void testSaveCoach_whenCoachObjectProvided_returnCoachObject() {
        // Arrange
        Coach coach = new Coach();
        coach.setName("Huy");
        coach.setEmail("coach@example.com");
        coach.setSpecialty(Specialty.STRENGTH_TRAINING);
        coach.setYearsOfExperience(3);
        coach.setClientCount(5);
        coach.setWorkPlace("Olympic Fitness");
        coach.setRole(Role.COACH);
        when(repo.save(any(Coach.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));


        // Act
        Coach c = service.saveCoach(coach);

        // Assert
        assertEquals("Huy", c.getName());
        assertEquals("coach@example.com", c.getEmail());
        assertEquals(Specialty.STRENGTH_TRAINING, c.getSpecialty());
        assertEquals(3, c.getYearsOfExperience());
        assertEquals(5, c.getClientCount());
        assertEquals("Olympic Fitness", c.getWorkPlace());
        assertEquals(Role.COACH, c.getRole());

        verify(repo).save(coach);
    }

    @Test
    @DisplayName("Test delete coach by id")
    void testDeleteCoach() {
        // Arrange
        Long id = 3L;
        when(repo.existsById(id)).thenReturn(true);

        // Act
        service.deleteCoach(id);

        // Assert
        verify(repo).existsById(id);
        verify(repo).deleteById(id);
    }

    @Test
    @DisplayName("Test delete all coaches")
    void testDeleteCAllCoaches() {
        // Arrange
        when(repo.count()).thenReturn(10L);

        // Act
        service.deleteAllCoaches();

        // Assert
        verify(repo).count();
        verify(repo).deleteAll();
    }
}
