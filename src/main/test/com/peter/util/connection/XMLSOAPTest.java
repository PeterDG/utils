package com.peter.util.connection;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by Peter on 2/29/2016.
 */
public class XMLSoapTest {

    public XMLSoap xmlSoap;
    public String request;
    public String response;
    @Before
    public void before(){
      request="<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ris=\"http://soap.easysol.net/detect/riskScoringService\">\n" +
              "   <soapenv:Body>\n" +
              "      <ris:calculateRiskScore>\n" +
              "         <sharedKey>1234</sharedKey>\n" +
              "         <clientEnvironment>Environment</clientEnvironment>\n" +
              "      </ris:calculateRiskScore>\n" +
              "   </soapenv:Body>\n" +
              "</soapenv:Envelope>";

        response="<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "   <soap:Body>\n" +
                "      <ns2:calculateRiskScoreResponse xmlns:ns2=\"http://soap.easysol.net/detect/riskScoringService\">\n" +
                "         <calculateRiskScoreResponse>\n" +
                "            <resultCode>99</resultCode>\n" +
                "            <resultDescription>Service error</resultDescription>\n" +
                "         </calculateRiskScoreResponse>\n" +
                "      </ns2:calculateRiskScoreResponse>\n" +
                "   </soap:Body>\n" +
                "</soap:Envelope>";

    }

    @Test
    public void testGetData() throws Exception {
        xmlSoap = new XMLSoap(request);
    }

    @Test
    public void testGetElementResponseC() throws Exception {
        xmlSoap = new XMLSoap(response);
        String result=xmlSoap.xml.getElement("calculateRiskScoreResponse/resultCode");
        assertTrue(result.equals("99"));
    }

    @Test
    public void testGetElementResponseB() throws Exception {
        xmlSoap = new XMLSoap(response);
        String result=xmlSoap.xml.getElement("calculateRiskScoreResponse/resultDescription");
        assertTrue(result.equals("Service error"));
    }

    @Test
    public void testToString() throws Exception {
        xmlSoap = new XMLSoap(response);
        String parse=xmlSoap.toString();
        xmlSoap = new XMLSoap(parse);
        String result=xmlSoap.xml.getElement("calculateRiskScoreResponse/resultDescription");
        assertTrue(result.equals("Service error"));
    }

    @Test
    public void testSetElementResponse() throws Exception {
        xmlSoap = new XMLSoap(response);
        xmlSoap.setElement("calculateRiskScoreResponse/resultDescription","!Esto es una prueba!");
        String result=xmlSoap.getElement("calculateRiskScoreResponse/resultDescription");
        assertTrue(result.equals("!Esto es una prueba!"));
    }

    @Test
    public void testGetElementResponse() throws Exception {
        xmlSoap = new XMLSoap(response);
        String result=xmlSoap.getElement("calculateRiskScoreResponse/resultDescription");
        assertTrue(result.equals("Service error"));
    }

    @Test
    public void testGetElementResponseA() throws Exception {
        xmlSoap = new XMLSoap(response);
        String result=xmlSoap.getElement("calculateRiskScoreResponse/resultCode");
        assertTrue(result.equals("99"));
    }

    @Test
    public void testGetElementRequest() throws Exception {
        xmlSoap = new XMLSoap(request);
        String result=xmlSoap.getElement("soapenv_-Body/ris_-calculateRiskScore/sharedKey");
        assertTrue(result.equals("1234"));
    }

    @Test
    public void testGetElementRequestA() throws Exception {
        xmlSoap = new XMLSoap(request);
        String result=xmlSoap.getElement("soapenv_-Body/ris_-calculateRiskScore/clientEnvironment");
        assertTrue(result.equals("Environment"));
    }
}