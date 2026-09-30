package com.HealthApp.model;

import com.HealthApp.controller.WorkoutController;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "workout_day")
public class WorkoutDay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer dayNumber;

    private String name;

    @ManyToMany
    @JoinTable(
            name = "workout_day_exercise",
            joinColumns = @JoinColumn(name = "workout_day_id"),
            inverseJoinColumns = @JoinColumn(name = "exercise_id")
    )
    private List<Exercise> exercises = new ArrayList<>();

    @ManyToOne
    @JoinColumn (name = "workout_id")
    private Workout workout;    //Many workout days can belong to one workout:
                                // Push, Pull, Legs (3 days) can belong to a workout plan


    public Long getId() {
        return id;
    }

    public Workout getWorkout() {
        return workout;
    }

    public void setWorkout(Workout workout) {
        this.workout = workout;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(Integer dayNumber) {
        this.dayNumber = dayNumber;
    }
}
