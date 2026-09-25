package com.HealthApp.dto;

import java.util.List;

public record WgerExerciseInfoDto(int id,
                                  WgerCategoryDto category,
                                  List<WgerMuscleDto> muscles,
                                  List<WgerMuscleDto> muscles_secondary,
                                  List<WgerEquipmentDto> equipment,
                                  List<WgerTranslationDto> translations,
                                  List<WgerExerciseImageDto> images,
                                  List<WgerVideoDto> videos){
}
