package com.HealthApp.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.HealthApp.model.Exercise;
import com.HealthApp.model.Group;


public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
	List<Exercise> findByMuscleGroup(Group group); //This will find muscleGroup so name must match
}
