package com.HealthApp.repo;

import java.util.List;
import com.HealthApp.model.Client;
import com.HealthApp.model.Goal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
	List<Client> findByFitnessGoal (Goal goal); //This will find the field fitnessGoal so name must match
}
