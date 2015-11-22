package test;

import com.jayway.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.peter.util.JSONSupport;
import com.peter.util.RESTSupport;
import sys.Init;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Peter on 11/20/15.
 */
public class RESTSupportTest {
    public String accessPointHost;
    public String accessPointPort;
    public RESTSupport rest;
    private String accessPointToken;
    private String queryInstruments;

    @Before
    public void setUp() throws Exception {
        accessPointHost="https://api-fxpractice.oanda.com/v1";
        accessPointPort="80";
        accessPointToken="96adf2bb5787f47f8a42e8a188ddbe27-6abfff05cf00171e5bee37d64ffa2eca";
        queryInstruments="EUR_USD";

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGet() throws Exception {
        rest = new RESTSupport(accessPointHost, accessPointPort, "candles");
        Map<String, String> headersMaps = new HashMap<String,String>() {{put("Authorization", "Bearer "+accessPointToken);}};
        Map<String, String> paramsMaps = new HashMap<String,String>() {{put("instrument",queryInstruments);}};
        Response response = rest.get(headersMaps,paramsMaps);
//        paramsMaps = new HashMap<String,String>() {{put("accountId","8978014");}};
//        rest = new RESTSupport(accessPointHost, accessPointPort, "instruments");
//        response = rest.get(headersMaps,paramsMaps);
        JSONSupport json = new JSONSupport(response);
        json.exportCsv(Init.getInstance().rootPath +  "\\"+"test"+".csv");
    }
}