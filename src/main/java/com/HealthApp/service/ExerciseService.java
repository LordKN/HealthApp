package com.HealthApp.service;

import com.HealthApp.model.Exercise;
import com.HealthApp.repo.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExerciseService {

    @Autowired
    private ExerciseRepository repo;

    public List<Exercise> getAllExercises() {
        return repo.findAll();
    }

    public Exercise getExerciseById (Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Exercise not found"));
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
}
