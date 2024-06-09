package com.citiustech.models;

public class PatientDemographicDetails {
    private String patientId;
    private String patientFirstName;
    private String patientLastName;
    private Integer patientAge;
    private String patientGender;
	public String getPatientId() {
		return patientId;
	}
	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}
	public String getPatientFirstName() {
		return patientFirstName;
	}
	public void setPatientFirstName(String patientFirstName) {
		this.patientFirstName = patientFirstName;
	}
	public String getPatientLastName() {
		return patientLastName;
	}
	public void setPatientLastName(String patientLastName) {
		this.patientLastName = patientLastName;
	}
	public Integer getPatientAge() {
		return patientAge;
	}
	public void setPatientAge(Integer patientAge) {
		this.patientAge = patientAge;
	}
	public String getPatientGender() {
		return patientGender;
	}
	public void setPatientGender(String patientGender) {
		this.patientGender = patientGender;
	}
	@Override
	public String toString() {
		return "PatientDemographicDetails [patientId=" + patientId + ", patientFirstName=" + patientFirstName
				+ ", patientLastName=" + patientLastName + ", patientAge=" + patientAge + ", patientGender="
				+ patientGender + "]";
	}
    
    

}