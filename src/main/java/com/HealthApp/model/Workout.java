package com.HealthApp.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "workout")
public class Workout {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	@Column(length = 2000)
	private String description; //AVOID PUTTING desc BECAUSE IT IS SQL KEYWORD
	
	@ManyToMany
	@JoinTable(
			name = "workout_exercise",
			joinColumns = @JoinColumn(name = "workout_id"),
			inverseJoinColumns = @JoinColumn(name = "exercise_id")
			)
	private List<Exercise> exercises = new ArrayList<>();
	
	public Workout() {
		System.out.println("Workout created");
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

	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Workout [id=" + id + ", name=" + name + ", desc=" + description + ", exercises=" + exercises + "]";
	}

	public List<Exercise> getExercises() {
		return exercises;
	}

	public void setExercises(List<Exercise> exercises) {
		this.exercises = exercises;
	}
}