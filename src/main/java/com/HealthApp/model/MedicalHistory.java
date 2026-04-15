package com.HealthApp.model;

import jakarta.persistence.*;

@Entity
public class MedicalHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	private String name, description;

    //Map to a client
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
	
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
		return description;
	}

	public void setDesc(String desc) {
		this.description = desc;
	}

	@Override
	public String toString() {
		return "MedicalHistory [name=" + name + ", desc=" + description + "]";
	}
	
	
}
