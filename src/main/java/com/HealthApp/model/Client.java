package com.HealthApp.model;

import java.util.List;

import jakarta.persistence.*;
import org.hibernate.jdbc.Work;

import java.util.ArrayList;

@Entity
@Table(name = "client")
public class Client extends Person {


    //One client has many medical history
    @OneToMany(cascade = CascadeType.ALL)
    private List<MedicalHistory> med = new ArrayList<>();

	@Enumerated(EnumType.STRING)
	private Level activityLevel;

	@Enumerated(EnumType.STRING)
	private WorkoutPreference workoutPreference;

	@Enumerated(EnumType.STRING)
	private Barrier barriers;
	
	@Enumerated(EnumType.STRING)
	private Goal fitnessGoal;
	
	
	private Double height;
	private Double weight;
	private Double bodyFat;
	private String availability;

	@Enumerated(EnumType.STRING)
	private Pattern sleepPattern;
	
	@Enumerated(EnumType.STRING)
	private Level stressLevel;
	
	private boolean waiverAccepted;
	
	public Client () {

	}

	public List<MedicalHistory> getMed() {
		return med;
	}

	public void setMed(List<MedicalHistory> med) {
		this.med = med;
	}

	public Level getActivityLevel() {
		return activityLevel;
	}

	public void setActivityLevel(Level activityLevel) {
		this.activityLevel = activityLevel;
	}

	public WorkoutPreference getWorkoutPreference() {
		return workoutPreference;
	}

	public void setWorkoutPreference(WorkoutPreference workoutPreference) {
		this.workoutPreference = workoutPreference;
	}

	public Barrier getBarriers() {
		return barriers;
	}

	public void setBarriers(Barrier barriers) {
		this.barriers = barriers;
	}

	public Goal getFitnessGoal() {
		return fitnessGoal;
	}

	public void setFitnessGoal(Goal fitnessGoal) {
		this.fitnessGoal = fitnessGoal;
	}

	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Double getBodyFat() {
		return bodyFat;
	}

	public void setBodyFat(Double bodyFat) {
		this.bodyFat = bodyFat;
	}

	public String getAvailability() {
		return availability;
	}

	public void setAvailability(String availability) {
		this.availability = availability;
	}

	public Pattern getSleepPattern() {
		return sleepPattern;
	}

	public void setSleepPattern(Pattern sleepPattern) {
		this.sleepPattern = sleepPattern;
	}

	public Level getStressLevel() {
		return stressLevel;
	}

	public void setStressLevel(Level stressLevel) {
		this.stressLevel = stressLevel;
	}

	public boolean isWaiverAccepted() {
		return waiverAccepted;
	}

	public void setWaiverAccepted(boolean waiverAccepted) {
		this.waiverAccepted = waiverAccepted;
	}

	@Override
	public String toString() {
		return "Client [med=" + med + ", activityLevel=" + activityLevel + ", workoutPreference=" + workoutPreference
				+ ", barriers=" + barriers + ", fitnessGoal=" + fitnessGoal + ", height=" + height + ", weight="
				+ weight + ", bodyFat=" + bodyFat + ", availability=" + availability + ", sleepPattern=" + sleepPattern
				+ ", stressLevel=" + stressLevel + ", waiverAccepted=" + waiverAccepted + "]";
	}
}