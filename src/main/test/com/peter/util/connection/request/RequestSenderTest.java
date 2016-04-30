package com.peter.util.connection.request;

import com.jayway.restassured.response.Response;
import com.peter.util.connection.REST;
import com.peter.util.connection.RESTTest;
import com.peter.util.connection.SOAPTest;
import com.peter.util.connection.soap.SOAP;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Peter on 4/29/2016.
 */
public class RequestSenderTest {
    public RESTTest restTest;
    public SOAPTest soapTest;
    public ArrayList<Request> requestList;

    @Before
    public void setUp() throws Exception {
        restTest = new RESTTest();
        restTest.setUp();
        restTest.rest = new REST(restTest.urlA, RequestType.Type.POST);
        soapTest = new SOAPTest();
        soapTest.before();
        soapTest.soap = new SOAP(soapTest.serviceUrl, soapTest.soapRequest, soapTest.paramsList);

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testRun() throws Exception {
        requestList=new ArrayList<>();
        for (int i = 0; i < 10; i++)
            requestList.add(new Request(soapTest.soap));
        RequestSender sender = new RequestSender(requestList);
        sender.run();
        for (int i = 0; i < 10; i++) {
            SOAP soapResource = (SOAP) (sender.requests.get(i).resource);
            assertTrue(soapResource.xmlSoapRequest != null);
        }
    }

    @Test
    public void testRunCombined() throws Exception {
        requestList=new ArrayList<>();
        for (int i = 0; i < 10; i++)
            requestList.add(new Request(soapTest.soap));
        for (int i = 0; i < 10; i++)
            requestList.add(new Request(restTest.rest));
        RequestSender sender = new RequestSender(requestList);
        sender.run();
        for (int i = 0; i < 10; i++) {
            SOAP soapResource = (SOAP) (sender.requests.get(i).resource);
            assertTrue(soapResource.xmlSoapRequest != null);
        }
        for (int i = 10; i < requestList.size(); i++) {
            REST soapResource = (REST) (sender.requests.get(i).resource);
            assertTrue(soapResource.response != null);
        }
    }
}