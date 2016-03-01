package com.peter.util.connection;

import com.jayway.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Peter on 2/29/2016.
 */
public class SOAP {
    public REST rest;
    public String soapXMLRequest;
    public Response response;

    public SOAP(String url, String soapXMLRequest) {
        this.soapXMLRequest = soapXMLRequest;
        this.rest = new REST(url);
    }

    public Response send(){
        Map<String, String> headersMaps = new HashMap<String,String>() {{put("Content-Type", "text/xml");}};
        response= rest.post(headersMaps,soapXMLRequest);
        return response;
    }
}
