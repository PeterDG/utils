package com.peter.util.connection.request;

import com.jayway.restassured.response.Response;
import com.peter.util.connection.REST;
import com.peter.util.connection.RESTTest;
import com.peter.util.connection.SOAPTest;
import com.peter.util.connection.soap.SOAP;
import com.peter.util.connection.soap.XMLSoap;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by Peter on 4/29/2016.
 */
public class RequestTest {
    public RESTTest restTest;
    public SOAPTest soapTest;
    public Request request;

    @Before
    public void setUp() throws Exception {
        restTest = new RESTTest();
        restTest.setUp();
        soapTest = new SOAPTest();
        soapTest.before();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testPostSOAP() throws Exception {
        soapTest.soap = new SOAP(soapTest.serviceUrl, soapTest.soapRequest, soapTest.paramsList);
        request = new Request(soapTest.soap);
        String strResponse = request.send();
        XMLSoap response = ((SOAP) request.resource).xmlSoapResponse;
        assertTrue(response != null);
    }

    @Test
    public void testPostREST() throws Exception {
        restTest.rest = new REST(restTest.urlA, RequestType.Type.POST);
        request = new Request(restTest.rest);
        String strResponse = request.send();
        Response response = ((REST) request.resource).response;
        assertTrue(response != null);
    }

    @Test
    public void testGetREST() throws Exception {
        REST requestREST = new REST(restTest.url, RequestType.Type.GET);
        Map<String, String> headersMaps = new HashMap<String, String>() {{
            put("Authorization", "Bearer " + restTest.accessPointToken);
        }};
        Map<String, String> paramsMaps = new HashMap<String, String>() {{
            put("instrument", restTest.queryInstruments);
        }};
        requestREST.params(paramsMaps);
        requestREST.headers(headersMaps);
        request = new Request(requestREST);
        String strResponse = request.send();
        Response response = ((REST) request.resource).response;
        assertTrue(response != null);
    }
}