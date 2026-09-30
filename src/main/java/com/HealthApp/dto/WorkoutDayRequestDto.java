package com.HealthApp.dto;

import java.util.List;

public record WorkoutDayRequestDto (Integer dayNumber, String name, List<Long> exerciseIds){
}
