package com.peter.util.data.util;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Peter on 5/20/2016.
 */
public class ArraysListTest {

    public enum rsContextTable {
        ctx_date("ctx_date"),
        ctx_user("ctx_user"),
        ctx_latitude("ctx_latitude"),
        ctx_longitude("ctx_longitude"),
        ctx_body("ctx_body"),
        ctx_risk("ctx_risk");

        public final String param;
        public final String name;

        rsContextTable(final String name) {
            this.param = "p_"+name;
            this.name = name;
        }
    }

    @Test
    public void testEnumToArrayList() throws Exception {
        ArrayList<String> arraysList = ArraysList.enumToArrayList(rsContextTable.class);
        List<String> list = new ArrayList<>(Arrays.asList("ctx_date", "ctx_user", "ctx_latitude", "ctx_longitude", "ctx_body", "ctx_risk"));
        assertTrue(arraysList.equals(list));
    }
}