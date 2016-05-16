package com.peter.util.so;

import org.junit.Before;
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

    @Test
    public void testSetSODate() throws Exception {
        soUtils.setSODate("05-09-2029", cmdPath); //mm-dd-yy
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int year = cal.get(Calendar.YEAR);
        assertTrue(month == 5 && day == 9 && year == 2029);
    }

    @Test
    public void testSetSOHour() throws Exception {
        soUtils.setSOHour("08:00", cmdPath);
        Calendar cal = Calendar.getInstance();
        int hours = cal.get(Calendar.HOUR_OF_DAY);
        assertTrue(hours == 8);
    }

    @Test
    public void testSetSOTime() throws Exception {
        soUtils.setSOTime("04-09-2029", "09:00", cmdPath);
        Calendar cal = Calendar.getInstance();
        int hours = cal.get(Calendar.HOUR_OF_DAY);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int year = cal.get(Calendar.YEAR);
        assertTrue(hours == 9 && month == 4 && day == 9 && year == 2029);
    }

    @Test
    public void testSetSOTimeDate() throws Exception {
        soUtils.setSOTime("2029-04-09 09:00:00", cmdPath);
        Calendar cal = Calendar.getInstance();
        int hours = cal.get(Calendar.HOUR_OF_DAY);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int year = cal.get(Calendar.YEAR);
        assertTrue(hours == 9 && month == 4 && day == 9 && year == 2029);
    }
}