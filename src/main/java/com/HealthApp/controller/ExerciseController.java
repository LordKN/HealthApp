package com.HealthApp.controller;

import com.HealthApp.model.Exercise;
import com.HealthApp.service.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ExerciseController {

    @Autowired
    private ExerciseService service;

    @GetMapping("/api/exercises")
    public ResponseEntity<List<Exercise>> getAllExercise() {

        return ResponseEntity.ok(service.getAllExercises());
    }

    @GetMapping("/api/exercise/{exerID}")
    public Exercise getExercise(@PathVariable("exerID") Long id) {
        return service.getExerciseById(id);
    }

    @GetMapping("/api/exercises/count")
    public Long countExercise() {
        return service.countExercises();
    }

    @PostMapping("/api/exercise")
    public void saveExercise(@RequestBody Exercise exercise) {
        service.saveExercise(exercise);
    }

    @DeleteMapping("/api/exercise/{exerID}")
    public String deleteExercise(@PathVariable("exerID") Long id) {
        service.deleteExercise(id);
        return "Exercise Deleted";
    }

    @DeleteMapping("/api/exercises")
    public String deleteAllExercises() {
        service.deleteAllExercises();
        return "All exercises deleted";
    }
}
