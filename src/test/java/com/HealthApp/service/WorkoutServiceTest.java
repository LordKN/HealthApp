package com.HealthApp.service;

import com.HealthApp.dto.CreateWorkoutRequestDto;
import com.HealthApp.dto.WorkoutDayRequestDto;
import com.HealthApp.model.Client;
import com.HealthApp.model.Exercise;
import com.HealthApp.model.Workout;
import com.HealthApp.model.WorkoutDay;
import com.HealthApp.repo.ClientRepository;
import com.HealthApp.repo.ExerciseRepository;
import com.HealthApp.repo.WorkoutRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WorkoutServiceTest {

    @Mock
    private ClientRepository clientRepo;

    @Mock
    private ExerciseRepository exerciseRepo;

    @Mock
    private WorkoutRepository workoutRepo;

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

    @Test
    @DisplayName("Test save workout for a client")
    void testSaveWorkout_whenClientEmailNameAndDescriptionAreProvided_saveWorkoutAndReturnNothing() {
        // Arrange
        Client client = new Client();
        client.setEmail(email);
        client.setWorkouts(new ArrayList<>());

        Workout workout = new Workout();
        workout.setClient(client);

        Exercise benchPress = new Exercise();
        benchPress.setId(1L);
        benchPress.setName("Bench press");

        Exercise shoulderPress = new Exercise();
        shoulderPress.setId(2L);
        shoulderPress.setName("Shoulder Press");

        WorkoutDayRequestDto dayDto = new WorkoutDayRequestDto(1,
                "Chest and Shoulders",
                List.of(1L, 2L));

        CreateWorkoutRequestDto requestDto = new CreateWorkoutRequestDto("Push Day",
                "Upper Body",
                List.of(dayDto));

        when(clientRepo.findByEmail(email)).thenReturn(Optional.of(client));
        when(exerciseRepo.findById(1L)).thenReturn(Optional.of(benchPress));
        when(exerciseRepo.findById(2L)).thenReturn(Optional.of(shoulderPress));

        // Act
        service.saveWorkout(requestDto, email);

        // Assert

        //Capture the argument passed into saveWorkout after the service call
        ArgumentCaptor<Workout> captor = ArgumentCaptor.forClass(Workout.class);
        verify(workoutRepo).save(captor.capture());

        Workout savedWorkout = captor.getValue();
        assertEquals("Push Day", savedWorkout.getName());
        assertEquals("Upper Body", savedWorkout.getDescription());
        assertSame(client, savedWorkout.getClient());

        WorkoutDay day = savedWorkout.getDays().get(0);

        assertEquals("Chest and Shoulders", day.getName());
        assertEquals(1, day.getDayNumber());
        assertSame(savedWorkout, day.getWorkout());

        assertEquals(2, day.getExercises().size());
        assertTrue(day.getExercises().contains(benchPress));
        assertTrue(day.getExercises().contains(shoulderPress));

        assertTrue(client.getWorkouts().contains(savedWorkout));

        verify(clientRepo).findByEmail(email);
        verify(exerciseRepo).findById(1L);
        verify(exerciseRepo).findById(2L);
    }

    @Test
    @DisplayName("Test delete workout")
    void testDeleteWorkout_whenIdAndEmailProvided_deleteWorkoutObject() {
        // Arrange
        long id = 1L;
        Client client = new Client();
        client.setId(id);
        client.setEmail(email);
        when(clientRepo.findByEmail(email)).thenReturn(Optional.of(client));

        Workout workout = new Workout();
        workout.setClient(client);
        workout.setId(2L);

        client.setWorkouts(new ArrayList<>(List.of(workout)));

        // Act
        service.deleteWorkout(2L, email);

        // Assert
        assertFalse(client.getWorkouts().contains(workout));

        verify(clientRepo).findByEmail(email);
        verify(workoutRepo).delete(workout);
    }

    @Test
    @DisplayName("Test count workout")
    void testCountWorkout_returnWorkoutCounts() {
        // Arrange
        when(workoutRepo.count()).thenReturn(5L);

        // Act
        long count = service.countWorkout();

        // Assert
        assertEquals(5L, count);

        verify(workoutRepo).count();
    }
}
