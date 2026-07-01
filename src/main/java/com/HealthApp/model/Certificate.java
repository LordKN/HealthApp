package com.HealthApp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class Certificate {

	/*
	*Jackson maps JSON properties using JavaBean getter/setter names.
	*
	* Example:
	* getDescription()/setDescription() <-> "description"
	*
	* If the methods are named getDesc()/setDesc(),
	* the JSON property must be "desc"
	*
	* Keep field names, getters, setters, and JSON property
	* names consistent to avoid null values during deserialization.
	 */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	private String cerName, issOrg, description;


	/*
	* This is the back side of the Coach-Certificate relationship.
	*
	* @JsonBackReference tells Jackson:
	* "Do not serialize this field when returning JSON."
	*
	* This breaks the infinite loop:
	*
	* Certificate -> coach -> certificates -> Certificate ->
	*
	* This relationship still exists in Java/JPA,
	* but the coach field is hidden from the JSON response.
	 */
	@JsonBackReference
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
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

    public Long getId() {
        return id;
    }
}
