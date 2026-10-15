package com.HealthApp.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.HealthApp.model.Exercise;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    Optional<Exercise> findByWgerId (int wgerId);

    @Query("""
            SELECT e FROM Exercise e
            Where (:name IS NULL OR LOWER(e.name) LIKE LOWER(CONCAT('%', :name, '%')))
            AND (:categoryId IS NULL OR e.category.id = :categoryId)
            AND (:muscleId IS NULL OR EXISTS (SELECT m.id from Exercise ex JOIN ex.primaryMuscles m WHERE ex.id = e.id AND m.id = :muscleId))
            AND (:equipmentId IS NULL OR EXISTS (SELECT eq.id from Exercise ex JOIN ex.equipment eq WHERE ex.id = e.id AND eq.id = :equipmentId))
            """)
    Page<Exercise> find(
            @Param("name") String name,
            @Param("categoryId") Long categoryId,
            @Param("muscleId") Long muscleId,
            @Param("equipmentId") Long equipmentId,
            Pageable pageable);
}
