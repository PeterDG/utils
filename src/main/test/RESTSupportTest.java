package test;

import com.jayway.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.peter.util.JSONSupport;
import com.peter.util.RESTSupport;

/**
 * Created by Peter on 11/20/15.
 */
public class RESTSupportTest {
    public String accessPointHost;
    public String accessPointPort;
    public String pricesService;
    public RESTSupport rest;
    private String accessPointToken;
    private String queryInstruments;

    @Before
    public void setUp() throws Exception {
        accessPointHost="https://api-fxpractice.oanda.com/v1";
        accessPointPort="80";
        pricesService="prices";
        accessPointToken="96adf2bb5787f47f8a42e8a188ddbe27-6abfff05cf00171e5bee37d64ffa2eca";
        queryInstruments="EUR_USD,USD_JPY,USD_CAD,";
        rest = new RESTSupport(accessPointHost, accessPointPort, pricesService);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGet() throws Exception {
        Response response = rest.get("Authorization","Bearer "+accessPointToken,"instruments",queryInstruments);
        JSONSupport json = new JSONSupport(response);
        json.jsonPath.prettyPrint();
    }
}