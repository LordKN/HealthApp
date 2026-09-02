package com.HealthApp.service;

import com.HealthApp.model.Equipment;
import com.HealthApp.repo.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentService {

    @Autowired
    private EquipmentRepository repo;

    public Equipment getEquipmentById (Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipment not found"));
    }

    public Equipment getEquipmentByWgerId (int wgerId) {
        return repo.findByWgerId(wgerId)
                .orElseThrow(() -> new RuntimeException("Equipment not found"));
    }

    public List<Equipment> getEquipmentByName (String name) {
        return repo.findByNameContainingIgnoreCase(name);
    }

    public Equipment saveEquipment (Equipment equipment) {
        return repo.save(equipment);
    }

    public void deleteEquipmentById (Long id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("Equipment not found");
        }
        repo.deleteById(id);
    }

    public void deleteAllEquipment() {
        if (repo.count() == 0) {
            throw new RuntimeException("No equipment to delete");
        }
        repo.deleteAll();
    }

    public Equipment updateEquipment(Long id, Equipment updatedEquipment) {
        Equipment existingEquipment = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipment not found"));

        existingEquipment.setName(updatedEquipment.getName());
        existingEquipment.setWgerId(updatedEquipment.getWgerId());
        existingEquipment.setExercises(updatedEquipment.getExercises());

        return repo.save(existingEquipment);
    }
}
