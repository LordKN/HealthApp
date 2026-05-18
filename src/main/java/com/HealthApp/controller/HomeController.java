package com.HealthApp.controller;

import com.HealthApp.model.Certificate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HomeController {

    @GetMapping("/api/message")
    public String getMessage() {
        return "Hello from backend";
    }
}
