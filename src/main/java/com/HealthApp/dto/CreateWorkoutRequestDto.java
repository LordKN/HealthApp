package com.HealthApp.dto;

import java.util.List;

public record CreateWorkoutRequestDto(String name, String description, List<WorkoutDayRequestDto> days) {
}
