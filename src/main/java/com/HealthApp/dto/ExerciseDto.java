package com.HealthApp.dto;

import java.util.Set;

public record ExerciseDto(Long id,
                          String name,
                          String description,
                          String exerciseImageUrl,
                          String videoUrl,
                          String name1,
                          Set<String> primaryMuscles,
                          Set<String> secondaryMuscles,
                          Set<String> equipments) {
}
