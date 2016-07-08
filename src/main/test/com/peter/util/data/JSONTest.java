package com.peter.util.data;

import com.peter.util.data.JSON;
import com.peter.util.connection.REST;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by Peter on 11/20/15.
 */
public class JSONTest {

    public String accessPointHost;
    public String accessPointPort;
    public REST rest;
    private String accessPointToken;
    private String queryInstruments;
    public JSON json;


    @Before
    public void setUp() throws Exception {
       json=new JSON(jsonStr);

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetDataList() throws Exception {
        HashMap<String, List<Object>> dataList = json.getDataList("candles");
        assertTrue("This will succeed.", dataList.size()==11);
    }

    @Test
    public void equals() throws Exception {
        assertTrue(json.equals(new JSON(jsonStr)));
    }

    public final String jsonStr="{\n" +
            "    \"granularity\": \"D\",\n" +
            "    \"instrument\": \"EUR_USD\",\n" +
            "    \"candles\": [\n" +
            "        {\n" +
            "            \"volume\": 39240,\n" +
            "            \"closeAsk\": 1.10112,\n" +
            "            \"openBid\": 1.0985,\n" +
            "            \"highAsk\": 1.10738,\n" +
            "            \"lowBid\": 1.09645,\n" +
            "            \"closeBid\": 1.10014,\n" +
            "            \"time\": \"2015-10-29T22:00:00.000000Z\",\n" +
            "            \"openAsk\": 1.09872,\n" +
            "            \"highBid\": 1.10719,\n" +
            "            \"lowAsk\": 1.09661,\n" +
            "            \"complete\": true\n" +
            "        },\n" +
            "        {\n" +
            "            \"volume\": 23888,\n" +
            "            \"closeAsk\": 1.10165,\n" +
            "            \"openBid\": 1.10318,\n" +
            "            \"highAsk\": 1.10538,\n" +
            "            \"lowBid\": 1.09997,\n" +
            "            \"closeBid\": 1.10142,\n" +
            "            \"time\": \"2015-11-01T22:00:00.000000Z\",\n" +
            "            \"openAsk\": 1.10402,\n" +
            "            \"highBid\": 1.10517,\n" +
            "            \"lowAsk\": 1.10013,\n" +
            "            \"complete\": true\n" +
            "        },\n" +
            "        {\n" +
            "            \"volume\": 23113,\n" +
            "            \"closeAsk\": 1.09676,\n" +
            "            \"openBid\": 1.10123,\n" +
            "            \"highAsk\": 1.10308,\n" +
            "            \"lowBid\": 1.09354,\n" +
            "            \"closeBid\": 1.09626,\n" +
            "            \"time\": \"2015-11-02T22:00:00.000000Z\",\n" +
            "            \"openAsk\": 1.10158,\n" +
            "            \"highBid\": 1.10294,\n" +
            "            \"lowAsk\": 1.0937,\n" +
            "            \"complete\": true\n" +
            "        },\n" +
            "        {\n" +
            "            \"volume\": 31451,\n" +
            "            \"closeAsk\": 1.08676,\n" +
            "            \"openBid\": 1.09614,\n" +
            "            \"highAsk\": 1.09694,\n" +
            "            \"lowBid\": 1.08429,\n" +
            "            \"closeBid\": 1.08643,\n" +
            "            \"time\": \"2015-11-03T22:00:00.000000Z\",\n" +
            "            \"openAsk\": 1.09679,\n" +
            "            \"highBid\": 1.09671,\n" +
            "            \"lowAsk\": 1.08453,\n" +
            "            \"complete\": true\n" +
            "        },\n" +
            "        {\n" +
            "            \"volume\": 30582,\n" +
            "            \"closeAsk\": 1.08847,\n" +
            "            \"openBid\": 1.08638,\n" +
            "            \"highAsk\": 1.08995,\n" +
            "            \"lowBid\": 1.08332,\n" +
            "            \"closeBid\": 1.08826,\n" +
            "            \"time\": \"2015-11-04T22:00:00.000000Z\",\n" +
            "            \"openAsk\": 1.08664,\n" +
            "            \"highBid\": 1.08964,\n" +
            "            \"lowAsk\": 1.08346,\n" +
            "            \"complete\": true\n" +
            "        },\n" +
            "        {\n" +
            "            \"volume\": 37207,\n" +
            "            \"closeAsk\": 1.07444,\n" +
            "            \"openBid\": 1.08807,\n" +
            "            \"highAsk\": 1.08947,\n" +
            "            \"lowBid\": 1.07,\n" +
            "            \"closeBid\": 1.07357,\n" +
            "            \"time\": \"2015-11-05T22:00:00.000000Z\",\n" +
            "            \"openAsk\": 1.08838,\n" +
            "            \"highBid\": 1.0893,\n" +
            "            \"lowAsk\": 1.07081,\n" +
            "            \"complete\": true\n" +
            "        },\n" +
            "        {\n" +
            "            \"volume\": 27960,\n" +
            "            \"closeAsk\": 1.07519,\n" +
            "            \"openBid\": 1.07269,\n" +
            "            \"highAsk\": 1.07909,\n" +
            "            \"lowBid\": 1.07186,\n" +
            "            \"closeBid\": 1.07492,\n" +
            "            \"time\": \"2015-11-08T22:00:00.000000Z\",\n" +
            "            \"openAsk\": 1.07328,\n" +
            "            \"highBid\": 1.07894,\n" +
            "            \"lowAsk\": 1.0721,\n" +
            "            \"complete\": true\n" +
            "        },\n" +
            "        {\n" +
            "            \"volume\": 28504,\n" +
            "            \"closeAsk\": 1.07254,\n" +
            "            \"openBid\": 1.07461,\n" +
            "            \"highAsk\": 1.07649,\n" +
            "            \"lowBid\": 1.06736,\n" +
            "            \"closeBid\": 1.07233,\n" +
            "            \"time\": \"2015-11-09T22:00:00.000000Z\",\n" +
            "            \"openAsk\": 1.07522,\n" +
            "            \"highBid\": 1.07635,\n" +
            "            \"lowAsk\": 1.06753,\n" +
            "            \"complete\": true\n" +
            "        },\n" +
            "        {\n" +
            "            \"volume\": 26025,\n" +
            "            \"closeAsk\": 1.07455,\n" +
            "            \"openBid\": 1.07218,\n" +
            "            \"highAsk\": 1.07748,\n" +
            "            \"lowBid\": 1.07048,\n" +
            "            \"closeBid\": 1.07415,\n" +
            "            \"time\": \"2015-11-10T22:00:00.000000Z\",\n" +
            "            \"openAsk\": 1.07239,\n" +
            "            \"highBid\": 1.07729,\n" +
            "            \"lowAsk\": 1.07064,\n" +
            "            \"complete\": true\n" +
            "        },\n" +
            "        {\n" +
            "            \"volume\": 42680,\n" +
            "            \"closeAsk\": 1.08167,\n" +
            "            \"openBid\": 1.07423,\n" +
            "            \"highAsk\": 1.08329,\n" +
            "            \"lowBid\": 1.06903,\n" +
            "            \"closeBid\": 1.08121,\n" +
            "            \"time\": \"2015-11-11T22:00:00.000000Z\",\n" +
            "            \"openAsk\": 1.07457,\n" +
            "            \"highBid\": 1.08305,\n" +
            "            \"lowAsk\": 1.06921,\n" +
            "            \"complete\": true\n" +
            "        },\n" +
            "        {\n" +
            "            \"volume\": 34752,\n" +
            "            \"closeAsk\": 1.07771,\n" +
            "            \"openBid\": 1.0809,\n" +
            "            \"highAsk\": 1.08178,\n" +
            "            \"lowBid\": 1.07137,\n" +
            "            \"closeBid\": 1.07677,\n" +
            "            \"time\": \"2015-11-12T22:00:00.000000Z\",\n" +
            "            \"openAsk\": 1.08154,\n" +
            "            \"highBid\": 1.08161,\n" +
            "            \"lowAsk\": 1.07152,\n" +
            "            \"complete\": true\n" +
            "        },\n" +
            "        {\n" +
            "            \"volume\": 28947,\n" +
            "            \"closeAsk\": 1.06871,\n" +
            "            \"openBid\": 1.07378,\n" +
            "            \"highAsk\": 1.07594,\n" +
            "            \"lowBid\": 1.06741,\n" +
            "            \"closeBid\": 1.06853,\n" +
            "            \"time\": \"2015-11-15T22:00:00.000000Z\",\n" +
            "            \"openAsk\": 1.0746,\n" +
            "            \"highBid\": 1.07574,\n" +
            "            \"lowAsk\": 1.06757,\n" +
            "            \"complete\": true\n" +
            "        },\n" +
            "        {\n" +
            "            \"volume\": 27486,\n" +
            "            \"closeAsk\": 1.0644,\n" +
            "            \"openBid\": 1.06842,\n" +
            "            \"highAsk\": 1.06914,\n" +
            "            \"lowBid\": 1.06303,\n" +
            "            \"closeBid\": 1.06409,\n" +
            "            \"time\": \"2015-11-16T22:00:00.000000Z\",\n" +
            "            \"openAsk\": 1.0687,\n" +
            "            \"highBid\": 1.06898,\n" +
            "            \"lowAsk\": 1.06318,\n" +
            "            \"complete\": true\n" +
            "        },\n" +
            "        {\n" +
            "            \"volume\": 26843,\n" +
            "            \"closeAsk\": 1.06612,\n" +
            "            \"openBid\": 1.06403,\n" +
            "            \"highAsk\": 1.06934,\n" +
            "            \"lowBid\": 1.06156,\n" +
            "            \"closeBid\": 1.06578,\n" +
            "            \"time\": \"2015-11-17T22:00:00.000000Z\",\n" +
            "            \"openAsk\": 1.06434,\n" +
            "            \"highBid\": 1.06919,\n" +
            "            \"lowAsk\": 1.06186,\n" +
            "            \"complete\": true\n" +
            "        },\n" +
            "        {\n" +
            "            \"volume\": 31365,\n" +
            "            \"closeAsk\": 1.07357,\n" +
            "            \"openBid\": 1.0659,\n" +
            "            \"highAsk\": 1.07647,\n" +
            "            \"lowBid\": 1.06544,\n" +
            "            \"closeBid\": 1.07326,\n" +
            "            \"time\": \"2015-11-18T22:00:00.000000Z\",\n" +
            "            \"openAsk\": 1.0661,\n" +
            "            \"highBid\": 1.07631,\n" +
            "            \"lowAsk\": 1.06565,\n" +
            "            \"complete\": true\n" +
            "        },\n" +
            "        {\n" +
            "            \"volume\": 26337,\n" +
            "            \"closeAsk\": 1.06487,\n" +
            "            \"openBid\": 1.07315,\n" +
            "            \"highAsk\": 1.07404,\n" +
            "            \"lowBid\": 1.06392,\n" +
            "            \"closeBid\": 1.06433,\n" +
            "            \"time\": \"2015-11-19T22:00:00.000000Z\",\n" +
            "            \"openAsk\": 1.07365,\n" +
            "            \"highBid\": 1.07375,\n" +
            "            \"lowAsk\": 1.06416,\n" +
            "            \"complete\": true\n" +
            "        },\n" +
            "        {\n" +
            "            \"volume\": 26374,\n" +
            "            \"closeAsk\": 1.06373,\n" +
            "            \"openBid\": 1.06364,\n" +
            "            \"highAsk\": 1.06579,\n" +
            "            \"lowBid\": 1.05916,\n" +
            "            \"closeBid\": 1.0635,\n" +
            "            \"time\": \"2015-11-22T22:00:00.000000Z\",\n" +
            "            \"openAsk\": 1.06437,\n" +
            "            \"highBid\": 1.06563,\n" +
            "            \"lowAsk\": 1.05943,\n" +
            "            \"complete\": true\n" +
            "        },\n" +
            "        {\n" +
            "            \"volume\": 27341,\n" +
            "            \"closeAsk\": 1.0644,\n" +
            "            \"openBid\": 1.06343,\n" +
            "            \"highAsk\": 1.06744,\n" +
            "            \"lowBid\": 1.06187,\n" +
            "            \"closeBid\": 1.06416,\n" +
            "            \"time\": \"2015-11-23T22:00:00.000000Z\",\n" +
            "            \"openAsk\": 1.06369,\n" +
            "            \"highBid\": 1.06729,\n" +
            "            \"lowAsk\": 1.06205,\n" +
            "            \"complete\": true\n" +
            "        },\n" +
            "        {\n" +
            "            \"volume\": 33478,\n" +
            "            \"closeAsk\": 1.06255,\n" +
            "            \"openBid\": 1.06419,\n" +
            "            \"highAsk\": 1.06902,\n" +
            "            \"lowBid\": 1.05656,\n" +
            "            \"closeBid\": 1.06232,\n" +
            "            \"time\": \"2015-11-24T22:00:00.000000Z\",\n" +
            "            \"openAsk\": 1.06458,\n" +
            "            \"highBid\": 1.06888,\n" +
            "            \"lowAsk\": 1.05678,\n" +
            "            \"complete\": true\n" +
            "        },\n" +
            "        {\n" +
            "            \"volume\": 14193,\n" +
            "            \"closeAsk\": 1.06118,\n" +
            "            \"openBid\": 1.0623,\n" +
            "            \"highAsk\": 1.06284,\n" +
            "            \"lowBid\": 1.05994,\n" +
            "            \"closeBid\": 1.06088,\n" +
            "            \"time\": \"2015-11-25T22:00:00.000000Z\",\n" +
            "            \"openAsk\": 1.06267,\n" +
            "            \"highBid\": 1.06267,\n" +
            "            \"lowAsk\": 1.06008,\n" +
            "            \"complete\": true\n" +
            "        },\n" +
            "        {\n" +
            "            \"volume\": 18482,\n" +
            "            \"closeAsk\": 1.05963,\n" +
            "            \"openBid\": 1.0607,\n" +
            "            \"highAsk\": 1.06393,\n" +
            "            \"lowBid\": 1.05675,\n" +
            "            \"closeBid\": 1.05876,\n" +
            "            \"time\": \"2015-11-26T22:00:00.000000Z\",\n" +
            "            \"openAsk\": 1.06145,\n" +
            "            \"highBid\": 1.06375,\n" +
            "            \"lowAsk\": 1.0569,\n" +
            "            \"complete\": true\n" +
            "        },\n" +
            "        {\n" +
            "            \"volume\": 3386,\n" +
            "            \"closeAsk\": 1.05897,\n" +
            "            \"openBid\": 1.05872,\n" +
            "            \"highAsk\": 1.05961,\n" +
            "            \"lowBid\": 1.05699,\n" +
            "            \"closeBid\": 1.05882,\n" +
            "            \"time\": \"2015-11-29T22:00:00.000000Z\",\n" +
            "            \"openAsk\": 1.05931,\n" +
            "            \"highBid\": 1.05935,\n" +
            "            \"lowAsk\": 1.05714,\n" +
            "            \"complete\": false\n" +
            "        }\n" +
            "    ]\n" +
            "}";
}