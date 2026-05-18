package com.HealthApp.controller;

import com.HealthApp.model.Coach;
import com.HealthApp.service.CoachService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/api/coach/{coachId}")
    public Coach getCoach(@PathVariable("coachId") Long id) {
        return service.findCoachById(id);
    }

    @GetMapping("/api/coaches/count")
    public Long countCoaches() {
        return service.countCoaches();
    }

    @PostMapping("/api/coach")
    public void saveCoach(@RequestBody Coach coach) {
        service.saveCoach(coach);
    }

    @DeleteMapping("/api/coach/{coachID}")
    public String deleteCoach(@PathVariable("coachID") Long id) {
        service.deleteCoach(id);
        return "Coach deleted";
    }

    @DeleteMapping("/api/coaches")
    public String deleteAllCoaches() {
        service.deleteAllCoaches();
        return "All coaches deleted";
    }
}
