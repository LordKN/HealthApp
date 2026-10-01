package com.HealthApp.controller;

import com.HealthApp.dto.CreateWorkoutRequestDto;
import com.HealthApp.model.Workout;
import com.HealthApp.service.WorkoutService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WorkoutController {

    @Autowired
    private WorkoutService service;

    @GetMapping("/api/workouts")
    public ResponseEntity<List<Workout>> getWorkouts(Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(service.getWorkouts(email));
    }

    @GetMapping("/api/workouts/{workoutID}")
    public Workout getWorkout(@PathVariable("workoutID") Long id, Authentication authentication) {
        String email = authentication.getName();
        return service.getWorkoutById(id, email);
    }

    @GetMapping("/api/workouts/count")
    public Long countWorkouts() {
        return service.countWorkout();
    }

    @PostMapping("/api/workouts")
    public void saveWorkout(@Valid @RequestBody CreateWorkoutRequestDto workout, Authentication authentication) {
        String email = authentication.getName();
        service.saveWorkout(workout, email);
    }

    @DeleteMapping("/api/workouts/{workoutID}")
    public ResponseEntity<String> deleteWorkout(@PathVariable("workoutID") Long id, Authentication authentication) {
        String email = authentication.getName();
        service.deleteWorkout(id, email);
        return ResponseEntity.ok("Workout deleted");
    }

//    @DeleteMapping("/api/workouts")
//    public String deleteAllWorkout() {
//        service.deleteAllWorkout();
//        return "All workouts deleted";
//    }
}
