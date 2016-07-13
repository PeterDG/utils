package com.peter.util.db;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

/**
 * Created by Peter on 7/13/2016.
 */
public class DBTableTest {
    public DBTable table;
    @Before
    public void setUp() throws Exception {
        ArrayList<DBRow> dbRows = new ArrayList<DBRow>();
        DBRow headers = new DBRow();
        DBRow dbRowB = new DBRow();
        DBRow dbRowC = new DBRow();
        headers.column1="code";
        dbRowB.column1="1118";
        dbRowC.column1="1119";
        dbRows.add(dbRowB);
        dbRows.add(dbRowC);
        table=new DBTable("testTable",headers,dbRows);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void cloneMethod() {
        DBTable clone = table.clone();
        assertTrue(table.values.size()==2);
        assertTrue(table.headers.getAsList().size()==1);
          assertTrue(!table.clone().equals(table));
    }

}