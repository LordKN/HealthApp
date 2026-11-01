package com.HealthApp.service;

import com.HealthApp.dto.ExerciseDto;
import com.HealthApp.model.Category;
import com.HealthApp.model.Exercise;
import com.HealthApp.repo.ExerciseRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExerciseServiceTest {

    @Mock
    private ExerciseRepository repo;

    @InjectMocks
    private ExerciseService service;

    @Test
    @DisplayName("Test get all exercises")
    void testGetExerciseById_whenExerciseIdIsProvided_returnExerciseDtoObject() {
        // Arrange
        Category c = new Category();
        c.setWgerId(1);

        Exercise exercise = new Exercise();
        exercise.setName("Bench Press");
        exercise.setDescription("A chest workout");
        exercise.setCategory(c);
        exercise.setId(1L);

        when(repo.findById(1L))
                .thenReturn(Optional.of(exercise));

        // Act
        ExerciseDto e = service.getExerciseById(1L);

        // Assert
        assertEquals(1L, e.id());
        assertEquals("Bench Press", e.name());
        assertEquals("A chest workout", e.description());
    }
}
