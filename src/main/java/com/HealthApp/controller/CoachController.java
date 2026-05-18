package com.HealthApp.controller;

import com.HealthApp.model.Coach;
import com.HealthApp.service.CoachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CoachController {

    @Autowired
    private CoachService service;

    @GetMapping("/api/coaches")
    public List<Coach> getAllCoaches() {
        return service.findAllCoaches();
    }
}
