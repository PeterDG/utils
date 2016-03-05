package com.peter.util.connection;

import com.peter.util.connection.soap.SOAP;
import com.peter.util.connection.soap.SOAPParams;
import com.peter.util.connection.soap.XMLSoap;
import org.junit.Before;
import org.junit.Test;
import sys.Environment;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by Peter on 2/29/2016.
 */
public class SOAPTest {

    public SOAP soap;
    public String serviceUrl;
    public String soapRequest;
    public Environment environment;
    public ArrayList<SOAPParams> paramsList;

    @Before
    public void before() {
        environment = new Environment();
        serviceUrl = "http://7.80.179.235:8080/detect/services/WSRiskScoringService";
        soapRequest = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ris=\"http://soap.easysol.net/detect/riskScoringService\">\n" +
                "   <soapenv:Body>\n" +
                "      <ris:calculateRiskScore>\n" +
                "         <sharedKey>1234</sharedKey>\n" +
                "         <clientEnvironment>Environmet</clientEnvironment>\n" +
                "      </ris:calculateRiskScore>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>";
        SOAPParams p1= new SOAPParams("sharedKey","pv1");
        SOAPParams p2= new SOAPParams("clientEnvironment","pv2");
        paramsList = new ArrayList<SOAPParams>();
        paramsList.add(p1);
        paramsList.add(p2);
    }

    @Test
    public void testSend() throws Exception {
        soap = new SOAP(serviceUrl, soapRequest);
        XMLSoap resp=soap.send();
        System.out.println(resp.toString());
        assertTrue(resp!=null);
    }

    @Test
    public void constructorB() throws Exception {
        soap = new SOAP(serviceUrl,soapRequest, paramsList);
        assertTrue(soap.xmlSoapRequest.getElement("//sharedKey").equals("pv1"));
        assertTrue(soap.xmlSoapRequest.getElement("//clientEnvironment").equals("pv2"));
    }

    @Test
    public void constructorC() throws Exception {
        String pathXmlSoap=environment.rootPath + "src\\main\\resources\\templates\\WSRiskScoringService.xsl";
        soap = new SOAP(serviceUrl, paramsList,pathXmlSoap);
        assertTrue(soap.xmlSoapRequest.getElement("//sharedKey").equals("pv1"));
        assertTrue(soap.xmlSoapRequest.getElement("//clientEnvironment").equals("pv2"));
    }
}