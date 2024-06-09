package com.citiustech.models;

public class NurseDetails {
    private String nurseId;
    private String nurseName;
    
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
		return "NurseDetails [nurseId=" + nurseId + ", nurseName=" + nurseName + "]";
	}
	
    
}
