package com.HealthApp.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Muscle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (unique = true, nullable = false)
    private Integer wgerId;

    @Column (nullable = false)
    private String name;

    @Column
    private String name_en;

    @Column
    private boolean is_front;

    @ManyToMany(mappedBy = "primaryMuscles")
    private Set<Exercise> exercises;

    private String imageUrlMain;
    private String imageUrlSecondary;

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

    public String getImageUrlMain() {
        return imageUrlMain;
    }

    public void setImageUrlMain(String imageUrlMain) {
        this.imageUrlMain = imageUrlMain;
    }

    public String getImageUrlSecondary() {
        return imageUrlSecondary;
    }

    public void setImageUrlSecondary(String imageUrlSecondary) {
        this.imageUrlSecondary = imageUrlSecondary;
    }

    public String getName_en() {
        return name_en;
    }

    public void setName_en(String name_en) {
        this.name_en = name_en;
    }

    public boolean isIs_front() {
        return is_front;
    }

    public void setIs_front(boolean is_front) {
        this.is_front = is_front;
    }
}
