package com.peter.util.db;

import com.google.common.base.Optional;
import com.mongodb.MongoClient;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Created by Peter on 1/23/2016.
 */
public class MongoDBManagerImplTest {
    public DBManager connectionWithDB;
    public DBManager connection;
    public ConnectionInfo connectionInfo;
    public ConnectionInfo connectionInfoWithDB;
    @Before
    public void before(){
        connectionInfo=new ConnectionInfo("jdbc:mongo://192.168.243.197:27017", Optional.absent());
        connection =DBManagerFactory.buildDBManager(DBManagerType.MONGO,Optional.of(connectionInfo));
        connectionInfoWithDB=new ConnectionInfo("jdbc:mongo://192.168.243.197:27017/historicContext", Optional.absent());
        connectionWithDB =DBManagerFactory.buildDBManager(DBManagerType.MONGO,Optional.of(connectionInfoWithDB));
    }
    @Test
    public void testGetConnection() throws Exception {
        MongoClient mongoClient=(MongoClient) connection.getConnection();
        assertTrue(mongoClient.getConnectPoint().equals("192.168.243.197:27017"));
    }

    @Test
    public void testCloseConnection() throws Exception {
        MongoClient mongoClient=(MongoClient) connection.getConnection();
        mongoClient.close();
        DBManager dbA = DBManagerFactory.buildDBManager(DBManagerType.MONGO,Optional.of(connectionInfo));
        MongoClient mongoClientA=(MongoClient) connection.getConnection();
        assertTrue(mongoClient!=mongoClientA);
    }

    @Test
    public void testNotCloseConnection() throws Exception {
        MongoClient mongoClient=(MongoClient) connection.getConnection();
        DBManager dbA = DBManagerFactory.buildDBManager(DBManagerType.MONGO,Optional.of(connectionInfo));
        MongoClient mongoClientA=(MongoClient) connection.getConnection();
        assertTrue(mongoClient==mongoClientA);
    }

    @Test
    public void testGetCommandList() throws Exception {
//         connection.executeQuery("SELECT * FROM textTable");
//         connection.executeQuery("select * from textTable");
    }

    @Test
    public void testExecuteQuerySelectSimple() throws Exception {
        MongoClient mongoClient=(MongoClient) connectionWithDB.getConnection();
        ArrayList<HashMap> result = connectionWithDB.executeQuery("SELECT * FROM historicalPopulationMetrics");
        assertTrue(result.size()==2);
    }

    @Test
    public void testExecuteQuerySelectComplex() throws Exception {
        MongoClient mongoClient=(MongoClient) connectionWithDB.getConnection();
        ArrayList<HashMap> result = connectionWithDB.executeQuery("SELECT date,metrics FROM historicalPopulationMetrics");
        assertTrue(!result.get(0).containsKey("_id"));
        assertTrue(result.get(0).containsKey("date"));
        assertTrue(result.get(0).containsKey("metrics"));
    }

    @Test
    public void testExecuteQuerySelectWithWhereOneStament() throws Exception {
        MongoClient mongoClient=(MongoClient) connectionWithDB.getConnection();
//        ArrayList<HashMap> result = connectionWithDB.executeQuery("SELECT _id,date FROM historicalUsernMetrics WHERE (_id='dbetancourt' OR _id='dcastaneda') AND (date='2015-06-23T18:28:30.859Z' OR date='2015-06-23T03:52:07.796Z') ");
        ArrayList<HashMap> result = connectionWithDB.executeQuery("SELECT _id,date FROM historicalUserMetrics WHERE _id='dbetancourt'");
        assertTrue(!result.get(0).containsKey("metrics"));
        assertTrue(result.get(0).containsKey("_id"));
        assertTrue(result.get(0).containsKey("date"));
        assertTrue(result.get(0).get("_id").equals("dbetancourt"));
    }

    @Test
    public void testExecuteQuerySelectWithWhereWithRegExpPositive() throws Exception {
        MongoClient mongoClient=(MongoClient) connectionWithDB.getConnection();
        ArrayList<HashMap> result =  connectionWithDB.executeQuery("SELECT * FROM historicalUserMetrics WHERE _id ~'dbet.*ourt'");
        assertTrue(result.get(0).get("_id").equals("dbetancourt"));
    }

    @Test
    public void testExecuteQuerySelectWithWhereWithRegExpNegative() throws Exception {
        MongoClient mongoClient=(MongoClient) connectionWithDB.getConnection();
        ArrayList<HashMap> result =  connectionWithDB.executeQuery("SELECT * FROM historicalUserMetrics WHERE _id ~'dbet.*ou5rt'");
        assertTrue(result.size()==0);
    }

    @Test
    public void testExecuteQuerySelectWithWhereWithGT() throws Exception {
        MongoClient mongoClient=(MongoClient) connectionWithDB.getConnection();
        ArrayList<HashMap> result =  connectionWithDB.executeQuery("SELECT * FROM historicalUserMetrics WHERE date >'2015-06-23T18:28:30.859Z'");
        assertTrue(result.size()==2);
    }

    @Test
    public void testExecuteQuerySelectWithWhereWithGTE() throws Exception {
        MongoClient mongoClient=(MongoClient) connectionWithDB.getConnection();
        ArrayList<HashMap> result =  connectionWithDB.executeQuery("SELECT * FROM historicalUserMetrics WHERE date >='2015-06-23T18:28:30.859Z'");
        assertTrue(result.size()==3);
    }

    @Test
    public void testExecuteQuerySelectWithWhereWithLTE() throws Exception {
        MongoClient mongoClient=(MongoClient) connectionWithDB.getConnection();
        ArrayList<HashMap> result =  connectionWithDB.executeQuery("SELECT * FROM historicalUserMetrics WHERE date <='2015-06-23T18:28:30.859Z'");
        assertTrue(result.size()==3);
    }

    @Test
    public void testExecuteQuerySelectWithWhereWithLT() throws Exception {
        MongoClient mongoClient=(MongoClient) connectionWithDB.getConnection();
        ArrayList<HashMap> result =  connectionWithDB.executeQuery("SELECT * FROM historicalUserMetrics WHERE date <'2015-06-23T18:28:30.859Z'");
        assertTrue(result.size()==2);
    }
}