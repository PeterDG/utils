package com.peter.util.db;

import com.google.common.base.Optional;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Peter on 1/22/2016.
 */
public class ConnectionInfoTest {
    public ConnectionInfo connectionInfo;
    public Credentials credentials;
    @Before
    public void before(){
        credentials=new Credentials("test","pass");
        connectionInfo= new ConnectionInfo("jdbc:mongo://ds029847.mongolab.com:29847/tpch",Optional.of(credentials));
    }

    @Test
    public void testGetServer() throws Exception {
        String server =connectionInfo.getServer();
        assertTrue(server.equals("ds029847.mongolab.com"));
    }

    @Test
    public void testGetPort() throws Exception {
      Integer port =connectionInfo.getPort();
        assertTrue(port.equals(29847));
    }

    @Test
    public void testGetDBName() throws Exception {
        String dbName =connectionInfo.getDBName();
        assertTrue(dbName.equals("tpch"));
    }

    @Test
    public void testGetWithoutDBName() throws Exception {
        connectionInfo= new ConnectionInfo("jdbc:mongo://ds029847.mongolab.com:29847/",Optional.of(credentials));
        String dbName =connectionInfo.getDBName();
        assertTrue(dbName=="");
    }

    @Test
    public void testGetWithoutDBNameA() throws Exception {
        connectionInfo= new ConnectionInfo("jdbc:mongo://ds029847.mongolab.com:29847",Optional.of(credentials));
        String dbName =connectionInfo.getDBName();
        assertTrue(dbName=="");
    }

    @Test
    public void testGetJDBCUrlWithDBName() throws Exception {
        String url =connectionInfo.getJDBCUrlWithDBName();
        assertTrue(url.equals("jdbc:mongo://ds029847.mongolab.com:29847/"));
    }

    @Test
    public void testGetJDBCUrlWithoutDBName() throws Exception {
        connectionInfo= new ConnectionInfo("jdbc:mongo://ds029847.mongolab.com:29847",Optional.of(credentials));
        String url =connectionInfo.getJDBCUrlWithDBName();
        assertTrue(url.equals("jdbc:mongo://ds029847.mongolab.com:29847/"));
    }

    @Test
    public void testGetJDBCUrlWithoutDBNameWithSlash() throws Exception {
        connectionInfo= new ConnectionInfo("jdbc:mongo://ds029847.mongolab.com:29847/",Optional.of(credentials));
        String url =connectionInfo.getJDBCUrlWithDBName();
        assertTrue(url.equals("jdbc:mongo://ds029847.mongolab.com:29847/"));
    }

    @Test
    public void testGetDBTypeMongo() throws Exception {
        connectionInfo= new ConnectionInfo("jdbc:mongo://ds029847.mongolab.com:29847/tpch",Optional.of(credentials));
        DBManagerType type =connectionInfo.getDBType();
        assertTrue(type.equals(DBManagerType.MONGO));
    }

    @Test
    public void testGetDBTypePostgres() throws Exception {
        connectionInfo= new ConnectionInfo("jdbc:postgresql://ds029847.mongolab.com:29847/tpch",Optional.of(credentials));
        DBManagerType type =connectionInfo.getDBType();
        assertTrue(type.equals(DBManagerType.POSTGRES));
    }

    @Test
    public void testValidateCredentialsWithOutCredentials() throws Exception {
        credentials=new Credentials("","");
        connectionInfo= new ConnectionInfo("jdbc:postgresql://ds029847.mongolab.com:29847/tpch",Optional.of(credentials));
        assertTrue(!connectionInfo.getCredentials().isPresent());
    }

    @Test
    public void testValidateCredentialsWithCredentials() throws Exception {
        credentials=new Credentials("test","test");
        connectionInfo= new ConnectionInfo("jdbc:postgresql://ds029847.mongolab.com:29847/tpch",Optional.of(credentials));
        assertTrue(connectionInfo.getCredentials().isPresent());
    }

    @Test
    public void testValidateCredentialsWithCredentialsOnluUsuerName() throws Exception {
        credentials=new Credentials("test","");
        connectionInfo= new ConnectionInfo("jdbc:postgresql://ds029847.mongolab.com:29847/tpch",Optional.of(credentials));
        assertTrue(connectionInfo.getCredentials().isPresent());
    }
}