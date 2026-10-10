package com.HealthApp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Category {

    public Category() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (unique = true, nullable = false)
    private Integer wgerId;

    @Column(nullable = false)
    private String name;

    @JsonIgnore     // Do not reverse display the exercises belong to this category in the returned json
    @OneToMany(mappedBy = "category")
    private Set<Exercise> exercises;

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

    public Long getId() {
        return id;
    }

    public Integer getWgerId() {
        return wgerId;
    }

    public void setWgerId(Integer wgerId) {
        this.wgerId = wgerId;
    }
}
