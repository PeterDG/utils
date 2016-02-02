package com.peter.util.time;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by Peter on 2/2/2016.
 */
public class TimeIsDateTest {
    private Time time;

    @Before
    public void init() {
        time = new Time();
    }

    @Test
    public void testDateIsNull() {
        assertFalse(time.isDate(null, "dd/MM/yyyy"));
    }

    @Test
    public void testDayIsInvalid() {
        assertFalse(time.isDate("32/02/2012", "dd/MM/yyyy"));
    }

    @Test
    public void testMonthIsInvalid() {
        assertFalse(time.isDate("31/20/2012", "dd/MM/yyyy"));
    }

    @Test
    public void testYearIsInvalid() {
        assertFalse(time.isDate("31/20/19991", "dd/MM/yyyy"));
    }

    @Test
    public void testDateFormatIsInvalid() {
        assertFalse(time.isDate("2012/02/20", "dd/MM/yyyy"));
    }

    @Test
    public void testDateFeb29_2012() {
        assertTrue(time.isDate("29/02/2012", "dd/MM/yyyy"));
    }

    @Test
    public void testDateFeb29_2011() {
        assertFalse(time.isDate("29/02/2011", "dd/MM/yyyy"));
    }

    @Test
    public void testDateFeb28() {
        assertTrue(time.isDate("28/02/2011", "dd/MM/yyyy"));
    }

    @Test
    public void testDateIsValid_1() {
        assertTrue(time.isDate("31/01/2012", "dd/MM/yyyy"));
    }

    @Test
    public void testDateIsValid_2() {
        assertTrue(time.isDate("30/04/2012", "dd/MM/yyyy"));
    }

    @Test
    public void testDateIsValid_3() {
        assertTrue(time.isDate("31/05/2012", "dd/MM/yyyy"));
    }
}
