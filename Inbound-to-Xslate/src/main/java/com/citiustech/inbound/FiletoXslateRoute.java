package com.citiustech.inbound;


import java.io.FileNotFoundException;
import java.net.ConnectException;
import java.sql.SQLException;

import org.apache.activemq.ConnectionFailedException;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.http.common.HttpOperationFailedException;

public class FiletoXslateRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		
		//Exception Handling
				//Http Invocation Exception
				onException(HttpOperationFailedException.class)
				.handled(true)
				.log("Http Request Failed : ${exception.message}")
				.maximumRedeliveries(3)
				.retryAttemptedLogLevel(LoggingLevel.WARN);
				
				//ActiveMQ connection Exception
				onException(ConnectionFailedException.class)
				.handled(true)
				.log("ActiveMQ Connection Failed : ${exception.message}")
				.maximumRedeliveries(3)
				.maximumRedeliveryDelay("1000")
				.retryAttemptedLogLevel(LoggingLevel.WARN);
				
				//File Not Found Exception
				onException(FileNotFoundException.class)
				.handled(true)
				.log("File Not Exist : ${exception.message}")
				.retryAttemptedLogLevel(LoggingLevel.WARN);
				
				//Rest Api Exception
				onException(ConnectException.class)
				.handled(true)
				.log("Rest Api Call Failed : ${exception.message}")
				.maximumRedeliveries(3)
				.maximumRedeliveryDelay("1000")
				.retryAttemptedLogLevel(LoggingLevel.WARN);
				
				
				//SQL Exception 
				onException(SQLException.class)
				.handled(true)
				.log("SQL Exception occurred: ${exception.message}");		
						
				//Default Error Handler
				onException(Exception.class)
				.handled(true)
				.log("Exception occurred: ${exception.message}");		
		

		from("file:output?fileName=PatientIds.txt&noop=true")
		.routeId("filetodirect")
		.split(body().tokenize("\n"))
		.setBody(simple("${body.trim()}"))
		.log("${body}")
		.choice()
			.when(body().isEqualTo(""))
			.log("Empty Line in File")
		.otherwise()
			.to("direct:PatientIdsfile");
		
		from("direct:PatientIdsfile")
		.routeId("directToHttpAndQueue")
		.removeHeaders("*")
		.setHeader(Exchange.HTTP_METHOD,simple("GET"))
		.setHeader(Exchange.HTTP_URI,simple("http://localhost:8080/PatientData/${body}"))
		.to("http://localhost:8080/PatientData/${body}")
		.to("activemq:queue:patient-xslate");

	}

}
