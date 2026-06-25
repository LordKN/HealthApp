package com.HealthApp.repo;

import java.util.List;

import com.HealthApp.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
	List<Client> findByFitnessGoal (Goal goal); //This will find the field fitnessGoal so name must match
	List<Client> findByStressLevel (Level stressLevel);
	List<Client> findBySleepPattern(Pattern pattern);
	List<Client> findByWorkoutPreference(WorkoutPreference preference);
	List<Client> findByBarriers(Barrier barrier);
}
