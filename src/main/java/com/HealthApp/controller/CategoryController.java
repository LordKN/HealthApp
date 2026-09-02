package com.HealthApp.controller;

import com.HealthApp.model.Category;
import com.HealthApp.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CategoryController {

    @Autowired
    private CategoryService service;

    @GetMapping("/api/categories/{catId}")
    public ResponseEntity<Category> getCategoryById(@PathVariable("catId") Long id) {
        Category category = service.findCategoryById(id);
        return ResponseEntity.ok(category);
    }

    @GetMapping("/api/categories/wger/{wgerId}")
    public ResponseEntity<Category> getCategoryByWgerId(@PathVariable("wgerId") int wgerId) {
        Category category = service.findByWgerId(wgerId);
        return ResponseEntity.ok(category);
    }

    @GetMapping("/api/categories/search/{catName}")
    public ResponseEntity<List<Category>> getCategoryByName(@PathVariable("catName") String name) {
        List<Category> categories = service.findCategoryByName(name);
        return ResponseEntity.ok(categories);
    }

    @PostMapping("/api/categories/")
    public ResponseEntity<Category> saveCategory(@RequestBody Category category) {
        Category savedCategory = service.saveCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
    }

    @PutMapping("/api/categories/{catId}")
    public ResponseEntity<Category> updateCategory(@PathVariable("catId") Long id, @RequestBody Category category) {
        Category updatedCategory = service.updateCategory(id, category);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/api/categories/{catId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("catId") Long id) {
        service.deleteCategoryById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/api/categories/")
    public ResponseEntity<Void> deleteAllCategories() {
        service.deleteAllCategory();
        return ResponseEntity.noContent().build();
    }
}
