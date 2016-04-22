package com.peter.util.error;

/**
 * Created by Peter on 3/28/2016.
 */

import junit.framework.Assert;

public class Validate {
    public Object receivedValue;
    public Object expectedValue;
    public String info;
    public String message;

    public Validate(Object receivedValue, Object expectedValue) {
        this.receivedValue = receivedValue;
        this.expectedValue = expectedValue;
    }

    public Validate(Object receivedValue, Object expectedValue, String info) {
        this.receivedValue = receivedValue;
        this.expectedValue = expectedValue;
        this.info = info;
    }

    public void fail() {
        String add= info != null ? " \n Info: \n" + info:"";
        message = "\nERROR!!" +
                " \n Expected value:" + expectedValue +
                " \n Received value:" + receivedValue +
                add ;
        Assert.fail(message);
    }

    public void equals(){
        if(!receivedValue.equals(expectedValue))
            fail();
    }

}
