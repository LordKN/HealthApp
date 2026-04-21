package com.HealthApp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/api/message")
    public String getMessage() {
        return "Hello from backend";
    }
}
