package com.citiustech.outbound;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.LinkedHashMap;

import org.apache.activemq.ConnectionFailedException;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

public class OutboundRouter extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		
		onException(ConnectionFailedException.class)
		.handled(true)
		.log("ActiveMQ Connection Failed : ${exception.message}")
		.maximumRedeliveries(3)
		.maximumRedeliveryDelay("1000")
		.retryAttemptedLogLevel(LoggingLevel.WARN);
		
		onException(SQLException.class)
		.handled(true)
		.log("SQL Exception occurred: ${exception.message}");		
		
		//File Not Found Exception
		onException(FileNotFoundException.class)
		.handled(true)
		.log("File Not Exist : ${exception.message}")
		.retryAttemptedLogLevel(LoggingLevel.WARN);
		
		//Default Error Handler
		onException(Exception.class)
		.handled(true)
		.log("Exception occurred: ${exception.message}");		
		
		from("activemq:queue:Outbound")
		.log("Before ${body}")
		.unmarshal().json(JsonLibrary.Jackson,LinkedHashMap.class)
		.log("After ${body}")
		.log("Data received after Xslate: ${body} ")
        .multicast().to("direct:PatientBackup","direct:DatabaseUpdate","direct:NurseStatus");
		
		
		//database Update route for changing Equipment Status
		from("direct:DatabaseUpdate")
		.log("Equipment Status flag in database Change route")
			.setHeader("PatientStatus",simple("${body[patientTreatmentDetails][diagnosisDetails][patientStatus]}"))
			.setHeader("PatientId",simple("${body[patientDemographicDetails][patientId]}"))
		.choice()
		 	.when(header("PatientStatus").isEqualTo("Active"))
		 		.to("sql: update patients set EquipmentStatus= \"Inuse\" where PatientId= :#PatientId")
		.otherwise()
				.to("sql: update patients set EquipmentStatus= \"Free\" where PatientId= :#PatientId");
		
		
		//Patient Backup Route 
		from("direct:PatientBackup")
		.log("Patient Diagnosis Data Backup Route")
		.process(exchange->{
			LinkedHashMap<String, Object> PatientDemographic=(LinkedHashMap<String, Object>) exchange.getIn().getBody(LinkedHashMap.class).get("patientDemographicDetails");
			LinkedHashMap<String, Object> PatientTreatment =(LinkedHashMap<String, Object>) exchange.getIn().getBody(LinkedHashMap.class).get("patientTreatmentDetails");
			LinkedHashMap<String,Object> newBody= new LinkedHashMap<>();
			newBody.put("Patientid", PatientDemographic.get("patientId"));
			newBody.put("PatientDiagnosis", PatientTreatment.get("diagnosisDetails"));
			exchange.getIn().setBody(newBody, LinkedHashMap.class);
			exchange.getIn().setHeader("CamelFileName",PatientDemographic.get("patientId"));			
		})
		.marshal().json(JsonLibrary.Jackson)
		.to("file:Backup");
		

		//Nurse Detail To File Route
		from("direct:NurseStatus")
		.log("Active and inactive Nurse to File")
		.setHeader("PatientStatus",simple("${body[patientTreatmentDetails][diagnosisDetails][patientStatus]}"))
		.setHeader("PatientId",simple("${body[patientDemographicDetails][patientId]}"))
		.setHeader("CamelFileName",simple("NurseId-" +"${body[patientTreatmentDetails][nurseDetails][nurseId]}"))
		.setBody(simple("${body[patientTreatmentDetails][nursedetails]}"))
		.marshal().json(JsonLibrary.Jackson)
		.choice()
	 	.when(header("PatientStatus").isEqualTo("Active"))
	 			.to("file:Nurse/Active")
	 		.otherwise()
	 			.to("file:Nurse/InActive");
	
	}

}
