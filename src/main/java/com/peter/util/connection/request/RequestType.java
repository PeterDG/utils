package com.peter.util.connection.request;

/**
 * Created by Peter on 4/29/2016.
 */


public interface RequestType {
    public enum Type {SOAP,GET, POST}
    String dispatch();
}
