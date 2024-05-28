package com.citiustech.restserver.model;

import java.sql.Date;
import java.time.LocalDate;

public class Patient {

	
	private int id;
	private String name;
	private Date dob;
	private String gender;
	private String diagnosis;
	private String equipment;
	private Long nurseId;
	private String nurseName;
	
	public Patient() {
		this.id = 1;
		this.name = null;
		this.dob = null;
		this.gender=null;
		this.diagnosis = null;
		this.equipment = null;
		this.nurseId = null;
		this.nurseName = null;
		
		
	}
	public Patient(int id, String name, Date dob,String gender, String diagnosis, String equipment, Long nurseId,
			String nurseName) {
		super();
		this.id = id;
		this.name = name;
		this.dob = dob;
		this.gender=gender;
		this.diagnosis = diagnosis;
		this.equipment = equipment;
		this.nurseId = nurseId;
		this.nurseName = nurseName;
	}
	
	
}
