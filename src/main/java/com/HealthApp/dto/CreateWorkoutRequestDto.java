package com.HealthApp.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CreateWorkoutRequestDto(

        @NotBlank(message = "Workout name is required")
        String name,

        @Size(max = 2000, message = "Description must be under 2000 character")
        String description,

        @NotNull(message = "Workout days are required")
        @Size(min = 7, max = 7, message = "Workout must contain exactly 7 days")
        List<@Valid WorkoutDayRequestDto> days) {
}
