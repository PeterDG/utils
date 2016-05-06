package com.peter.util.math;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Peter on 5/6/2016.
 */
public class MathsTest {

    @Test
    public void testRnd() throws Exception {
       int number=Maths.rnd(1,10);
//        System.out.print(number);
        assertTrue(number>=1 && number<=10);
    }
}