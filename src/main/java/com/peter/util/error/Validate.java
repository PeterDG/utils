package com.peter.util.error;

/**
 * Created by Peter on 3/28/2016.
 */

import junit.framework.Assert;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validate <T>{
    public T receivedValue;
    public T expectedValue;
    public String info;
    public String message;

    public Validate(T receivedValue, T expectedValue) {
        this.receivedValue = receivedValue;
        this.expectedValue = expectedValue;
    }

    public Validate(T receivedValue, T expectedValue, String info) {
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

    public static boolean regexMatcher (String expected, String regex){
        Pattern pattern = Pattern.compile(expected);
        Matcher matcher = pattern.matcher(regex);
        return matcher.find();
    }

    public boolean regex (){
        return regexMatcher((String)expectedValue,(String)receivedValue);
    }



}
