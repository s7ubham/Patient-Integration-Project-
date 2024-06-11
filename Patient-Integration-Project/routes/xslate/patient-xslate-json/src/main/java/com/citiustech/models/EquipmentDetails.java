package com.citiustech.models;

public class EquipmentDetails {
    private String equipmentStatus;
    private String equipmentName;
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
	@Override
	public String toString() {
		return "EquipmentDetails [equipmentStatus=" + equipmentStatus + ", equipmentName=" + equipmentName + "]";
	}

    
}
