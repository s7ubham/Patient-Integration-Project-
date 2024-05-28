package com.citiustech.outbound.file.to.xslate;


import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

public class FiletoXslateRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		
		from("file:output?fileName=new.txt")
		.split(body().tokenize("\n"))
		.process(exchange->{
			String body = exchange.getIn().getBody(String.class);
			exchange.getIn().setHeader("id",body);
		})
		.log("http://localhost:8080/PatientData/${header.id}")
		.to("direct:restRouteFile");
		
		from("direct:restRouteFile")
		.log("${header.id}")
		.log("Body: ${body}")
		.setHeader(Exchange.HTTP_METHOD, constant("GET"))
		.setHeader(Exchange.HTTP_URI,simple("http://localhost:8080/PatientData/${header.id}"))
		.to("http://localhost:8080/PatientData/${header.id}")
		.log("Body After: ${body}");
//		
		
//		from("timer:foo?period=2000")
//		.setHeader(Exchange.HTTP_METHOD, constant("GET"))
//		.to("http://localhost:8080/PatientData/2")
//		.log("${body}");
				
		
		
	}

}
