package com.HealthApp.exception;

public class ExerciseNotFoundException extends RuntimeException{

    public ExerciseNotFoundException(Long id) {
        super("Exercise not found: " + id);
    }
}
