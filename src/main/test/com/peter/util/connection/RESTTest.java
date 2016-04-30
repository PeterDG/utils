package com.peter.util.connection;

import com.jayway.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.peter.util.data.JSON;
import sys.Environment;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by Peter on 11/20/15.
 */
public class RESTTest {
    public String accessPointHost;
    public String accessPointPort;
    public REST rest;
    public String accessPointToken;
    public String queryInstruments;
    public String url;
    public String urlA;
    public String urlB;
    private String urlC;

    @Before
    public void setUp() throws Exception {
        accessPointHost="https://api-fxpractice.oanda.com/v1";
        accessPointPort="443";
        url="https://api-fxpractice.oanda.com:443/v1/candles";
        accessPointToken="96adf2bb5787f47f8a42e8a188ddbe27-6abfff05cf00171e5bee37d64ffa2eca";
        queryInstruments="EUR_USD";
        urlA="http://192.168.0.20:8080/detect/public/calculate-risk-score.htm";
        urlB="http://192.168.0.20:8080/detect/public/calculate-risk-score.htm?sharedkey=Home";
        urlC="http://192.168.0.20:8080/detect//public/calculate-risk-score.htm";
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGet() throws Exception {
        rest = new REST(accessPointHost, accessPointPort, "candles");
        Map<String, String> headersMaps = new HashMap<String,String>() {{put("Authorization", "Bearer "+accessPointToken);}};
        Map<String, String> paramsMaps = new HashMap<String,String>() {{put("instrument",queryInstruments);}};
        Response response = rest.get(headersMaps,paramsMaps);
//        paramsMaps = new HashMap<String,String>() {{put("accountId","8978014");}};
//        rest = new REST(accessPointHost, accessPointPort, "instruments");
//        response = rest.get(headersMaps,paramsMaps);
        JSON json = new JSON(response);
        json.exportCsv(Environment.getInstance().rootPath +  "\\"+"test"+".csv");
        assertTrue(response!=null);
    }

    @Test
    public void testGetConstructorOnlyHostWithoutPort() throws Exception {
        rest = new REST(accessPointHost, "candles");
        Map<String, String> headersMaps = new HashMap<String,String>() {{put("Authorization", "Bearer "+accessPointToken);}};
        Map<String, String> paramsMaps = new HashMap<String,String>() {{put("instrument",queryInstruments);}};
        Response response = rest.get(headersMaps,paramsMaps);
        assertTrue(response!=null);
    }

    @Test
    public void testGetConstructorHostWithPort() throws Exception {
        rest = new REST("https://api-fxpractice.oanda.com"+":" +accessPointPort, "/v1/candles");
        Map<String, String> headersMaps = new HashMap<String,String>() {{put("Authorization", "Bearer "+accessPointToken);}};
        Map<String, String> paramsMaps = new HashMap<String,String>() {{put("instrument",queryInstruments);}};
        Response response = rest.get(headersMaps,paramsMaps);
        assertTrue(response!=null);
    }

    @Test
    public void testGetConstructorURL() throws Exception {
        rest = new REST(url);
        Map<String, String> headersMaps = new HashMap<String,String>() {{put("Authorization", "Bearer "+accessPointToken);}};
        Map<String, String> paramsMaps = new HashMap<String,String>() {{put("instrument",queryInstruments);}};
        Response response = rest.get(headersMaps,paramsMaps);
        assertTrue(response!=null);
    }

    @Test
    public void testPOSTConstructorURL() throws Exception {
        rest = new REST(urlA);
        Response response = rest.post();
        assertTrue(response.getStatusCode()==200);
    }

    @Test
    public void testMalformedURL() throws Exception {
        rest = new REST(urlC);
        Response response = rest.post();
        assertTrue("Response code:"+String.valueOf(response.getStatusCode()),response.getStatusCode()==200);
    }
}