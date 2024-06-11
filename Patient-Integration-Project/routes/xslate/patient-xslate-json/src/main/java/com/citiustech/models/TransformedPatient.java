package com.citiustech.models;

public class TransformedPatient {
    private PatientDemographicDetails patientDemographicDetails;
    private PatientTreatmentDetails patientTreatmentDetails;
	public PatientDemographicDetails getPatientDemographicDetails() {
		return patientDemographicDetails;
	}
	public void setPatientDemographicDetails(PatientDemographicDetails patientDemographicDetails) {
		this.patientDemographicDetails = patientDemographicDetails;
	}
	public PatientTreatmentDetails getPatientTreatmentDetails() {
		return patientTreatmentDetails;
	}
	public void setPatientTreatmentDetails(PatientTreatmentDetails patientTreatmentDetails) {
		this.patientTreatmentDetails = patientTreatmentDetails;
	}
	@Override
	public String toString() {
		return "TransformedPatient [patientDemographicDetails=" + patientDemographicDetails
				+ ", patientTreatmentDetails=" + patientTreatmentDetails + "]";
	}

    
}