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
    private String accessPointToken;
    private String queryInstruments;

    @Before
    public void setUp() throws Exception {
        accessPointHost="https://api-fxpractice.oanda.com/v1";
        accessPointPort="443";
        accessPointToken="96adf2bb5787f47f8a42e8a188ddbe27-6abfff05cf00171e5bee37d64ffa2eca";
        queryInstruments="EUR_USD";
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
}