package com.peter.util.data;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by Peter on 3/13/2016.
 */
public class StringsTest {

    long startTime;
    long endTime;
    String expectedValue="";
    String receivedValue="";
    ArrayList<String> strTestList;

    @Before
    public void setUp() throws Exception {
        String test = "HOLA ESTO NO ES CAMEL CASE";
        strTestList=new ArrayList<>();
        for(int i=0;i<10;i++){
            strTestList.add(test);
        }
        startTime = System.currentTimeMillis();
    }

    @After
    public void tearDown() throws Exception {
        endTime = System.currentTimeMillis();
        System.out.println("Execution time: " + (endTime - startTime)+" ms");
        assertTrue(receivedValue.equals(expectedValue));
    }

    @Test
    public void testToCamelCase() throws Exception {
        receivedValue = Strings.toCamelCase("HOLA_ESTO_NO_ES_CAMEL_CASE");
        expectedValue = "HolaEstoNoEsCamelCase";
    }


    @Test
    public void testCapitalize() throws Exception {
        receivedValue = Strings.capitalize("HOLA ESTO NO ES CAMEL CASE");
        expectedValue = "Hola Esto No Es Camel Case";
    }

    @Test
    public void testCapitalizeMultipleSpace() throws Exception {
        receivedValue = Strings.capitalize("HOLA        ESTO NO ES CAMEL CASE");
        expectedValue = "Hola Esto No Es Camel Case";
    }


    @Test
    public void testCapitalizeList() throws Exception {
        Strings.capitalize(strTestList);
    }

    @Test
    public void testCompare() throws Exception {
        String a="I change the size of the DetectId 15window to 1081p";
        String b="I change the size of the DetectId 16window to 1080p";
        Strings.compare(a,b);
    }
}