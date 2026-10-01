package com.HealthApp.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.List;

public record WorkoutDayRequestDto (

        @NotNull
        Integer dayNumber,

        @Size(max = 100, message = "Day name must be under 100 characters")
        String name,

        @NotNull(message = "Exercise list cannot be null")
        List<@NotNull @Positive Long> exerciseIds){
}
