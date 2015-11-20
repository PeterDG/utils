package util;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.path.json.config.JsonPathConfig;
import com.jayway.restassured.response.Response;

import static com.jayway.restassured.RestAssured.basic;
import static com.jayway.restassured.RestAssured.given;


import java.io.IOException;

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

    public Response get( String header,String headerValue,String param1,String param1Value) {
        Response r =
                given().
                        header(header, headerValue).
                        param(param1,param1Value).
                        when().
                        get().
                        then().
                        extract().
                        response();
        return r;
    }

}
