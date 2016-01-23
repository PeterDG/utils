package com.peter.util.db;

import com.google.common.base.Optional;
import com.mongodb.MongoClient;
import org.junit.Test;

import static com.mongodb.client.model.Filters.regex;
import static org.junit.Assert.*;

/**
 * Created by Peter on 1/22/2016.
 */
public class DBManagerFactoryTest {

    @Test
    public void testBuildDBManagerMongo() throws Exception {
        ConnectionInfo connectionInfo=new ConnectionInfo("jdbc:mongo://192.168.243.197:27017", Optional.absent());
        DBManager db =DBManagerFactory.buildDBManager(DBManagerType.MONGO,Optional.of(connectionInfo));
        MongoClient mongoClient=(MongoClient)db.getConnection();
        assertTrue(mongoClient.getConnectPoint().equals("192.168.243.197:27017"));
    }

    @Test
    public void testCloseConnectionMongo() throws Exception {
        ConnectionInfo connectionInfo=new ConnectionInfo("jdbc:mongo://192.168.243.197:27017", Optional.absent());
        DBManager db =DBManagerFactory.buildDBManager(DBManagerType.MONGO,Optional.of(connectionInfo));
        MongoClient mongoClient=(MongoClient)db.getConnection();
        mongoClient.close();
        DBManager dbA = DBManagerFactory.buildDBManager(DBManagerType.MONGO,Optional.of(connectionInfo));
        MongoClient mongoClientA=(MongoClient)db.getConnection();
        assertTrue(mongoClient!=mongoClientA);
    }

    @Test
    public void testNotCloseConnectionMongo() throws Exception {
        ConnectionInfo connectionInfo=new ConnectionInfo("jdbc:mongo://192.168.243.197:27017", Optional.absent());
        DBManager db =DBManagerFactory.buildDBManager(DBManagerType.MONGO,Optional.of(connectionInfo));
        MongoClient mongoClient=(MongoClient)db.getConnection();
        DBManager dbA = DBManagerFactory.buildDBManager(DBManagerType.MONGO,Optional.of(connectionInfo));
        MongoClient mongoClientA=(MongoClient)db.getConnection();
        assertTrue(mongoClient==mongoClientA);
    }
}