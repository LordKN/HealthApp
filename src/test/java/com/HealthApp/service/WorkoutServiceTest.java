package com.HealthApp.service;

import com.HealthApp.dto.CreateWorkoutRequestDto;
import com.HealthApp.model.Client;
import com.HealthApp.model.Workout;
import com.HealthApp.repo.ClientRepository;
import com.HealthApp.repo.WorkoutRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WorkoutServiceTest {

    @Mock
    private ClientRepository clientRepo;

    @InjectMocks
    private WorkoutService service;

    private final String email = "client@example.com";
    Client client = new Client();

    @Test
    @DisplayName("Test get workout by id")
    void testGetWorkoutById_whenWorkoutIdAndClientEmailAreProvided_returnWorkoutObject() {
        // Arrange
        Workout workout = new Workout();

        workout.setId(1L);
        workout.setName("Push Day");

        client.setWorkouts(List.of(workout));

        when(clientRepo.findByEmail(email))
                .thenReturn(Optional.of(client));

        // Act
        Workout result = service.getWorkoutById(1L, email);

        // Assert
        assertEquals("Push Day", result.getName());
        assertEquals(1L, result.getId());

        verify(clientRepo).findByEmail(email);
    }

    @Test
    @DisplayName("Test get workout per client")
    void testGetWorkout_whenClientEmailIsProvided_returnListOfWorkoutObject() {
        // Arrange
        Workout workout1 = new Workout();
        workout1.setId(1L);
        workout1.setName("Push Day");

        Workout workout2 = new Workout();
        workout2.setId(2L);
        workout2.setName("Pull Day");

        client.setWorkouts(List.of(workout1, workout2));

        when(clientRepo.findByEmail(email))
                .thenReturn(Optional.of(client));

        // Act
        List<Workout> workoutList= service.getWorkouts(email);

        // Assert
        assertEquals(2, workoutList.size());
        assertEquals("Push Day", workoutList.get(0).getName());
        assertEquals("Pull Day", workoutList.get(1).getName());

        verify(clientRepo).findByEmail(email);
    }

    @Disabled
    @Test
    @DisplayName("Test save workout for a client")
    void testSaveWorkout_whenClientEmailNameAndDescriptionAreProvided_saveWorkoutAndReturnNothing() {
        // Arrange

        // Act

        // Assert

    }
}
