package com.HealthApp.controller;

import com.HealthApp.model.Workout;
import com.HealthApp.service.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WorkoutController {

    @Autowired
    private WorkoutService service;

    @GetMapping("/api/workouts")
    public List<Workout> getAllWorkouts() {
        return service.getAllWorkout();
    }
}
