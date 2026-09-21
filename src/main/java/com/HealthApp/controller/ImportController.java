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

    @PostMapping("/api/admin/import/muscles")
    public ResponseEntity<String> importMuscles() {
        service.importMuscles();
        return ResponseEntity.ok("Muscles imported successfully");
    }

    @PostMapping("/api/admin/import/equipments")
    public ResponseEntity<String> importEquipments() {
        service.importEquipments();
        return ResponseEntity.ok("Equipments imported successfully");
    }
}
