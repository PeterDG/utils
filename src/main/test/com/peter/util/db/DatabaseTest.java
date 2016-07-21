package com.peter.util.db;

import com.google.common.base.Optional;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.peter.util.sys.Settings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Peter on 7/13/2016.
 */
public class DatabaseTest {

    public Database database;
    public Settings settings;
    public DBTableTest dbTableTest;

    @Before
    public void setUp() throws Exception {
        settings = Settings.getInstance();
        database = new Database(settings.getJdbcPostgresURL(), settings.getJdbcPostgresUsername(), settings.getJdbcPostgresPassword());
        dbTableTest = new DBTableTest();
        dbTableTest.setUp();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void addTable() throws Exception {
        database.addTable(dbTableTest.table);
        assertTrue(database.tablesMap.size()==1);
    }

    @Test
    public void insertTable() throws Exception {
        database.addTable(dbTableTest.table);
        database.createTable("testTable","id serial NOT NULL,code text,name text", Optional.of("id"));
        database.insertTable(database.tablesMap.get("testTable"));
        assertTrue(database.isQuerySuccessful());
        database.deleteTable(dbTableTest.table);
        assertTrue(database.isQuerySuccessful());
    }

    @Test
    public void deleteTable() throws Exception {

    }

    @Test
    public void cleanTable() throws Exception {
        database.addTable(dbTableTest.table);
        database.createTable("testTable","id serial NOT NULL,code text,name text", Optional.of("id"));
        database.insertTable(database.tablesMap.get("testTable"));
        assertTrue(database.isQuerySuccessful());
        database.cleanTable(dbTableTest.table);
        assertTrue(database.isQuerySuccessful());
        database.deleteTable(dbTableTest.table);
        assertTrue(database.isQuerySuccessful());
    }

    @Test
    public void selectTableWithoutWhere() throws Exception {
        database.addTable(dbTableTest.table);
        database.createTable("testTable","id serial NOT NULL,code text,name text", Optional.of("id"));
        assertTrue(database.isQuerySuccessful());
        database.insertTable(database.tablesMap.get("testTable"));
        assertTrue(database.isQuerySuccessful());
        List<HashMap> list = database.selectTable(dbTableTest.table, Optional.absent());
        assertTrue(database.isQuerySuccessful());
        assertTrue(list.size()==2);
        database.deleteTable(dbTableTest.table);
        assertTrue(database.isQuerySuccessful());
    }

    @Test
    public void selectTableWithWhere() throws Exception {
        database.addTable(dbTableTest.table);
        database.createTable("testTable","id serial NOT NULL,code text,name text", Optional.of("id"));
        assertTrue(database.isQuerySuccessful());
        database.insertTable(database.tablesMap.get("testTable"));
        assertTrue(database.isQuerySuccessful());
        List<HashMap> list = database.selectTable(dbTableTest.table, Optional.of("code = '1119'"));
        assertTrue(database.isQuerySuccessful());
        assertTrue(list.size()==1);
        database.deleteTable(dbTableTest.table);
        assertTrue(database.isQuerySuccessful());
    }

    @Test
    public void updatetTableWithWhere() throws Exception {
        DBTable updatedTable = dbTableTest.table.clone();
        updatedTable.cleanRows();
        updatedTable.setHeaders(new DBRow(new ArrayList<String>(Arrays.asList("code"))));
        updatedTable.addRow(new DBRow(new ArrayList<String>(Arrays.asList("1154"))));
        database.addTable(dbTableTest.table);
        database.createTable("testTable","id serial NOT NULL,code text,name text", Optional.of("id"));
        assertTrue(database.isQuerySuccessful());
        database.insertTable(database.tablesMap.get("testTable"));
        assertTrue(database.isQuerySuccessful());
        database.updateTable(updatedTable, Optional.of("code = '1119'"));
        assertTrue(database.isQuerySuccessful());
        List<HashMap> list = database.selectTable(dbTableTest.table, Optional.of("code = '1154'"));
        assertTrue(database.isQuerySuccessful());
        assertTrue(list.size()==1);
        database.deleteTable(dbTableTest.table);
        assertTrue(database.isQuerySuccessful());
    }

    @Test
    public void updatetTableWithoutWhere() throws Exception {
        DBTable updatedTable = dbTableTest.table.clone();
        updatedTable.cleanRows();
        updatedTable.setHeaders(new DBRow(new ArrayList<String>(Arrays.asList("code"))));
        updatedTable.addRow(new DBRow(new ArrayList<String>(Arrays.asList("1154"))));
        database.addTable(dbTableTest.table);
        database.createTable("testTable","id serial NOT NULL,code text,name text", Optional.of("id"));
        assertTrue(database.isQuerySuccessful());
        database.insertTable(database.tablesMap.get("testTable"));
        assertTrue(database.isQuerySuccessful());
        database.updateTable(updatedTable, Optional.absent());
        assertTrue(database.isQuerySuccessful());
        List<HashMap> list = database.selectTable(dbTableTest.table, Optional.of("code = '1154'"));
        assertTrue(database.isQuerySuccessful());
        assertTrue(list.size()==2);
        database.deleteTable(dbTableTest.table);
        assertTrue(database.isQuerySuccessful());
    }

    @Test
    public void countRowsTable() throws Exception {
        database.addTable(dbTableTest.table);
        database.createTable("testTable","id serial NOT NULL,code text,name text", Optional.of("id"));
        database.insertTable(database.tablesMap.get("testTable"));
        assertTrue(database.isQuerySuccessful());
        int count=database.countRowsTable(dbTableTest.table,Optional.absent());
        assertTrue(count==2);
        database.deleteTable(dbTableTest.table);
        assertTrue(database.isQuerySuccessful());
    }


}