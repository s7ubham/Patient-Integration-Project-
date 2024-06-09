package com.mycompany;

import java.io.File;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.blueprint.CamelBlueprintTestSupport;
import org.junit.Test;

import com.citiustech.inbound.FiletoXslateRoute;

/**
 * A unit test to verify the Camel route works as designed.
 */
public class BlueprintCBRTest extends CamelBlueprintTestSupport {
	
	@Test
    public void testValidIDsInFile() throws Exception {
        context.getRouteDefinition("filetodirect").adviceWith(context, new AdviceWithRouteBuilder() {
            @Override
            public void configure() {
                replaceFromWith("direct:start");
                weaveByToUri("direct:PatientIdsfile").replace().to("mock:result");
            	
            }
        });

        context.start();

        MockEndpoint mockResult = getMockEndpoint("mock:result");

        String fileContent = "P001\nP002\nP003";
        mockResult.expectedBodiesReceived("P001", "P002", "P003");
        System.out.println(mockResult.toString());
        template.sendBody("direct:start", fileContent);

        MockEndpoint.assertIsSatisfied(context);

        context.stop();
    }

    @Test
    public void testEmptyFile() throws Exception {
        context.getRouteDefinition("filetodirect").adviceWith(context, new AdviceWithRouteBuilder() {
            @Override
            public void configure() {
            	replaceFromWith("direct:start");
                weaveByToUri("direct:PatientIdsfile").replace().to("mock:result");
            }
        });

        context.start();

        MockEndpoint mockHttp = getMockEndpoint("mock:http");

        String fileContent = "";
        mockHttp.expectedMessageCount(0);

        template.sendBody("direct:start", fileContent);

        MockEndpoint.assertIsSatisfied(context);

        context.stop();
    }

    

    @Test
    public void testLargeFile() throws Exception {
        context.getRouteDefinition("filetodirect").adviceWith(context, new AdviceWithRouteBuilder() {
            @Override
            public void configure() {
            	replaceFromWith("direct:start");
                weaveByToUri("direct:PatientIdsfile").replace().to("mock:result");
            }
        });

        context.start();

        MockEndpoint mockresult = getMockEndpoint("mock:result");

        StringBuilder fileContent = new StringBuilder();
        for (int i = 1; i <= 1000; i++) {
            fileContent.append(i).append("\n");
        }

        mockresult.expectedMessageCount(1000);
        for (int i = 1; i <= 1000; i++) {
            mockresult.message(i - 1).body().isEqualTo("" + i);
        }

        template.sendBody("direct:start", fileContent.toString());

        MockEndpoint.assertIsSatisfied(context);

        context.stop();
    }

    @Test
    public void testFileWithWhitespace() throws Exception {
        context.getRouteDefinition("filetodirect").adviceWith(context, new AdviceWithRouteBuilder() {
            @Override
            public void configure() {
                
            	replaceFromWith("direct:start");
                weaveByToUri("direct:PatientIdsfile").replace().to("mock:result");
            }
        });

        context.start();

        MockEndpoint mockresult = getMockEndpoint("mock:result");

        String fileContent = "P001 \n P002 \n P003";
        mockresult.expectedBodiesReceived("P001", "P002", "P003");

        template.sendBody("direct:start", fileContent);

        MockEndpoint.assertIsSatisfied(context);

        context.stop();
    }

    @Test
    public void testFileWithBlankLines() throws Exception {
        context.getRouteDefinition("filetodirect").adviceWith(context, new AdviceWithRouteBuilder() {
            @Override
            public void configure() {
            	replaceFromWith("direct:start");
                weaveByToUri("direct:PatientIdsfile").replace().to("mock:result");
            }
        });

        context.start();

        MockEndpoint mockresult = getMockEndpoint("mock:result");

        String fileContent = "P008\n\n\n\n\nP009";
        mockresult.expectedBodiesReceived("P008", "P009");

        template.sendBody("direct:start", fileContent);

        MockEndpoint.assertIsSatisfied(context);

        context.stop();
    }
    
	@Override
	protected String getBlueprintDescriptor() {
		return "OSGI-INF/blueprint/blueprint.xml";
	}
	
}
