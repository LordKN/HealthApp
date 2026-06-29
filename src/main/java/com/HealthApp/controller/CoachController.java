package com.HealthApp.controller;

import com.HealthApp.model.Coach;
import com.HealthApp.service.CoachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CoachController {

    @Autowired
    private CoachService service;

    @GetMapping("/api/coaches")
    public List<Coach> getAllCoaches() {
        return service.findAllCoaches();
    }

    @GetMapping("/api/coaches/{coachId}")
    public Coach getCoach(@PathVariable("coachId") Long id) {
        return service.findCoachById(id);
    }

    @GetMapping("/api/coaches/count")
    public Long countCoaches() {
        return service.countCoaches();
    }

    @PostMapping("/api/coaches")
    public ResponseEntity<Coach> saveCoach(@RequestBody Coach coach) {

        Coach savedCoach = service.saveCoach(coach);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCoach);
    }

    @PutMapping("/api/coaches/{id}")
    public ResponseEntity<Coach> updateCoach(@PathVariable Long id, @RequestBody Coach updatedCoach) {
        Coach newCoach = service.updateCoach(id, updatedCoach);
        return ResponseEntity.ok(newCoach);
    }

    @DeleteMapping("/api/coaches/{coachID}")
    public ResponseEntity<Void> deleteCoach(@PathVariable("coachID") Long id) {
        service.deleteCoach(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/api/coaches")
    public ResponseEntity<Void> deleteAllCoaches() {
        service.deleteAllCoaches();
        return ResponseEntity.noContent().build();
    }
}
