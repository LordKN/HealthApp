package com.HealthApp.controller;

import com.HealthApp.model.Exercise;
import com.HealthApp.service.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ExerciseController {

    @Autowired
    private ExerciseService service;

    @GetMapping("/api/exercises")
    public List<Exercise> getAllExercise() {
        return service.getAllExercises();
    }
}
