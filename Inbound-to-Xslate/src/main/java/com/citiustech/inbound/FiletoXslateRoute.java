package com.citiustech.inbound;


import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

public class FiletoXslateRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		

		from("file:output?fileName=PatientIds.txt&noop=true")
		.split(body().tokenize("\n"))
		.setBody(simple("${body.trim()}"))
		.log("${body}")
		.to("direct:test");
		
		from("direct:test")
		.removeHeaders("*")
		.setHeader(Exchange.HTTP_METHOD,simple("GET"))
		.setHeader(Exchange.HTTP_URI,simple("http://localhost:8080/PatientData/${body}"))
		.to("http://localhost:8080/PatientData/${body}")
		.to("activemq:queue:patient-xslate");

	}

}
