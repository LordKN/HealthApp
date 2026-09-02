package com.HealthApp.service;

import com.HealthApp.model.Muscle;
import com.HealthApp.repo.MuscleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MuscleService {

    @Autowired
    private MuscleRepository repo;

    public Muscle getMuscleById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Muscle not found"));
    }

    public Muscle getMuscleByWgerId (int wgerId) {
        return repo.findByWgerId(wgerId)
                .orElseThrow(() -> new RuntimeException("Muscle not found"));
    }

    public List<Muscle> getMuscleByName (String name) {
        return repo.findByNameContainingIgnoreCase(name);
    }

    public Muscle saveMuscle (Muscle muscle) {
        return repo.save(muscle);
    }

    public void deleteMuscleById(Long id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("Muscle not found");
        }
        repo.deleteById(id);
    }

    public void deleteAllMuscle() {
        if (repo.count() == 0) {
            throw new RuntimeException("No muscle to delete");
        }
        repo.deleteAll();
    }

    public Muscle updateMuscle(Long id, Muscle updatedMuscle) {
        Muscle existingMuscle = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Muscle not found"));

        existingMuscle.setName(updatedMuscle.getName());
        existingMuscle.setWgerId(updatedMuscle.getWgerId());
        existingMuscle.setExercises(updatedMuscle.getExercises());
        existingMuscle.setImageUrlMain(updatedMuscle.getImageUrlMain());

        return repo.save(existingMuscle);
    }
}
