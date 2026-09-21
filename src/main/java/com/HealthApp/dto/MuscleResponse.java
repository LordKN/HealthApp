package com.HealthApp.dto;

import java.util.List;

public record MuscleResponse (int count,
                            String next,
                            String previous,
                            List<WgerMuscleDto> results) {
}
