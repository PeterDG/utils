package com.peter.util.connection;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.peter.util.connection.request.RequestType;

import java.util.HashMap;
import java.util.Map;

import static com.jayway.restassured.RestAssured.given;

/**
 * Created by PEDRO GUTIERREZ on 26/08/2015.
 */
public class REST implements RequestType {
    public String host;
    public String port;
    public String service;
    public String url;
    public Map<String, String> headers;
    public Map<String, String> params;
    public String body;
    public Response response;
    public Operation operation;

    public enum Operation {GET, POST}


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

    public REST(String url, Operation operation) {
        this.operation = operation;
        setData(url);
        init();
    }

    public void setData(String url) {
        this.url = url;
        String tmp = url;
        String[] splitHost = tmp.split(":\\d");
        this.host = splitHost[0];
        if (splitHost.length > 1) {
            tmp = tmp.replace(host + ":", "");
            String[] splitPort = tmp.split("/");
            this.port = splitPort[0];
            if (splitPort.length > 1) {
                this.service = tmp.replace(port, "");
            }
        }
    }

    public void init() {
        RestAssured.baseURI = host;
        RestAssured.basePath = service.charAt(0) == '/' ? service : "/" + service;
        if (port != null) RestAssured.port = Integer.parseInt(port);
    }

    public Response post(String jsonGivenBody) {
        this.body = jsonGivenBody;
        response =
                given().
                        header("Content-Type", "application/json").
                        body(jsonGivenBody).
                        post().
                        then().
                        extract().
                        response();
        return response;
    }

    public Response post(Map<String, String> headers, String strBody) {
        this.headers = headers;
        this.body = strBody;
        response =
                given().
                        headers(headers).
                        body(strBody).
                        when().
                        post().
                        then().
                        extract().
                        response();
        return response;
    }

    public Response post(Map<String, String> headers, Map<String, String> params, String body) {
        this.headers = headers;
        this.params = params;
        this.body = body;
        response =
                given().
                        headers(headers).
                        params(params).
                        body(body).
                        when().
                        post().
                        then().
                        extract().
                        response();
        return response;
    }

    public Response post() {
        response =
                given().
                        headers(headers != null ? headers : new HashMap<>()).
                        params(params != null ? params : new HashMap<>()).
                        body(body != null ? body : "").
                        when().
                        post().
                        then().
                        extract().
                        response();
        return response;
    }

    public Response get(Map<String, String> headers, Map<String, String> params) {
        this.headers = headers;
        this.params = params;
        response =
                given().
                        headers(headers).
                        params(params).
                        when().
                        get().
                        then().
                        extract().
                        response();
        return response;
    }

    public Response get() {
        response =
                given().
                        headers(headers != null ? headers : new HashMap<>()).
                        params(params != null ? params : new HashMap<>()).
                        when().
                        get().
                        then().
                        extract().
                        response();
        return response;
    }

    public Response send() {
        if (operation.equals(Operation.GET))
            response = get();
        if (operation.equals(Operation.POST))
            response = post();
        return response;
    }


    public void url(String url) {
        this.url = url;
    }

    public void body(String body) {
        this.body = body;
    }

    public void params(Map<String, String> params) {
        this.params = params;
    }

    public void headers(Map<String, String> headers) {
        this.headers = headers;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }


    @Override
    public String dispatch() {
        send();
        return response.prettyPrint();
    }

}
