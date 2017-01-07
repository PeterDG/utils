package com.peter.util.data.serializable.json;

import com.peter.util.data.serializable.json.data.Prices;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Peter on 1/7/2017.
 */
public class JSONMapperTest {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void fromString() {
        String json="{\"prices\":[{\"instrument\":\"EUR_USD\",\"time\":\"2017-01-06T21:59:58.800121Z\",\"bid\":1.05273,\"ask\":1.05373,\"status\":\"halted\"},{\"instrument\":\"USD_JPY\",\"time\":\"2017-01-06T21:59:58.801374Z\",\"bid\":116.932,\"ask\":117.032,\"status\":\"halted\"},{\"instrument\":\"USD_CAD\",\"time\":\"2017-01-06T21:59:58.802391Z\",\"bid\":1.32333,\"ask\":1.32433,\"status\":\"halted\"}]}";
        Prices prices = JSONMapper.fromString(json, Prices.class);
        assertTrue(prices.getPrices().size()==3);
    }

}