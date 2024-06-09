package com.citiustech.models;

public class DiagnosisDetails {
    private String diagnosisDisease;
    private String diagnosisSymptoms;
    private String diagnosisMedication;
    private String patientStatus;
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
	@Override
	public String toString() {
		return "DiagnosisDetails [diagnosisDisease=" + diagnosisDisease + ", diagnosisSymptoms=" + diagnosisSymptoms
				+ ", diagnosisMedication=" + diagnosisMedication + ", patientStatus=" + patientStatus + "]";
	}
	
}