package com.peter.util.db;

import com.google.common.base.Optional;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import sys.Settings;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Peter on 1/22/2016.
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PostgresDBManagerImplTest {
    public DBManager db;
    public Settings settings;
    @Before
    public void before() throws Exception {
        settings= Settings.getInstance();
        Credentials credentials = new Credentials(settings.getJdbcPostgresUsername(),settings.getJdbcPostgresPassword());
        ConnectionInfo connectionInfo=new ConnectionInfo(settings.jdbcPostgresURL, Optional.of(credentials));
        db =DBManagerFactory.buildDBManager(connectionInfo);
    }

    @Test
    public void test01ExecuteQuery() throws Exception {
        db.getConnection();
        ArrayList<HashMap> data = db.executeQuery("select * from channel_factor");
        assertTrue(data.size()==14);
    }

    @Test
    public void test02GetConnection() throws Exception {
        Connection connection= (Connection) db.getConnection();
        assertTrue(!connection.isClosed());
    }

    @Test
    public void test03ExecuteSQLFile() throws Exception {
        db.getConnection();
        ArrayList<ArrayList<HashMap>> data = db.executeSQLFile("src/main/test/resources/db/postgres/file.sql");
        assertTrue(data.size()==2);
        assertTrue(db.isQuerySuccessful());
    }

    @Test
    public void test04DCreateDB() throws Exception {
        db.getConnection();
        db.createDB("testABC");
        assertTrue(db.isQuerySuccessful());
    }

    @Test
    public void test05CreateDBWithoutDBName() throws Exception {
        Credentials credentials = new Credentials(settings.getJdbcPostgresUsername(),settings.getJdbcPostgresPassword());
        ConnectionInfo connectionInfo=new ConnectionInfo(settings.jdbcPostgresURL.replaceAll("/"+settings.jdbcPostgresURL.split("/")[3],""), Optional.of(credentials));
        db =DBManagerFactory.buildDBManager(connectionInfo);
        db.getConnection();
        db.createDB("testABCD");
        assertTrue(db.isQuerySuccessful());
    }

    @Test
    public void test06DeleteDB() throws Exception {
        db.getConnection();
        db.deleteDB("testABC");
        db.deleteDB("testABCD");
        assertTrue(db.isQuerySuccessful());
    }

    @Test
    public void test07ExistDB() throws Exception {
        db.getConnection();
        String dbName="detectid";
        boolean flag =db.existDB(dbName);
        assertTrue(flag);
    }

    @Test
    public void test08NoExistDB() throws Exception {
        boolean flag =db.existDB("testABDCE");
        assertTrue(!flag);
    }

    @Test
    public void test09CreateTable() throws Exception {
        db.getConnection();
        db.createTable("testTable","id serial NOT NULL,code text,name text", Optional.of("id"));
        assertTrue(db.isQuerySuccessful());
    }

    @Test
    public void test10InsetTable() throws Exception {
        db.getConnection();
        db.insertTable("testTable","code","1110");
        assertTrue(db.isQuerySuccessful());
    }

    @Test
    public void test10_1MultipleInsetTable() throws Exception {
        db.getConnection();
        ArrayList<String> strList = new ArrayList<String>();
        strList.add("'1111','NameA'");
        strList.add("'1112','NameB'");
        strList.add("'1113','NameC'");
        strList.add("'1113','NameC'");
        strList.add("'1113','NameC'");
        strList.add("'1113','NameC'");
        strList.add("'1113','NameC'");
        strList.add("'1113','NameC'");
        strList.add("'1113','NameC'");
        strList.add("'1113','NameC'");
        strList.add("'1113','NameC'");
        strList.add("'1113','NameC'");
        strList.add("'1113','NameC'");
        strList.add("'1113','NameC'");
        strList.add("'1113','NameC'");
        strList.add("'1113','NameC'");
        strList.add("'1113','NameC'");
        strList.add("'1113','NameC'");
        strList.add("'1113','NameC'");
        strList.add("'1113','NameC'");
        strList.add("'1113','NameC'");
        strList.add("'1113','NameC'");
        strList.add("'1113','NameC'");
        strList.add("'1113','NameC'");
        strList.add("'1113','NameC'");
        strList.add("'1113','NameC'");
        strList.add("'1113','NameC'");
        strList.add("'1113','NameC'");
        strList.add("'1113','NameC'");
        strList.add("'1113','NameC'");
        strList.add("'1113','NameC'");
        strList.add("'1113','NameC'");
        strList.add("'1113','NameC'");
        strList.add("'1113','NameC'");
        strList.add("'1113','NameC'");
        strList.add("'1113','NameC'");
        strList.add("'1113','NameC'");
        strList.add("'1113','NameC'");
        strList.add("'1113','NameC'");
        strList.add("'1113','NameC'");
        strList.add("'1113','NameC'");
        strList.add("'1113','NameC'");
        strList.add("'1113','NameC'");
        strList.add("'1113','NameC'");
        strList.add("'1113','NameC'");
        strList.add("'1113','NameC'");
        strList.add("'1113','NameC'");
        strList.add("'1113','NameC'");
        strList.add("'1113','NameC'");
        strList.add("'1113','NameC'");
        ArrayList<String> columnNamesList=new ArrayList<>(Arrays.asList(new String[] { "code" , "name" }));
        db.insertTable("testTable",columnNamesList,strList);
        assertTrue(db.isQuerySuccessful());
    }

    @Test
    public void test11UpdateTableWithWhere() throws Exception {
        db.getConnection();
        db.updateTable("testTable","code","1234",Optional.of("code='1110'"));
        assertTrue(db.isQuerySuccessful());
    }

    @Test
    public void test12UpdateTableWithoutWhere() throws Exception {
        db.getConnection();
        db.updateTable("testTable","code","1134",Optional.absent());
        assertTrue(db.isQuerySuccessful());
    }

    @Test
    public void test13SelectTableWithoutWhere() throws Exception {
        db.getConnection();
        List<HashMap> data = db.selectTable("testTable", "*", Optional.absent());
        assertTrue(db.isQuerySuccessful());
    }

    @Test
    public void test14SelectTableWithWhere() throws Exception {
        db.getConnection();
        List<HashMap> data = db.selectTable("testTable", "*", Optional.of("code='1034'"));
        assertTrue(db.isQuerySuccessful());
    }


    @Test
    public void test15CleanTable() throws Exception {
        db.getConnection();
        db.cleanTable("testTable");
        assertTrue(db.isQuerySuccessful());
    }

    @Test
    public void test16DeleteTable() throws Exception {
        db.getConnection();
        db.deleteTable("testTable");
        assertTrue(db.isQuerySuccessful());
    }


    @Test
    public void test17ExecuteSQLFileReplaceParameters() throws Exception {
        db.getConnection();
        ArrayList pairsToReplace = new ArrayList() ;
        pairsToReplace.add(new String []{"p_cli_description","testUserDescription"});
        pairsToReplace.add(new String []{"p_cli_date_added","2016-02-27 12:56:49.221"});
        pairsToReplace.add(new String []{"p_cli_shared_key","testUserSharedKey"});
        pairsToReplace.add(new String []{"p_cli_email","testUserEmail"});
        ArrayList<ArrayList<HashMap>> data = db.executeSQLFile("src/main/test/resources/db/postgres/insert.sql",pairsToReplace);
        ArrayList<HashMap> dataAssertion =  db.executeQuery("SELECT count(*) FROM client WHERE cli_shared_key='testUserSharedKey'");
        assertTrue(dataAssertion.get(0).get("count").equals(1L));
        db.executeQuery("DELETE FROM client WHERE cli_shared_key='testUserSharedKey'");
    }

}