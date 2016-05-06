package com.peter.util.time;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

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

    @Test
    public void testAddTimes2Date() throws Exception {
        Date date =Time.string2IsoDate("2015-01-1T03:12:07.796Z");
        HashMap<Integer,Integer> map=new HashMap();
        map.put(Calendar.YEAR,10);
        map.put(Calendar.MONTH,10);
        map.put(Calendar.DAY_OF_YEAR,10);
        map.put(Calendar.HOUR,10);
        map.put(Calendar.MINUTE,10);
        map.put(Calendar.SECOND,10);
        date=Time.addTimes2Date(date,map);
        new SimpleDateFormat(Time.DEFAULT_DATE_FORMAT).format(date).toString().equals("2025-11-11T13:22:17");
    }

    @Test
    public void testSubtractTimes2Date() throws Exception {
        Date date =Time.string2IsoDate("2025-11-11T13:22:17.796Z");
        HashMap<Integer,Integer> map=new HashMap();
        map.put(Calendar.YEAR,-10);
        map.put(Calendar.MONTH,-10);
        map.put(Calendar.DAY_OF_YEAR,-10);
        map.put(Calendar.HOUR,-10);
        map.put(Calendar.MINUTE,-10);
        map.put(Calendar.SECOND,-10);
        date=Time.addTimes2Date(date,map);
        new SimpleDateFormat(Time.DEFAULT_DATE_FORMAT).format(date).toString().equals("2015-01-01T03:12:07");
    }
}