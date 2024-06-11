package com.citiustech.models;

public class PatientTreatmentDetails {
    private DiagnosisDetails diagnosisDetails;
    private EquipmentDetails equipmentDetails;
    private NurseDetails nurseDetails;
	public DiagnosisDetails getDiagnosisDetails() {
		return diagnosisDetails;
	}
	public void setDiagnosisDetails(DiagnosisDetails diagnosisDetails) {
		this.diagnosisDetails = diagnosisDetails;
	}
	public EquipmentDetails getEquipmentDetails() {
		return equipmentDetails;
	}
	public void setEquipmentDetails(EquipmentDetails equipmentDetails) {
		this.equipmentDetails = equipmentDetails;
	}
	public NurseDetails getNurseDetails() {
		return nurseDetails;
	}
	public void setNurseDetails(NurseDetails nurseDetails) {
		this.nurseDetails = nurseDetails;
	}
	@Override
	public String toString() {
		return "PatientTreatmentDetails [diagnosisDetails=" + diagnosisDetails + ", equipmentDetails="
				+ equipmentDetails + ", nurseDetails=" + nurseDetails + "]";
	}
    
}
