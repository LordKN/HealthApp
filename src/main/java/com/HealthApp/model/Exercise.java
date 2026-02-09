package com.HealthApp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "exercise")
public class Exercise {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	@Column(length = 2000)
	private String description;
	
	@Enumerated(EnumType.STRING)
	private Group muscleGroup;
	
	public Exercise() {
		System.out.println("Exercise created");
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

	public Group getMuscleGroup() {
		return muscleGroup;
	}

	public void setMuscleGroup(Group muscleGroup) {
		this.muscleGroup = muscleGroup;
	}

	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Exercise [id=" + id + ", name=" + name + ", description=" + description + ", muscleGroup=" + muscleGroup
				+ "]";
	}
	
	
}

enum Group { CHEST, SHOULDER, BACK, TRICEP, BICEP, LEG, CALF }