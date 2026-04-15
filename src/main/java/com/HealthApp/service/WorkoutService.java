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
        repo.save(workout);
    }

    public void deleteWorkout(Long id) {
        repo.deleteById(id);
    }

    public long countWorkout() {
        return repo.count();
    }

    public void deleteAllWorkout() {
        repo.deleteAll();
    }
}
