package com.HealthApp.repo;

import com.HealthApp.model.Muscle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MuscleRepository extends JpaRepository<Muscle, Long> {
    Optional<Muscle> findByWgerId (int wgerId);
    List<Muscle> findByNameContainingIgnoreCase(String name);

}
