package com.HealthApp.model;

public class MedicalHistory {
	private String name, desc;
	
	public MedicalHistory() {
		System.out.println("Medical history created");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Override
	public String toString() {
		return "MedicalHistory [name=" + name + ", desc=" + desc + "]";
	}
	
	
}
