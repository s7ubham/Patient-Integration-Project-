package com.citiustech.outbound;

import java.util.LinkedHashMap;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

public class OutboundRouter extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		
		from("activemq:queue:Outbound")
		.unmarshal().json(JsonLibrary.Jackson,LinkedHashMap.class)
		.log("Data received after Xslate: ${body} ")
        .multicast().to("direct:PatientBackup","direct:DatabaseUpdate","direct:NurseStatus");
		
		
		//database Update route for changing Equipment Status
		from("direct:DatabaseUpdate")
		.log("Equipment Status flag in database Change route")
			.setHeader("PatientStatus",simple("${body[PatientTreatmentDetails][DiagnosisDetails][PatientStatus]}"))
			.setHeader("PatientId",simple("${body[PatientDemographicDetails][PatientId]}"))
		.choice()
		 	.when(header("PatientStatus").isEqualTo("Active"))
		 		.to("sql: update patients set EquipmentStatus= \"Inuse\" where PatientId= :#PatientId")
		.otherwise()
				.to("sql: update patients set EquipmentStatus= \"Free\" where PatientId= :#PatientId");
		
		
		//Patient Backup Route 
		from("direct:PatientBackup")
		.log("Patient Diagnosis Data Backup Route")
		.process(exchange->{
			LinkedHashMap<String, Object> PatientDemographic=(LinkedHashMap<String, Object>) exchange.getIn().getBody(LinkedHashMap.class).get("PatientDemographicDetails");
			LinkedHashMap<String, Object> PatientTreatment =(LinkedHashMap<String, Object>) exchange.getIn().getBody(LinkedHashMap.class).get("PatientTreatmentDetails");
			LinkedHashMap<String,Object> newBody= new LinkedHashMap<>();
			newBody.put("Patientid", PatientDemographic.get("PatientId"));
			newBody.put("PatientDiagnosis", PatientTreatment.get("DiagnosisDetails"));
			exchange.getIn().setBody(newBody, LinkedHashMap.class);
			exchange.getIn().setHeader("CamelFileName",PatientDemographic.get("PatientId"));			
		})
		.marshal().json(JsonLibrary.Jackson)
		.to("file:Backup");
		

		//Nurse Detail To File Route
		from("direct:NurseStatus")
		.log("Active and inactive Nurse to File")
		.setHeader("PatientStatus",simple("${body[PatientTreatmentDetails][DiagnosisDetails][PatientStatus]}"))
		.setHeader("PatientId",simple("${body[PatientDemographicDetails][PatientId]}"))
		.setHeader("CamelFileName",simple("NurseId-" +"${body[PatientTreatmentDetails][nursedetails][NurseId]}"))
		.setBody(simple("${body[PatientTreatmentDetails][nursedetails]}"))
		.marshal().json(JsonLibrary.Jackson)
		.choice()
	 	.when(header("PatientStatus").isEqualTo("Active"))
	 			.to("file:Nurse/Active")
	 		.otherwise()
	 			.to("file:Nurse/InActive");
	
	}

}
