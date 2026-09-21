package com.HealthApp.service;

import com.HealthApp.dto.CategoryResponse;
import com.HealthApp.dto.MuscleResponse;
import com.HealthApp.dto.WgerCategoryDto;
import com.HealthApp.dto.WgerMuscleDto;
import com.HealthApp.model.Category;
import com.HealthApp.model.Muscle;
import com.HealthApp.repo.CategoryRepository;
import com.HealthApp.repo.MuscleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class ExerciseImportService {

    @Autowired
    private RestClient client;

    @Autowired
    private CategoryRepository categoryRepo;

    @Autowired
    private MuscleRepository muscleRepo;

    public void importCategories() {
        String next = "exercisecategory/";
        while (next != null) {
            CategoryResponse response = client.get()
                    .uri(next)
                    .retrieve()
                    .body(CategoryResponse.class);

            for (WgerCategoryDto dto : response.results()) {
                Category category = categoryRepo.findByWgerId(dto.id())
                        .orElseGet(Category::new);
                category.setWgerId(dto.id());
                category.setName(dto.name());
                categoryRepo.save(category);
            }
            next = response.next();
        }
    }

    public void importMuscles() {
        String next = "muscle/";
        while (next != null) {
            MuscleResponse response = client.get()
                    .uri(next)
                    .retrieve()
                    .body(MuscleResponse.class);

            for (WgerMuscleDto dto : response.results()) {
                Muscle muscle = muscleRepo.findByWgerId(dto.id())
                        .orElseGet(Muscle::new);
                muscle.setWgerId(dto.id());
                muscle.setName(dto.name());
                muscle.setName_en(dto.name_en());
                muscle.setIs_front(dto.is_front());
                muscle.setImageUrlMain(dto.image_url_main());
                muscle.setImageUrlSecondary(dto.image_url_secondary());

                muscleRepo.save(muscle);
            }

            next = response.next();
        }
    }
}
