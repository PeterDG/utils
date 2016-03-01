package com.peter.util.connection;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;

import java.util.ArrayList;
import java.util.Map;

import static com.jayway.restassured.RestAssured.given;

/**
 * Created by PEDRO GUTIERREZ on 26/08/2015.
 */
public class REST {
    public String host;
    public String port;
    public String service;
    public String url;


    public REST(String host, String port, String service) {
        this.host = host;
        this.port = port;
        this.service = service;
        init();
    }

    public REST(String host, String service) {
        setData(host);
        this.service = service;
        init();
    }

    public REST(String url) {
        setData(url);
        init();
    }

    public void setData(String url){
        this.url=url;
        String tmp = url;
        String [] splitHost= tmp.split(":\\d");
        this.host =splitHost[0];
        if(splitHost.length>1){
            tmp=tmp.replace(host+":","");
            String [] splitPort= tmp.split("/");
            this.port=splitPort[0];
            if(splitPort.length>1){
                this.service = tmp.replace(port,"");
            }
        }
    }

    public void init() {
        RestAssured.baseURI = host;
        RestAssured.basePath = service.charAt(0)=='/' ? service :"/" + service;
        if(port!=null) RestAssured.port = Integer.parseInt(port);
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

    public Response post(Map<String,String> headers,String strBody) {
        Response r =
                given().
                        headers(headers).
                        body(strBody).
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
