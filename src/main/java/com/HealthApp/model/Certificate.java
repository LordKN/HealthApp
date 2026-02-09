package com.HealthApp.model;

public class Certificate {
	private String cerName, issOrg, desc;
	
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
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Override
	public String toString() {
		return "Certificate [cerName=" + cerName + ", issOrg=" + issOrg + ", desc=" + desc + "]";
	}
}
