package com.HealthApp.controller;

import com.HealthApp.dto.ExerciseDto;
import com.HealthApp.model.Exercise;
import com.HealthApp.service.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ExerciseController {

    @Autowired
    private ExerciseService service;

    @GetMapping("/api/exercises")
    public ResponseEntity<Page<ExerciseDto>> getAllExercise(@PageableDefault(size = 20) Pageable pageable) {

        return ResponseEntity.ok(service.getAllExercises(pageable));
    }

    @GetMapping("/api/exercise/{exerID}")
    public ResponseEntity<ExerciseDto> getExercise(@PathVariable("exerID") Long id) {
        ExerciseDto exercise = service.getExerciseById(id);

        return ResponseEntity.ok(service.getExerciseById(id));
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
