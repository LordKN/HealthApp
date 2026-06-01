package com.HealthApp.service;

import com.HealthApp.model.Workout;
import com.HealthApp.repo.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkoutService {

    @Autowired
    private WorkoutRepository repo;

    public List<Workout> getAllWorkout() {
        return repo.findAll();
    }

    public Workout getWorkoutById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Cannot find workout"));
    }

    public void saveWorkout (Workout workout) {

        validateWorkout(workout);
        repo.save(workout);
    }

    public void deleteWorkout(Long id) {

        if (!repo.existsById(id)) {
            throw new RuntimeException("No workout found to be deleted");
        }
        repo.deleteById(id);
    }

    public long countWorkout() {
        return repo.count();
    }

    public void deleteAllWorkout() {

        if (countWorkout() == 0) {
            throw new RuntimeException("No workout to be deleted");
        }
        repo.deleteAll();
    }

    private void validateWorkout(Workout workout) {
        if (workout == null) {
            throw new RuntimeException("Workout cannot be null");
        }

        if (workout.getName() == null || workout.getName().isBlank()) {
            throw new RuntimeException("Workout name is required");
        }

        if (workout.getName().length() > 100) {
            throw new RuntimeException("Workout name must be under 100 characters");
        }

        if (workout.getDescription() != null && workout.getDescription().length() > 2000) {
            throw new RuntimeException("Workout description must be under 2000 characters");
        }

        if (workout.getExercises() == null) {
            throw new RuntimeException("Exercises list cannot be null");
        }
    }
}
