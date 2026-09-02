package com.HealthApp.controller;

import com.HealthApp.model.Equipment;
import com.HealthApp.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EquipmentController {

    @Autowired
    private EquipmentService service;

    @GetMapping("/api/equipment/{equiId}")
    public ResponseEntity<Equipment> getEquipmentById (@PathVariable("equiId") Long id) {
        Equipment equipment = service.getEquipmentById(id);

        return ResponseEntity.ok(equipment);
    }

    @GetMapping("/api/equipment/wger/{wgerId}")
    public ResponseEntity<Equipment> getEquipmentByWgerId(@PathVariable("wgerId") int id) {
        Equipment equipment = service.getEquipmentByWgerId(id);

        return ResponseEntity.ok(equipment);
    }

    @GetMapping("/api/equipment/search/{equiName}")
    public ResponseEntity<List<Equipment>> getEquipmentByName(@PathVariable("equiName") String name) {
        List<Equipment> equipmentList = service.getEquipmentByName(name);

        return ResponseEntity.ok(equipmentList);
    }

    @PostMapping("/api/equipment")
    public ResponseEntity<Equipment> saveEquipment(@RequestBody Equipment equipment) {
        Equipment saveEquipment = service.saveEquipment(equipment);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveEquipment);
    }

    @DeleteMapping("/api/equipment/{equiId}")
    public ResponseEntity<Void> deleteEquipment(@PathVariable("equiId") Long id) {
        service.deleteEquipmentById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/api/equipment/")
    public ResponseEntity<Void> deleteAllEquipment() {
        service.deleteAllEquipment();
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/api/equipment/{equiId}")
    public ResponseEntity<Equipment> updateEquipment(@PathVariable("equiId") Long id, @RequestBody Equipment equipment) {
        Equipment updatedEquipment = service.updateEquipment(id, equipment);
        return ResponseEntity.ok(updatedEquipment);
    }
}
