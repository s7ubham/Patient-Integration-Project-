package com.citiustech.restserver.routebuilder;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import com.citiustech.restserver.processors.BasicAuthProcessor;

public class PatientCrudGetbyId extends RouteBuilder
{
	private String patientByIdSQlQuery;
	
	public String getPatientByIdSQlQuery() {
		return patientByIdSQlQuery;
	}

	public void setPatientByIdSQlQuery(String patientByIdSQlQuery) {
		this.patientByIdSQlQuery = patientByIdSQlQuery;
	}

	@Override
	public void configure() throws Exception {
		
		restConfiguration() 
		.component("spark-rest").port(8080)
		.bindingMode(RestBindingMode.json);

		rest("/PatientData")
			.get("/{id}").to("direct:getPatientById");
		
		from("direct:getPatientById")
		.to(getPatientByIdSQlQuery());
		
		
	}

}
