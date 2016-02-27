package com.peter.util.db;

import com.google.common.base.Optional;

/**
 * @author acabra
 * @version 2015-06-18
 */
public class DBManagerFactory {
    public static DBManager buildDBManager(DBManagerType type, Optional<? extends Object> connInfo) {
        switch (type) {
            case MONGO:
                return connInfo.isPresent() ? new MongoDBManagerImpl(((ConnectionInfo) connInfo.get())) : new MongoDBManagerImpl();
            case POSTGRES:
                return connInfo.isPresent() ? new PostgresDBManagerImpl((ConnectionInfo) connInfo.get()) : new PostgresDBManagerImpl();
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
