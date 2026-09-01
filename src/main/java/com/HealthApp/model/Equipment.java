package com.HealthApp.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (unique = true, nullable = false)
    private Integer wgerId;

    @Column (nullable = false)
    private String name;

    @ManyToMany(mappedBy = "equipment")
    private Set<Exercise> exercises;

    public Long getId() {
        return id;
    }

    public Integer getWgerId() {
        return wgerId;
    }

    public void setWgerId(Integer wgerId) {
        this.wgerId = wgerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(Set<Exercise> exercises) {
        this.exercises = exercises;
    }
}
