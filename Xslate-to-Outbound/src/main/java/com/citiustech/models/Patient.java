package com.citiustech.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Patient {
	
	@JsonProperty("PatientId")
	private String patientId;
	    
	@JsonProperty("PatientFirstName")
	private String patientFirstName;
	    
	@JsonProperty("PatientLastName")
	private String patientLastName;
	    
	@JsonProperty("PatientAge")
	private Integer patientAge;
	    
	@JsonProperty("PatientGender")
	private String patientGender;
	    
	@JsonProperty("DiagnosisDisease")
	private String diagnosisDisease;
	    
	@JsonProperty("DiagnosisSymptoms")
	private String diagnosisSymptoms;
	    
	@JsonProperty("DiagnosisMedication")  
	private String diagnosisMedication;
	    
	@JsonProperty("PatientStatus")
	private String patientStatus;
	    
	@JsonProperty("EquipmentStatus")   
	private String equipmentStatus;
	    
	@JsonProperty("EquipmentName")
	private String equipmentName;
	
	@JsonProperty("NurseId")
	private String nurseId;
	    
	@JsonProperty("NurseName")
	private String nurseName;
	    
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

	public String getDiagnosisDisease() {
		return diagnosisDisease;
	}

	public void setDiagnosisDisease(String diagnosisDisease) {
		this.diagnosisDisease = diagnosisDisease;
	}

	public String getDiagnosisSymptoms() {
		return diagnosisSymptoms;
	}

	public void setDiagnosisSymptoms(String diagnosisSymptoms) {
		this.diagnosisSymptoms = diagnosisSymptoms;
	}

	public String getDiagnosisMedication() {
		return diagnosisMedication;
	}

	public void setDiagnosisMedication(String diagnosisMedication) {
		this.diagnosisMedication = diagnosisMedication;
	}

	public String getPatientStatus() {
		return patientStatus;
	}

	public void setPatientStatus(String patientStatus) {
		this.patientStatus = patientStatus;
	}

	public String getEquipmentStatus() {
		return equipmentStatus;
	}

	public void setEquipmentStatus(String equipmentStatus) {
		this.equipmentStatus = equipmentStatus;
	}

	public String getEquipmentName() {
		return equipmentName;
	}

	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}

	public String getNurseId() {
		return nurseId;
	}

	public void setNurseId(String nurseId) {
		this.nurseId = nurseId;
	}

	public String getNurseName() {
		return nurseName;
	}

	public void setNurseName(String nurseName) {
		this.nurseName = nurseName;
	}

	@Override
	public String toString() {
		return "Patient [patientId=" + patientId + ", patientFirstName=" + patientFirstName + ", patientLastName="
				+ patientLastName + ", patientAge=" + patientAge + ", patientGender=" + patientGender
				+ ", diagnosisDisease=" + diagnosisDisease + ", diagnosisSymptoms=" + diagnosisSymptoms
				+ ", diagnosisMedication=" + diagnosisMedication + ", patientStatus=" + patientStatus
				+ ", equipmentStatus=" + equipmentStatus + ", equipmentName=" + equipmentName + ", nurseId=" + nurseId
				+ ", nurseName=" + nurseName + "]";
	}
	
	
	
}
