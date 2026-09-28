package com.HealthApp.service;

import com.HealthApp.dto.ExerciseDto;
import com.HealthApp.model.Equipment;
import com.HealthApp.model.Exercise;
import com.HealthApp.model.Muscle;
import com.HealthApp.repo.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ExerciseService {

    @Autowired
    private ExerciseRepository repo;

    public Page<ExerciseDto> getAllExercises(Pageable pageable) {

        Page<Exercise> exercises = repo.findAll(pageable);

        return exercises.map(this::convertToDto);
    }

    public ExerciseDto getExerciseById (Long id) {
        Exercise exercise = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Exercise not found"));

        return convertToDto(exercise);
    }

    public void saveExercise (Exercise exercise) {

        validateExercise(exercise);
        repo.save(exercise);
    }

    public void deleteExercise (Long id) {

        if (!repo.existsById(id)) {
            throw new RuntimeException("Exercise not found to be deleted");
        }
        repo.deleteById(id);
    }

    public long countExercises() {
        return repo.count();
    }

    public void deleteAllExercises() {
        if (repo.count() == 0) {
            throw new RuntimeException("No exercise to be deleted");
        }
        repo.deleteAll();
    }

    private void validateExercise(Exercise exercise) {
        if (exercise == null) {
            throw new RuntimeException("Exercise cannot be null");
        }

        if (exercise.getName() == null || exercise.getName().isBlank()) {
            throw new RuntimeException("Exercise name is required");
        }

        if (exercise.getName().length() > 100) {
            throw new RuntimeException("Exercise name must be under 100 characters");
        }

        if (exercise.getDescription() != null && exercise.getDescription().length() > 2000) {
            throw new RuntimeException("Exercise description must be under 2000 characters");
        }
    }

    private ExerciseDto convertToDto(Exercise exercise) {
        Set<String> primaryMuscles = exercise.getPrimaryMuscles()
                .stream()
                .map(Muscle::getName)
                .collect(Collectors.toSet());

        Set<String> secondaryMuscles = exercise.getSecondaryMuscles()
                .stream()
                .map(Muscle::getName)
                .collect(Collectors.toSet());

        Set<String> equipments = exercise.getEquipment()
                .stream()
                .map(Equipment::getName)
                .collect(Collectors.toSet());

        return new ExerciseDto(exercise.getId(),
                                exercise.getName(),
                                exercise.getDescription(),
                                exercise.getExerciseImageUrl(),
                                exercise.getVideoUrl(),
                                exercise.getCategory().getName(),
                                primaryMuscles,
                                secondaryMuscles,
                                equipments);
    }
}
