package com.HealthApp.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "exercise")
public class Exercise {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    @Column (unique = true, nullable = false)
    private Integer wgerId;

    @Column (nullable = false)
	private String name;
	@Lob
	private String description;
    private String exerciseImageUrl;
    private String videoUrl;

    @ManyToMany
    @JoinTable(
            name = "exercise_muscle",
            joinColumns = @JoinColumn(name = "exercise_id"),
            inverseJoinColumns = @JoinColumn(name = "muscle_id")
    )
    private Set<Muscle> primaryMuscles;

    @ManyToMany
    @JoinTable(
            name = "exercise_equipment",
            joinColumns = @JoinColumn(name = "exercise_id"),
            inverseJoinColumns = @JoinColumn(name = "equipment_id")
    )
    private Set<Equipment> equipment;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
	
	public Exercise() {

	}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExerciseImageUrl() {
        return exerciseImageUrl;
    }

    public void setExerciseImageUrl(String exerciseImageUrl) {
        this.exerciseImageUrl = exerciseImageUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public Set<Muscle> getPrimaryMuscles() {
        return primaryMuscles;
    }

    public void setPrimaryMuscles(Set<Muscle> primaryMuscles) {
        this.primaryMuscles = primaryMuscles;
    }

    public Set<Equipment> getEquipment() {
        return equipment;
    }

    public void setEquipment(Set<Equipment> equipment) {
        this.equipment = equipment;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}