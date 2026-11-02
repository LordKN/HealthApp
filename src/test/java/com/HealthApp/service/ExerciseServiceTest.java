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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
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

    @Test
    @DisplayName("Test get all exercises")
    void testGetAllExercises_returnAllExercises() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 2);

        Exercise exercise1 = createExercise(1L, "Bench Press");
        Exercise exercise2 = createExercise(2L, "Squat");

        // 10 exercises across all pages
        Page<Exercise> exercisePage = new PageImpl<>(List.of(exercise1, exercise2)
                                                    ,pageable, 10);

        when(repo.findAll(pageable)).thenReturn(exercisePage);

        // Act
        Page<ExerciseDto> results = service.getAllExercises(pageable);

        // Assert
        assertEquals(2, results.getContent().size());
        assertEquals(10, results.getTotalElements());
        assertEquals(5, results.getTotalPages());
        assertEquals(0, results.getNumber());

        assertEquals("Bench Press", results.getContent().get(0).name());
        assertEquals("Squat", results.getContent().get(1).name());

        verify(repo).findAll(pageable);
    }

    @Test
    @DisplayName("Test get exercise by id")
    void testGetExercise_whenNameCategoryIdMuscleIdEquipmentIdPageableProvided_returnExercises() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 5);
        String name = "Bench";
        Long categoryId = 1L;
        Long muscleId = 2L;
        Long equipmentId = 3L;

        Exercise exercise = createExercise(1L, "Bench Press");
        Page<Exercise> exercisePage = new PageImpl<>(List.of(exercise),
                pageable, 1);

        when(repo.find(name, categoryId, muscleId, equipmentId, pageable)).thenReturn(exercisePage);

        // Act
        Page<ExerciseDto> results = service.getExercise(name, categoryId, muscleId, equipmentId, pageable);

        // Assert
        assertEquals(1, results.getTotalElements());
        assertEquals("Bench Press", results.getContent().getFirst().name());

        verify(repo).find(name, categoryId, muscleId, equipmentId, pageable);
    }

    @Test
    @DisplayName("Test save exercise")
    void testSaveExercise_whenExerciseObjectProvided_saveExercise() {

        // Arrange
        Exercise e = new Exercise();
        e.setName("Squat");
        when(repo.save(any(Exercise.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        service.saveExercise(e);

        // Assert
        verify(repo).save(e);

    }

    private Exercise createExercise(Long id, String name) {
        Exercise exercise = new Exercise();
        exercise.setId(id);
        exercise.setName(name);
        exercise.setDescription("Strength exercise");

        Category category = new Category();
        category.setName("Strength");
        exercise.setCategory(category);

        exercise.setEquipment(Set.of());
        exercise.setPrimaryMuscles(Set.of());
        exercise.setSecondaryMuscles(Set.of());

        return exercise;
    }
}
