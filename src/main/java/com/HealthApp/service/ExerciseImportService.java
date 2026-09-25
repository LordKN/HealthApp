package com.HealthApp.service;

import com.HealthApp.dto.*;
import com.HealthApp.model.Category;
import com.HealthApp.model.Equipment;
import com.HealthApp.model.Exercise;
import com.HealthApp.model.Muscle;
import com.HealthApp.repo.CategoryRepository;
import com.HealthApp.repo.EquipmentRepository;
import com.HealthApp.repo.ExerciseRepository;
import com.HealthApp.repo.MuscleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.HashSet;
import java.util.Set;

@Service
public class ExerciseImportService {

    @Autowired
    private RestClient client;

    @Autowired
    private CategoryRepository categoryRepo;

    @Autowired
    private MuscleRepository muscleRepo;

    @Autowired
    private EquipmentRepository equipmentRepo;

    @Autowired
    private ExerciseRepository exerciseRepo;

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

    public void importEquipments() {
        String next = "equipment/";
        while (next != null) {
            EquipmentResponse response = client.get()
                    .uri(next)
                    .retrieve()
                    .body(EquipmentResponse.class);

            for (WgerEquipmentDto dto : response.results()) {
                Equipment equipment = equipmentRepo.findByWgerId(dto.id())
                        .orElseGet(Equipment::new);
                equipment.setWgerId(dto.id());
                equipment.setName(dto.name());

                equipmentRepo.save(equipment);
            }

            next = response.next();
        }
    }

    public void importExercises() {
        String next = "exerciseinfo/";
        while (next != null) {
            ExerciseResponse response = client.get()
                    .uri(next)
                    .retrieve()
                    .body(ExerciseResponse.class);

            for (WgerExerciseInfoDto dto : response.results()) {

                // Create an exercise and set its name
                Exercise exercise = exerciseRepo.findByWgerId(dto.id())
                        .orElseGet(Exercise::new);
                exercise.setWgerId(dto.id());

                WgerTranslationDto englishTranslation = null;
                for (WgerTranslationDto translation : dto.translations()) {
                    if (translation.language() == 2) {
                        englishTranslation = translation;
                        break;
                    }
                }
                if (englishTranslation == null) {
                    continue;
                }

                exercise.setName(englishTranslation.name());
                exercise.setDescription(englishTranslation.description());

                // Add category
                Category category = categoryRepo.findByWgerId(dto.category().id())
                        .orElseThrow(() -> new RuntimeException("Category not found"));

                exercise.setCategory(category);

                // Add primary muscles
                Set<Muscle> primaryMuscles = new HashSet<>();
                for (WgerMuscleDto muscleDto : dto.muscles()) {
                    Muscle muscle = muscleRepo.findByWgerId(muscleDto.id())
                            .orElseThrow(() -> new RuntimeException("Muscle not found"));
                    primaryMuscles.add(muscle);
                }
                exercise.setPrimaryMuscles(primaryMuscles);

                // Add secondary muscles
                Set<Muscle> secondaryMuscles = new HashSet<>();
                for (WgerMuscleDto muscleDto : dto.muscles_secondary()) {
                    Muscle muscle = muscleRepo.findByWgerId(muscleDto.id())
                            .orElseThrow(() -> new RuntimeException("Secondary muscle not found"));
                    secondaryMuscles.add(muscle);
                }
                exercise.setSecondaryMuscles(secondaryMuscles);

                // Add equipments
                Set<Equipment> equipments = new HashSet<>();
                for (WgerEquipmentDto equipmentDto : dto.equipment()) {
                    Equipment equipment = equipmentRepo.findByWgerId(equipmentDto.id())
                            .orElseThrow(() -> new RuntimeException("Equipment not found"));
                    equipments.add(equipment);
                }
                exercise.setEquipment(equipments);

                // Set image
                exercise.setExerciseImageUrl(null);
                for (WgerExerciseImageDto imageDto : dto.images()) {
                    if (imageDto.is_main()) {
                        exercise.setExerciseImageUrl(imageDto.image());
                        break;
                    }
                }

                // Set video
                exercise.setVideoUrl(null);
                for (WgerVideoDto videoDto : dto.videos()) {
                    if (exercise.getVideoUrl() == null) {
                        exercise.setVideoUrl(videoDto.video());
                    }
                    if (videoDto.is_main()) {
                        exercise.setVideoUrl(videoDto.video());
                        break;
                    }
                }

                // Save
                exerciseRepo.save(exercise);
            }
            next = response.next();
        }
    }
}
