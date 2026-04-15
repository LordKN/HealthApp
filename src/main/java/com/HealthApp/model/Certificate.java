package com.HealthApp.model;

import jakarta.persistence.*;

@Entity
public class Certificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	private String cerName, issOrg, description;

    @ManyToOne
    @JoinColumn(name = "coach_id")
    private Coach coach;
	
	public Certificate() {
		System.out.println("Certificate created");
	}

	public String getCerName() {
		return cerName;
	}

	public void setCerName(String cerName) {
		this.cerName = cerName;
	}

	public String getIssOrg() {
		return issOrg;
	}

	public void setIssOrg(String issOrg) {
		this.issOrg = issOrg;
	}

	public String getDesc() {
		return description;
	}

	public void setDesc(String desc) {
		this.description = description;
	}

    public Coach getCoach() { return coach; }

    public void setCoach(Coach coach) {
        this.coach = coach;
    }

	@Override
	public String toString() {
		return "Certificate [cerName=" + cerName + ", issOrg=" + issOrg + ", desc=" + description + "]";
	}
}
