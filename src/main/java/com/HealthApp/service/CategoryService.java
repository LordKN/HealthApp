package com.HealthApp.service;

import com.HealthApp.model.Category;
import com.HealthApp.model.Client;
import com.HealthApp.repo.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repo;

    public Category findCategoryById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    public Category findByWgerId (int wgerId) {
        return repo.findByWgerId(wgerId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }
    public List<Category> findCategoryByName (String name) {
        return repo.findByNameContainingIgnoreCase(name);
    }

    public Category saveCategory(Category category) {
        return repo.save(category);
    }

    public void deleteCategoryById(Long id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("Category not found");
        }
        repo.deleteById(id);
    }

    public void deleteAllCategory() {
        if (repo.count() == 0) {
            throw new RuntimeException("No category to delete");
        }
        repo.deleteAll();
    }

    public Category updateCategory(Long id, Category updatedCategory) {
        Category existingCategory = repo.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));

        existingCategory.setName(updatedCategory.getName());
        existingCategory.setWgerId(updatedCategory.getWgerId());
        existingCategory.setExercises(updatedCategory.getExercises());

        return repo.save(existingCategory);
    }
}
