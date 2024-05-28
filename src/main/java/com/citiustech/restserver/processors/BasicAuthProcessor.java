package com.citiustech.restserver.processors;

import java.util.Base64;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class BasicAuthProcessor implements Processor{

	@Override
	public void process(Exchange exchange) throws Exception {
		String AuthHeader = exchange.getIn().getHeader("Authorization", String.class);
		if (exchange.getIn().getHeader("Authorization", String.class) == null) {
			exchange.getIn().setHeader("SuccessfulAuthorization", true);
			

		} else {
			byte[] decodedBytes = Base64.getDecoder().decode(AuthHeader.replace("Basic ", ""));
			String[] decodedString = new String(decodedBytes).split(":");
			if (decodedString[0].equals("admin") && decodedString[1].equals("admin")) {
				exchange.getIn().setHeader("SuccessfulAuthorization", true);
			} else {
				exchange.getIn().setHeader("SuccessfulAuthorization", false);
			}

		}
		
	}

}
