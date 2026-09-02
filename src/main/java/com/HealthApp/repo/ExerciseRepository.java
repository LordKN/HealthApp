package com.HealthApp.repo;


import com.HealthApp.model.Category;
import com.HealthApp.model.Equipment;
import com.HealthApp.model.Muscle;
import org.springframework.data.jpa.repository.JpaRepository;
import com.HealthApp.model.Exercise;

import java.util.List;
import java.util.Optional;


public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    Optional<Exercise> findByWgerId (int wgerId);
    Optional<Exercise> findByName (String name);
    List<Exercise> findByDescriptionContaining(String description);
    List<Exercise> findByCategory (Category category);
    List<Exercise> findByPrimaryMusclesContaining (Muscle muscle);
    List<Exercise> findByEquipmentContaining(Equipment equipment);
}
