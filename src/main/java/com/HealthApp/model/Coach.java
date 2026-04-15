package com.HealthApp.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;

@Entity
@Table(name = "coach")
public class Coach extends Person {
	@Enumerated(EnumType.STRING)
	private Specialty specialty;
	
	private int yearsOfExperience;

	@OneToMany(mappedBy = "coach", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Certificate> certifications = new ArrayList<>();
	//mappedBy: Certificate entity owns the foreign key, not COACH
	//cascadeType.ALL: Whatever I do to Coach, do the same to its Certificates
	//If a Certificate is removed from the Coach's list, delete it from DB
	
	
	private boolean openForNewClient;
	private int clientCount;
	public String workPlace;

    //AVOID PUTTING DESC BECAUSE IT IS SQL KEYWORD
	public String description;
	
	public Coach() {
		System.out.println("Coach created");
	}

	public Specialty getSpecialty() {
		return specialty;
	}

	public void setSpecialty(Specialty specialty) {
		this.specialty = specialty;
	}

	public int getYearsOfExperience() {
		return yearsOfExperience;
	}

	public void setYearsOfExperience(int yearsOfExperience) {
		this.yearsOfExperience = yearsOfExperience;
	}

	public List<Certificate> getCertifications() {
		return certifications;
	}

	public void setCertifications(List<Certificate> certifications) {
		this.certifications = certifications;
	}

	public boolean isOpenForNewClient() {
		return openForNewClient;
	}

	public void setOpenForNewClient(boolean openForNewClient) {
		this.openForNewClient = openForNewClient;
	}

	public int getClientCount() {
		return clientCount;
	}

	public void setClientCount(int clientCount) {
		this.clientCount = clientCount;
	}

	public String getWorkPlace() {
		return workPlace;
	}

	public void setWorkPlace(String workPlace) {
		this.workPlace = workPlace;
	}

	public String getDesc() {
		return description;
	}

	public void setDesc(String desc) {
		this.description = desc;
	}

    public void addCertificate(Certificate certificate) {
        certifications.add(certificate);
        certificate.setCoach(this);
    }

    public void removeCertificate (Certificate certificate) {
        certifications.remove(certificate);
        certificate.setCoach(null);
    }

	@Override
	public String toString() {
		return "Coach [specialty=" + specialty + ", yearsOfExperience=" + yearsOfExperience + ", certifications="
				+ certifications + ", openForNewClient=" + openForNewClient + ", clientCount=" + clientCount
				+ ", workPlace=" + workPlace + ", desc=" + description + "]";
	}
	
	
}