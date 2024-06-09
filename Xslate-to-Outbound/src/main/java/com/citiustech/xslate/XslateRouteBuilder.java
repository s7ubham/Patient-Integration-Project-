package com.citiustech.xslate;

import org.apache.activemq.ConnectionFailedException;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

import com.citiustech.models.DiagnosisDetails;
import com.citiustech.models.EquipmentDetails;
import com.citiustech.models.NurseDetails;
import com.citiustech.models.Patient;
import com.citiustech.models.PatientDemographicDetails;
import com.citiustech.models.PatientTreatmentDetails;
import com.citiustech.models.TransformedPatient;

public class XslateRouteBuilder extends RouteBuilder {
	
	private String xslateJsonStructure;

	public String getXslateJsonStructure() {
		return xslateJsonStructure;
	}

	public void setXslateJsonStructure(String xslateJsonStructure) {
		this.xslateJsonStructure = xslateJsonStructure;
	}

	@Override
	public void configure() throws Exception {
		
		onException(ConnectionFailedException.class)
		.handled(true)
		.log("ActiveMQ Connection Failed : ${exception.message}")
		.maximumRedeliveries(3)
		.maximumRedeliveryDelay("1000")
		.retryAttemptedLogLevel(LoggingLevel.ERROR);
		
		//Default Error Handler
		onException(Exception.class)
		.handled(true)
		.log("Exception occurred: ${exception.message}");			

		from("activemq:queue:patient-xslate")
		.unmarshal().json(JsonLibrary.Jackson,Patient.class)
		.log("${body}")
		.process(e->{
			Patient patient = e.getIn().getBody(Patient.class);
			System.out.println(patient);
			
			TransformedPatient patientData = new TransformedPatient();

			PatientDemographicDetails patientDemographic = new PatientDemographicDetails();
			patientDemographic.setPatientId(patient.getPatientId());
			patientDemographic.setPatientFirstName(patient.getPatientFirstName());
			patientDemographic.setPatientLastName(patient.getPatientLastName());
			patientDemographic.setPatientAge(patient.getPatientAge());
			patientDemographic.setPatientGender(patient.getPatientGender());

			PatientTreatmentDetails patientTreatmentDetails = new PatientTreatmentDetails();

			DiagnosisDetails diagnosisDetails = new DiagnosisDetails();
			diagnosisDetails.setDiagnosisDisease(patient.getDiagnosisDisease());
			diagnosisDetails.setDiagnosisSymptoms(patient.getDiagnosisSymptoms());
			diagnosisDetails.setDiagnosisMedication(patient.getDiagnosisMedication());
			diagnosisDetails.setPatientStatus(patient.getPatientStatus());

			EquipmentDetails equipmentDetails = new EquipmentDetails();
			equipmentDetails.setEquipmentStatus(patient.getEquipmentStatus());
			equipmentDetails.setEquipmentName(patient.getEquipmentName());

			NurseDetails nurseDetails = new NurseDetails();
			nurseDetails.setNurseId(patient.getNurseId());
			nurseDetails.setNurseName(patient.getNurseName());

			patientTreatmentDetails.setDiagnosisDetails(diagnosisDetails);
			patientTreatmentDetails.setEquipmentDetails(equipmentDetails);
			patientTreatmentDetails.setNurseDetails(nurseDetails);

			patientData.setPatientDemographicDetails(patientDemographic);
			patientData.setPatientTreatmentDetails(patientTreatmentDetails);
			e.getIn().setBody(patientData);
			System.out.println(patientData);
			
		})
		.marshal().json(JsonLibrary.Jackson)
		.to("activemq:queue:Outbound")
		.log("Data Sent to Outbound Queue");

//		Method 3
//		from("activemq:queue:patient-xslate")
//		.log("Data Recieved From Inbound: ${body}")
//		.unmarshal().json(JsonLibrary.Jackson)
//		.setBody(simple(getXslateJsonStructure()))
//		.to("activemq:queue:Outbound")
//		.log("Data Sent to Outbound Queue");

		
		//Method-1
//		.process(exchange -> {
//			String originalData = exchange.getIn().getBody(String.class);
//			ObjectMapper objectmapper = new ObjectMapper();
//			JsonNode originalJson = objectmapper.readTree(originalData);	
//			JsonNode transformedJson = ((ObjectNode) objectmapper.createObjectNode().set("Patient_Demographic_Details",
//					objectmapper.createObjectNode().put("id", originalJson.get("id").asInt())
//							.put("name", originalJson.get("name").asText()).put("dob", originalJson.get("dob").asText())))
//					.set("Patient_Treatment_Details", ((ObjectNode) ((ObjectNode) objectmapper.createObjectNode()
//							.set("diagnosisdetails",objectmapper.createObjectNode().put("diagnosis", originalJson.get("diagnosis").asText())))
//							.set("equipment details",objectmapper.createObjectNode().put("equipment", originalJson.get("equipment").asText())))
//							.set("nursedetails", objectmapper.createObjectNode().put("nurseid", originalJson.get("nurseId").asText()).put("name", originalJson.get("nurseName").asText())));
//			String transformedJsonString= objectmapper.writeValueAsString(transformedJson);
//			exchange.getIn().setBody(transformedJsonString);
//		})	

		//Method 2 By using a LinkedHashMap

		//Method 3
//		.setBody(simple("{" +
//			    "\"PatientDemographicDetails\": {" +
//			        "\"PatientId\": \"${body[PatientId]}\"," +
//			        "\"PatientFirstName\": \"${body[PatientFirstName]}\"," +
//			        "\"PatientLastName\": \"${body[PatientLastName]}\"," +
//			        "\"PatientAge\": ${body[PatientAge]},"+
//			        "\"PatientGender\": \"${body[PatientGender]}\""+
//			    "}," +
//			    "\"PatientTreatmentDetails\": {" +
//			        "\"DiagnosisDetails\": {" +
//			            "\"DiagnosisDisease\": \"${body[DiagnosisDisease]}\"," +
//			            "\"DiagnosisSymptoms\": \"${body[DiagnosisSymptoms]}\"," +
//			            "\"DiagnosisMedication\": \"${body[DiagnosisMedication]}\"," +
//			            "\"PatientStatus\": \"${body[PatientStatus]}\""+
//			            
//			        "}," +
//			        "\"Equipment\": {" +
//			            "\"EquipmentName\": \"${body[EquipmentName]}\"," +
//			            "\"EquipmentStatus\": \"${body[EquipmentStatus]}\"" +
//			        	
//			        "}," +
//			        "\"nursedetails\": {" +
//			            "\"NurseId\": \"${body[NurseId]}\"," +
//			            "\"NurseName\": \"${body[NurseName]}\"" +
//			        "}" +
//			    "}" +
//			"}"))
//			.log("${body}")
//			.to("activemq:queue:outbound");
	}
}
