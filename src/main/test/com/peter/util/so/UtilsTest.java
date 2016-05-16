package com.peter.util.so;

import com.peter.util.time.Time;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.*;

/**
 * Created by Peter on 5/12/2016.
 */
public class UtilsTest {
    public SOUtils soUtils;
    public String cmdPath = "E:\\Development\\Projects\\utils\\src\\main\\resources\\so\\admin.exe";

    @Before
    public void before() {
        soUtils = new SOUtils();
    }

    @After
    public void after() throws Exception {
        soUtils.syncTime(cmdPath);
    }

    @Test
    public void testSetSODate() throws Exception {
        soUtils.setSODate("05-20-2016", cmdPath); //mm-dd-yy
        int month = Time.getCurrentTime(Calendar.MONTH);
        int day = Time.getCurrentTime(Calendar.DAY_OF_MONTH);
        int year = Time.getCurrentTime(Calendar.YEAR);
        assertTrue(month == 5 && day == 20 && year == 2016);
    }

    @Test
    public void testSetSOHour() throws Exception {
        soUtils.setSOHour("08:00", cmdPath);
        int hours = Time.getCurrentTime(Calendar.HOUR_OF_DAY);
        assertTrue(hours == 8);
    }

    @Test
    public void testSetSOTime() throws Exception {
        soUtils.setSOTime("05-20-2016", "09:00", cmdPath);
        int hours = Time.getCurrentTime(Calendar.HOUR_OF_DAY);
        int month = Time.getCurrentTime(Calendar.MONTH);
        int day = Time.getCurrentTime(Calendar.DAY_OF_MONTH);
        int year = Time.getCurrentTime(Calendar.YEAR);
        assertTrue(hours == 9 && month == 5 && day == 20 && year == 2016);
    }

    @Test
    public void testSetSOTimeDate() throws Exception {
        soUtils.setSOTime("2016-05-20 09:00:00", cmdPath);
        int hours = Time.getCurrentTime(Calendar.HOUR_OF_DAY);
        int month = Time.getCurrentTime(Calendar.MONTH);
        int day = Time.getCurrentTime(Calendar.DAY_OF_MONTH);
        int year = Time.getCurrentTime(Calendar.YEAR);
        assertTrue(hours == 9 && month == 5 && day == 20 && year == 2016);
    }
}