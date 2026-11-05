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
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
    @DisplayName("Test get all coaches")
    void testFindAllCoaches_returnAllCoaches() {
        // Arrange
        Coach c1 = new Coach();
        c1.setName("Huy");

        Coach c2 = new Coach();
        c2.setName("Rin");

        when(repo.findAll()).thenReturn(List.of(c1, c2));

        // Act
        List<Coach> coachList = service.findAllCoaches();

        // Assert
        assertEquals("Huy", coachList.get(0).getName());
        assertEquals("Rin", coachList.get(1).getName());

        verify(repo).findAll();
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
    void testDeleteCoach_whenCoachIdProvided_deleteCoachObject() {
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
    @DisplayName("Test delete coach by id throw run time exception")
    void testDeleteCoach_whenInvalidCoachIdProvided_throwUsernameNotFoundException() {
        // Arrange
        Long id = 1L;
        when(repo.existsById(id)).thenReturn(false);

        // Act
        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> service.deleteCoach(1L));

        // Assert
        assertEquals("Coach doesn't exist for deletion", exception.getMessage());

        verify(repo).existsById(id);
    }

    @Test
    @DisplayName("Test delete all coaches")
    void testDeleteAllCoaches() {
        // Arrange
        when(repo.count()).thenReturn(10L);

        // Act
        service.deleteAllCoaches();

        // Assert
        verify(repo).count();
        verify(repo).deleteAll();
    }

    @Test
    @DisplayName("Test delete all coaches throw runtime exception")
    void testDeleteAllCoaches_throwRuntimeException() {
        // Arrange
        when(repo.count()).thenReturn(0L);

        // Act
        RuntimeException exception = assertThrows(RuntimeException.class, () -> service.deleteAllCoaches());


        // Assert
        assertEquals("No coach to delete", exception.getMessage());

        verify(repo).count();
    }

    @Test
    @DisplayName("Test count coaches")
    void testCountCoaches_returnCoachesCount() {
        // Arrange
        when(repo.count()).thenReturn(5L);

        // Act
        Long count = service.countCoaches();

        // Assert
        assertEquals(5L, count);

        verify(repo).count();
    }

    @Test
    @DisplayName("Test validate coach on null name")
    void testValidateCoach_whenNoNameProvided_throwRuntimeException() {
        // Arrange
        Coach c = new Coach();

        // Act
        RuntimeException exception = assertThrows(RuntimeException.class, () -> service.saveCoach(c));

        // Assert
        assertEquals("Coach name is required", exception.getMessage());
        verify(repo, never()).save(any(Coach.class));
    }

    @Test
    @DisplayName("Test validate coach on blank name")
    void testValidateCoach_whenBlankNameProvided_throwRuntimeWException() {
        // Arrange
        Coach c = new Coach();
        c.setName("");

        // Act
        RuntimeException exception = assertThrows(RuntimeException.class, () -> service.saveCoach(c));

        // Assert
        assertEquals("Coach name is required", exception.getMessage());
        verify(repo, never()).save(any(Coach.class));
    }

    @Test
    @DisplayName("Test validate coach on null email")
    void testValidateCoach_whenNoEmailProvided_throwRuntimeException() {
        // Arrange
        Coach c = createValidCoach();
        c.setEmail(null);

        // Act
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.saveCoach(c)
        );

        // Assert
        assertEquals("Coach email is required", exception.getMessage());
        verify(repo, never()).save(any(Coach.class));
    }

    @Test
    @DisplayName("Test validate coach on blank email")
    void testValidateCoach_whenBlankEmailProvided_throwRuntimeException() {
        // Arrange
        Coach c = createValidCoach();
        c.setEmail("   ");

        // Act
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.saveCoach(c)
        );

        // Assert
        assertEquals("Coach email is required", exception.getMessage());
        verify(repo, never()).save(any(Coach.class));
    }

    @Test
    @DisplayName("Test validate coach on invalid email")
    void testValidateCoach_whenInvalidEmailProvided_throwRuntimeException() {
        // Arrange
        Coach c = createValidCoach();
        c.setEmail("invalidemail.com");

        // Act
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.saveCoach(c)
        );

        // Assert
        assertEquals("Coach email must be valid", exception.getMessage());
        verify(repo, never()).save(any(Coach.class));
    }

    @Test
    @DisplayName("Test validate coach on null specialty")
    void testValidateCoach_whenNoSpecialtyProvided_throwRuntimeException() {
        // Arrange
        Coach c = createValidCoach();
        c.setSpecialty(null);

        // Act
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.saveCoach(c)
        );

        // Assert
        assertEquals("Coach specialty is required", exception.getMessage());
        verify(repo, never()).save(any(Coach.class));
    }

    @Test
    @DisplayName("Test validate coach on negative years of experience")
    void testValidateCoach_whenNegativeExperienceProvided_throwRuntimeException() {
        // Arrange
        Coach c = createValidCoach();
        c.setYearsOfExperience(-1);

        // Act
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.saveCoach(c)
        );

        // Assert
        assertEquals("Years of experience cannot be negative", exception.getMessage());
        verify(repo, never()).save(any(Coach.class));
    }

    @Test
    @DisplayName("Test validate coach on client count greater than 20")
    void testValidateCoach_whenClientCountGreaterThan20_throwRuntimeException() {
        // Arrange
        Coach c = createValidCoach();
        c.setClientCount(21);

        // Act
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.saveCoach(c)
        );

        // Assert
        assertEquals("Client count cannot be greater than 20", exception.getMessage());
        verify(repo, never()).save(any(Coach.class));
    }

    @Test
    @DisplayName("Test validate coach on null workplace")
    void testValidateCoach_whenNoWorkPlaceProvided_throwRuntimeException() {
        // Arrange
        Coach c = createValidCoach();
        c.setWorkPlace(null);

        // Act
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.saveCoach(c)
        );

        // Assert
        assertEquals("Workplace is required", exception.getMessage());
        verify(repo, never()).save(any(Coach.class));
    }

    @Test
    @DisplayName("Test validate coach on blank workplace")
    void testValidateCoach_whenBlankWorkPlaceProvided_throwRuntimeException() {
        // Arrange
        Coach c = createValidCoach();
        c.setWorkPlace("   ");

        // Act
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.saveCoach(c)
        );

        // Assert
        assertEquals("Workplace is required", exception.getMessage());
        verify(repo, never()).save(any(Coach.class));
    }

    @Test
    @DisplayName("Test validate coach on description exceeding 1000 characters")
    void testValidateCoach_whenDescriptionExceeds1000_throwRuntimeException() {
        // Arrange
        Coach c = createValidCoach();
        c.setDesc("A".repeat(1001));

        // Act
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.saveCoach(c)
        );

        // Assert
        assertEquals("Description must be under 1000 characters", exception.getMessage());
        verify(repo, never()).save(any(Coach.class));
    }

    @Test
    @DisplayName("Test validate coach on null role")
    void testValidateCoach_whenNoRoleProvided_throwRuntimeException() {
        // Arrange
        Coach c = createValidCoach();
        c.setRole(null);

        // Act
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.saveCoach(c)
        );

        // Assert
        assertEquals("Role must be assigned", exception.getMessage());
        verify(repo, never()).save(any(Coach.class));
    }


    @Test
    @DisplayName("Test update coach")
    void testUpdateCoach_whenIdAndUpdatedCoachObjectProvided_updateExistingCoach() {
        // Arrange
        long id = 1L;
        Coach existingCoach = new Coach();
        existingCoach.setId(id);
        existingCoach.setRole(Role.COACH);

        when(repo.findById(id)).thenReturn(Optional.of(existingCoach));


        Coach updatedCoach = createValidCoach();

        when(repo.save(existingCoach)).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        existingCoach = service.updateCoach(id, updatedCoach);

        // Assert
        assertEquals("Nguyen", existingCoach.getName());
        assertEquals("coach@example.com", existingCoach.getEmail());
        assertEquals(Specialty.STRENGTH_TRAINING, existingCoach.getSpecialty());
        assertEquals(5, existingCoach.getYearsOfExperience());
        assertEquals(10, existingCoach.getClientCount());
        assertEquals("GrindHub", existingCoach.getWorkPlace());
        assertEquals("Certified fitness coach", existingCoach.getDesc());

        verify(repo).findById(id);
        verify(repo).save(existingCoach);
    }

    private Coach createValidCoach() {
        Coach c = new Coach();

        c.setName("Nguyen");
        c.setEmail("coach@example.com");
        c.setSpecialty(Specialty.STRENGTH_TRAINING);
        c.setYearsOfExperience(5);
        c.setClientCount(10);
        c.setWorkPlace("GrindHub");
        c.setDesc("Certified fitness coach");
        c.setRole(Role.COACH);

        return c;
    }
}
