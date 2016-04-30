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
    public Map<String, String> headers;
    public Map<String, String> params;
    public Map<String, String> queryParams;
    public String body;
    public Response response;
    public Type type;
    public URL uri;

    public REST(String host, String port, String service) {
        uri=new URL(host+":"+port+"/"+service);
        init();
    }

    public REST(String host, String service) {
        uri=new URL(host+"/"+service);
        init();
    }

    public REST(String url) {
        uri=new URL(url);
        init();
    }

    public REST(String url, Type type) {
        this(url);
        this.type = type;
    }

    public void init() {
        RestAssured.baseURI =  uri.getProtocol()+"://"+uri.getHost();
        int port=uri.url.getPort();
        RestAssured.basePath = uri.getPath();
        if (port != 0) RestAssured.port = port;
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
                        queryParameters(queryParams !=null ? queryParams :new HashMap<>()).
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
        if (type.equals(Type.GET))
            response = get();
        if (type.equals(Type.POST))
            response = post();
        return response;
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

    public void setType(Type type) {
        this.type = type;
    }

    public void queryParams(Map<String, String> queryParams) {
        this.queryParams = queryParams;
    }

    @Override
    public String dispatch() {
        send();
        return response.prettyPrint();
    }

}
