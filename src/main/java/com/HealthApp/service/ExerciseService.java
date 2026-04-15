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
        repo.save(exercise);
    }

    public void deleteExercise (Long id) {
        repo.deleteById(id);
    }

    public long countCertificate() {
        return repo.count();
    }

    public void deleteAllCertificate() {
        repo.deleteAll();
    }
}
