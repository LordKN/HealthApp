package com.HealthApp.controller;

import com.HealthApp.dto.CreateWorkoutRequestDto;
import com.HealthApp.model.Workout;
import com.HealthApp.service.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WorkoutController {

    @Autowired
    private WorkoutService service;

    @GetMapping("/api/workouts")
    public ResponseEntity<List<Workout>> getAllWorkouts() {

        return ResponseEntity.ok(service.getAllWorkout());
    }

    @GetMapping("/api/workouts/{workoutID}")
    public Workout getWorkout(@PathVariable("workoutID") Long id) {
        return service.getWorkoutById(id);
    }

    @GetMapping("/api/workouts/count")
    public Long countWorkouts() {
        return service.countWorkout();
    }

    @PostMapping("/api/workouts")
    public void saveWorkout(@RequestBody CreateWorkoutRequestDto workout) {
        service.saveWorkout(workout);
    }

    @DeleteMapping("/api/workouts/{workoutID}")
    public String deleteWorkout(@PathVariable("workoutID") Long id) {
        service.deleteWorkout(id);
        return "Workout deleted";
    }

    @DeleteMapping("/api/workouts")
    public String deleteAllWorkout() {
        service.deleteAllWorkout();
        return "All workouts deleted";
    }
}
