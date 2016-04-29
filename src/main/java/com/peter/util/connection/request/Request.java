package com.peter.util.connection.request;

import com.peter.util.connection.REST;
import com.peter.util.connection.soap.SOAP;

import java.time.Instant;
import java.util.Date;

/**
 * Created by Peter on 4/29/2016.
 */
public class Request extends Thread{
    public Thread t;
    public RequestType resource;
    public Instant requestTime;
    public Instant responseTime;
    public boolean verbose = false;

    public Request(REST resource) {
        this.resource = resource;
    }

    public Request(SOAP resource) {
        this.resource = resource;
    }

    public String send() {
        requestTime = new Date().toInstant();
        if (verbose) System.out.println(requestTime + " - Sending resource: " + getName());
        String response= resource.dispatch();
        responseTime = new Date().toInstant();
        if (verbose) System.out.println(responseTime + " - Recevived Response: " + getName());
        return response;
    }

    public void start() {
        if (verbose) System.out.println("Starting " + getName());
        if (t == null) {
            t = new Thread(this, getName());
            t.start();
        }
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }
}
