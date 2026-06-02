package com.HealthApp.controller;

import com.HealthApp.model.Certificate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "redirect:/index.html";
    }

    @GetMapping("/about")
    public String about() {
        return "redirect:/html/about.html";
    }

    @GetMapping("/login")
    public String login() {
        return "redirect:/html/login.html";
    }
}
