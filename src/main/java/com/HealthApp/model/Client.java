package com.HealthApp.model;

import java.util.List;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.util.ArrayList;

public class Client extends Person {
	private List<MedicalHistory> med = new ArrayList<>();
	
	@Enumerated(EnumType.STRING)
	private Level activityLevel;
	
	private String workoutPreference;
	private String barriers;
	
	@Enumerated(EnumType.STRING)
	private Goal fitnessGoal;
	
	
	private Double height;
	private Double weight;
	private Double bodyFat;
	private String availability;
	private String sleepPattern;
	
	@Enumerated(EnumType.STRING)
	private Level stressLevel;
	
	private boolean waiverAccepted;
	
	public Client () {
		System.out.println("Client created");
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

	public String getWorkoutPreference() {
		return workoutPreference;
	}

	public void setWorkoutPreference(String workoutPreference) {
		this.workoutPreference = workoutPreference;
	}

	public String getBarriers() {
		return barriers;
	}

	public void setBarriers(String barriers) {
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

	public String getSleepPattern() {
		return sleepPattern;
	}

	public void setSleepPattern(String sleepPattern) {
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

enum Level { LOW, MEDIUM, HIGH }
enum Goal { LONG_TERM, SHORT_TERM }
enum Pattern {POOR, AVERAGE, GOOD }