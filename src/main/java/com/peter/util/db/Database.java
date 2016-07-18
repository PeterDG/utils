package com.peter.util.db;

import com.google.common.base.Optional;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Peter on 7/13/2016.
 */
public class Database {
    public DBManager db;
    public ConnectionInfo connectionInfo;
    public HashMap<String,DBTable> tablesMap = new HashMap();

    public Database(ConnectionInfo connectionInfo) {
        setConnectionInfo(connectionInfo);
    }

    public Database(ConnectionInfo connectionInfo, HashMap<String,DBTable> tablesMap) {
        setConnectionInfo(connectionInfo);
        this.tablesMap = tablesMap;
    }

    public Database(String jdbcURL, String userName,String password) {
        this.connectionInfo = new ConnectionInfo(jdbcURL, Optional.of(new Credentials(userName,password)));
        setConnectionInfo(connectionInfo);
    }

    public Database(String jdbcURL, String userName,String password, HashMap<String,DBTable> tablesMap) {
        this(jdbcURL,userName,password);
        this.tablesMap = tablesMap;
    }

    public DBManager getDb() {
        return db;
    }

    public void setDb(DBManager db) {
        this.db = db;
    }

    public ConnectionInfo getConnectionInfo() {
        return connectionInfo;
    }

    public void setConnectionInfo(ConnectionInfo connectionInfo) {
        this.db=DBManagerFactory.buildDBManager(connectionInfo);
        this.connectionInfo = connectionInfo;
    }

    public HashMap<String,DBTable> getTablesMap() {
        return tablesMap;
    }

    public void setTablesMap(HashMap<String,DBTable> tablesMap) {
        this.tablesMap = tablesMap;
    }

    public void addTable(DBTable dbTable){
        tablesMap.put(dbTable.name,dbTable);
    }

    public void connect(){
        db.connect();
    }

    public void createTable(String tableName, String columnNamesAndTypes, Optional<String> primaryKey) {
        db.createTable(tableName,columnNamesAndTypes,primaryKey);
    }

    public void insertTable(DBTable dbTable){
        db.insertTable(dbTable);
    }

    public void deleteTable(DBTable dbTable){
        db.deleteTable(dbTable.name);
    }

    public void cleanTable(DBTable dbTable){
       db.cleanTable(dbTable.name);
    }

    public void cleanTable(DBTable dbTable,String options){
        db.cleanTable(dbTable.name,options);
    }

    public int countRowsTable(DBTable dbTable,Optional<String> where){
        return db.countRowsTable(dbTable,where);
    }

    public List<HashMap> selectTable(DBTable dbTable, Optional<String> where){
        return db.selectTable(dbTable,where);
    }

    public void updateTable(DBTable dbTable, Optional<String> where){
        db.updateTable(dbTable,where);
    }

    public boolean isQuerySuccessful(){
        return db.isQuerySuccessful();
    }

}
