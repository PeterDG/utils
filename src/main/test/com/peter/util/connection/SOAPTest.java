package com.peter.util.connection;

import com.jayway.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Peter on 2/29/2016.
 */
public class SOAPTest {

    public SOAP soap;
    public String serviceUrl;
    public String soapRequest;

    @Before
    public void before() {
        serviceUrl = "http://localhost:8080/detect/services/WSRiskScoringService";
        soapRequest = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ris=\"http://soap.easysol.net/detect/riskScoringService\">\n" +
                "   <soapenv:Body>\n" +
                "      <ris:calculateRiskScore>\n" +
                "         <sharedKey>1234</sharedKey>\n" +
                "         <clientEnvironment>Environmet</clientEnvironment>\n" +
                "      </ris:calculateRiskScore>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>";
    }

    @Test
    public void testSend() throws Exception {
        soap = new SOAP(serviceUrl, soapRequest);
        Response resp=soap.send();
        System.out.println(resp.getBody().asString());
        assertTrue(resp!=null);
    }
}