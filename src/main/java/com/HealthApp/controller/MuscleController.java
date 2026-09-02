package com.HealthApp.controller;

import com.HealthApp.model.Muscle;
import com.HealthApp.service.MuscleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MuscleController {

    @Autowired
    private MuscleService service;

    @GetMapping("/api/muscles/{musId}")
    public ResponseEntity<Muscle> getMuscleById (@PathVariable("musId") Long id) {
        Muscle muscle = service.getMuscleById(id);

        return ResponseEntity.ok(muscle);
    }

    @GetMapping("/api/muscles/wger/{wgerId}")
    public ResponseEntity<Muscle> getMuscleByWgerId(@PathVariable("wgerId") int wgerId) {
        Muscle muscle = service.getMuscleByWgerId(wgerId);

        return ResponseEntity.ok(muscle);
    }

    @GetMapping("/api/muscles/search/{musName}")
    public ResponseEntity<List<Muscle>> getMuscleByName(@PathVariable("musName") String name) {
        List<Muscle> muscles = service.getMuscleByName(name);

        return ResponseEntity.ok(muscles);
    }

    @PostMapping("/api/muscles")
    public ResponseEntity<Muscle> saveMuscle(@RequestBody Muscle muscle) {
        Muscle saveMuscle = service.saveMuscle(muscle);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveMuscle);
    }

    @DeleteMapping("/api/muscles/{musId}")
    public ResponseEntity<Void> deleteMuscleById(@PathVariable("musId") Long id) {
        service.deleteMuscleById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/api/muscles/")
    public ResponseEntity<Void> deleteAllMuscles() {
        service.deleteAllMuscle();
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/api/muscles/{musId}")
    public ResponseEntity<Muscle> updateMuscle(@PathVariable("musId") Long id, @RequestBody Muscle muscle) {
        Muscle updatedMuscle = service.updateMuscle(id, muscle);
        return ResponseEntity.ok(updatedMuscle);
    }
}
