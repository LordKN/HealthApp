package com.HealthApp.controller;

import com.HealthApp.model.MedicalHistory;
import com.HealthApp.service.MedicalHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MedicalHistoryController {

    @Autowired
    private MedicalHistoryService service;

    @GetMapping("/api/histories")
    public ResponseEntity<List<MedicalHistory>> getAllMedicalHistory() {

        return ResponseEntity.ok(service.getAllMedicalHistory());
    }

    @GetMapping("/api/history/{histID}")
    public MedicalHistory getMedicalHistory(@PathVariable("histID") Long id) {
        return service.getMedicalHistoryById(id);
    }

    @GetMapping("/api/histories/count")
    public Long countMedicalHistory() {
        return service.countMedicalHistory();
    }

    @PostMapping("/api/history")
    public void saveMedicalHistory(@RequestBody MedicalHistory history) {
        service.saveMedicalHistory(history);
    }

    @DeleteMapping("/api/history/{histID}")
    public String deleteMedicalHistory(@PathVariable("histID") Long id) {
        service.deleteMedicalHistory(id);
        return "Medical History deleted";
    }

    @DeleteMapping("/api/histories")
    public String deleteAllMedicalHistory() {
        service.deleteAllMedicalHistory();
        return "All medical histories deleted";
    }
}
