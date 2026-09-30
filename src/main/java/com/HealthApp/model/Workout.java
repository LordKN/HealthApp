package com.HealthApp.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "workout")
public class Workout {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	@Column(length = 2000)
	private String description; //AVOID PUTTING desc BECAUSE IT IS SQL KEYWORD
	
	@OneToMany(
            mappedBy = "workout",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<WorkoutDay> days = new ArrayList<>();
	
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

    public List<WorkoutDay> getDays() {
        return days;
    }

    public void setDays(List<WorkoutDay> days) {
        this.days = days;
    }

    public void addDay(WorkoutDay day) {
        days.add(day);
        day.setWorkout(this);
    }
}