package com.HealthApp.controller;

import com.HealthApp.service.ExerciseImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImportController {

    @Autowired
    private ExerciseImportService service;

    @PostMapping("/api/admin/import/categories")
    public ResponseEntity<String> importCategories() {
        service.importCategories();
        return ResponseEntity.ok("Categories imported successfully");
    }
}
