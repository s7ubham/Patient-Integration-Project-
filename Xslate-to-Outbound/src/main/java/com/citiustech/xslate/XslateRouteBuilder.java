package com.citiustech.xslate;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

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
		
		onException(Exception.class)
		.log("Exception Occured");

		from("activemq:queue:patient-xslate")
		.log("Data Recieved From Inbound: ${body}")
		.unmarshal().json(JsonLibrary.Jackson)
		.setBody(simple(getXslateJsonStructure()))
		.to("activemq:queue:Outbound")
		.log("Data Sent to Outbound Queue");

		
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
