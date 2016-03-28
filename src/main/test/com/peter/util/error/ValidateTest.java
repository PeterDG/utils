package com.peter.util.error;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Peter on 3/28/2016.
 */
public class ValidateTest {
    public Validate validate;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testFail() throws Exception {
        String received="1";
        String expected="2";
        String info = "additional";
        validate = new Validate(received,expected,info);
        validate.fail();
    }

    @Test
    public void testFailNoInfo() throws Exception {
        String received="1";
        String expected="2";
        validate = new Validate(received,expected);
        validate.fail();
    }
}