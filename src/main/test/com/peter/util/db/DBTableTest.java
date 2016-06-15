package com.peter.util.db;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Peter on 6/15/2016.
 */
public class DBTableTest {
    public static List<String> data = Arrays.asList(new String[]{"GND(date)",
            "GND(user)",
            "0.0",
            "0.0",
            "GND(context)",
            "RND(0,1)",
    });
    @Test
    public void setList() throws Exception {
        DBTable table = new DBTable(new ArrayList<String>(data));
        assertTrue(table.column1.equals("GND(date)"));
        assertTrue(table.column6.equals("RND(0,1)"));
    }

}