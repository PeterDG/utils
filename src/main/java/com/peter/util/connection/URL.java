package com.peter.util.connection;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Peter on 4/30/2016.
 */
public class URL {
    public java.net.URL url;

    public URL(String url) {
        url = validateData(url);
        try {
            this.url = new java.net.URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private String validateData(String url) {
        String exp = "://";
        String[] split = url.split(exp);
        url = url.split("://")[1].replaceAll("//{1,100}", "/");
        return split[0] + exp + url;
    }


    public String getHost() {
        return url.getHost();
    }

    public String getPath() {
        return url.getPath();
    }

    public String getProtocol() {
        return url.getProtocol();
    }

    public Map<String, String> getQueryParams() {
        Map<String, String> queryMap = new HashMap<>();
        String query = url.getQuery();
        if(query!=null) {
            if (query.contains("&")) {
                String[] params = query.split("&");
                for (String param : params) {
                    String[] data = param.split("=");
                    queryMap.put(data[0], data[1]);
                }
            } else {
                String[] data = query.split("=");
                queryMap.put(data[0], data[1]);
            }
        }
        return queryMap;
    }
}
