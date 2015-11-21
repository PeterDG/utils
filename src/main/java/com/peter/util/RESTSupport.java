package com.peter.util;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;

import java.util.ArrayList;
import java.util.Map;

import static com.jayway.restassured.RestAssured.given;

/**
 * Created by PEDRO GUTIERREZ on 26/08/2015.
 */
public class RESTSupport {
    public String host;
    public String port;
    public String service;


    public RESTSupport(String host, String port, String service) {
        this.host = host;
        this.port = port;
        this.service = service;
        init();
    }

    public void init() {
        RestAssured.baseURI = host;
//        RestAssured.port = Integer.parseInt(port);
        RestAssured.basePath = "/" + service;
    }

    public Response post(String jsonGivenBody) {
        Response r =
                given().
                        header("Content-Type", "application/json").
                        body(jsonGivenBody).
                        when().
                        post().
                        then().
                        extract().
                        response();
        return r;
    }

    public Response get( Map<String,String> headers, Map<String,String> params) {
        Response r =
                given().
                        headers(headers).
                        params(params).
                        when().
                        get().
                        then().
                        extract().
                        response();
        return r;
    }

}
