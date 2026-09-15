package com.HealthApp.service;

import com.HealthApp.dto.CategoryResponse;
import com.HealthApp.dto.WgerCategoryDto;
import com.HealthApp.model.Category;
import com.HealthApp.repo.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class ExerciseImportService {

    @Autowired
    private RestClient client;

    @Autowired
    private CategoryRepository repo;

    public void importCategories() {
        String next = "exercisecategory/";
        while (next != null) {
            CategoryResponse response = client.get()
                    .uri(next)
                    .retrieve()
                    .body(CategoryResponse.class);

            for (WgerCategoryDto dto : response.results()) {
                Category category = repo.findByWgerId(dto.id())
                        .orElseGet(Category::new);
                category.setWgerId(dto.id());
                category.setName(dto.name());
                repo.save(category);
            }
            next = response.next();
        }
    }
}
