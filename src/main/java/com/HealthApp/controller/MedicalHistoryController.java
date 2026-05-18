package com.HealthApp.controller;

import com.HealthApp.model.MedicalHistory;
import com.HealthApp.service.MedicalHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MedicalHistoryController {

    @Autowired
    private MedicalHistoryService service;

    @GetMapping("/api/histories")
    public List<MedicalHistory> getAllMedicalHistory() {
        return service.getAllMedicalHistory();
    }
}
