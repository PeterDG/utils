package com.peter.util.time;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertTrue;

/**
 * Created by Peter on 2/2/2016.
 */
public class TimeTest {

    @Test
    public void testToDate() throws Exception {
        Date date =Time.string2IsoDate("2015-06-23T03:52:07.796Z");
        assertTrue(date.toString().equals("Tue Jun 23 03:52:07 COT 2015"));
    }

    @Test
    public void testIsISODate() throws Exception {
       assertTrue(Time.isISODate("2015-06-23T03:52:07.796Z"));
    }

    @Test
    public void testString2Date() throws Exception {
        Date date=Time.string2Date("2015-06-23T18:28:30.859Z",Time.DEFAULT_ISO_DATE_FORMAT,"GTM+00:00");
        assertTrue(date.toInstant().toString().toString().equals("2015-06-23T18:28:30.859Z"));
    }
}