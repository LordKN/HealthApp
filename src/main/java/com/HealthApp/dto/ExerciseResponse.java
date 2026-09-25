package com.HealthApp.dto;

import java.util.List;

public record ExerciseResponse(int count,
                               String next,
                               String previous,
                               List<WgerExerciseInfoDto> results) {
}
