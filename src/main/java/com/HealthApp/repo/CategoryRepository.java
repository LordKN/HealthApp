package com.HealthApp.repo;

import com.HealthApp.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByWgerId (int wgerId);
    List<Category> findByNameContainingIgnoreCase(String name);
}
