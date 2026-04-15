package com.HealthApp.repo;

import com.HealthApp.model.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkoutRepository extends JpaRepository<Workout, Long>{
}
