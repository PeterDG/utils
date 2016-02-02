package com.peter.util.time;

import com.peter.util.time.ZonedDate;
import org.junit.Test;

/**
 * Created by Peter on 1/10/2016.
 */
public class DateTest {

    @Test
    public void testZoneOffset() throws Exception {
        ZonedDate date = new ZonedDate("2014-09-14T17:00:00+00:00");
        System.out.println(date.toString());
        date.setZoneOffset("America/Bogota");
        System.out.println(date.toString());
    }
}