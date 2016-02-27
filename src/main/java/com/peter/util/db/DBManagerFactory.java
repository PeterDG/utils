package com.peter.util.db;

import com.google.common.base.Optional;

/**
 * @author acabra
 * @version 2015-06-18
 */
public class DBManagerFactory {
    public static DBManager buildDBManager(ConnectionInfo connInfo) {
        switch (connInfo.getDBType()) {
            case MONGO:
                return new MongoDBManagerImpl( connInfo);
            case POSTGRES:
                return new PostgresDBManagerImpl(connInfo);
            default:
                try {
                    throw new Exception("Unrecognized type of DBManager");
                } catch (Exception e) {
                    e.printStackTrace();
                }
             return null;
        }
    }
}

