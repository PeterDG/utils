package com.peter.util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Peter on 1/23/2016.
 */
public class CMDTest {

    @Test
    public void testExecute() throws Exception {
        CMD.execute("calc");
    }
}