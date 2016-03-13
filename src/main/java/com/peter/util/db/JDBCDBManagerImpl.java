package com.peter.util.db;


import com.google.common.base.Optional;
import com.peter.util.data.File;
import sys.Environment;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


/**
 * Created by Peter on 1/22/2016.
 */
public class JDBCDBManagerImpl implements DBManager {

    public ConnectionInfo connectionInfo;
    public Connection activeConnection;
    public boolean querySuccessful;
    public DBManagerType dbManagerType;

    public enum scripts {
        templateCreateDB("template_createDB.sql"),
        templateDisconnectDB("template_disconnectDB.sql"),
        templateDeleteDB("template_deleteDB.sql"),
        tmpSQL("tmp.sql");

        public final String fileName;

        scripts(final String scriptFileName) {
            this.fileName = scriptFileName;
        }
    }

    public String getPath(String scriptFileName){
        String dir;
        switch(scriptFileName){
            default:
                dir = dbManagerType.scriptsPath;
                break;
            case "tmp.sql":
                dir = Environment.getInstance().targetPath;
                break;
        }
        dir +=scriptFileName;
        return dir;
    }

    public enum scriptsParameters {
        dbName("p_dbName"),
        dbOwner("p_dbOwner");

        public final String name;

        scriptsParameters(final String name) {
            this.name = name;
        }
    }

    public JDBCDBManagerImpl(ConnectionInfo connectionInfo) {
        this.connectionInfo = connectionInfo;
        this.dbManagerType=connectionInfo.getDBType();
    }

    public JDBCDBManagerImpl() {
    }

    public Connection connect() {
        return this.getConnection(connectionInfo.getDBName());
    }

    public Connection getConnection(String dbName) {
        try {
            if (activeConnection == null || activeConnection.isClosed()) {
                Class.forName(dbManagerType.driverClassName);
                activeConnection = DriverManager.getConnection(connectionInfo.getJDBCUrlWithDBName() + dbName, connectionInfo.getUserName(), connectionInfo.getPassword());
            }
        } catch (SQLRecoverableException e){
            System.out.println("Connection with "+connectionInfo.getJdbcUrl() + " could not be established!!");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return activeConnection;
    }

    public void commit(){
        try {
            activeConnection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection(){
        try {
            activeConnection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ArrayList<HashMap>> createDB(String dbName) {
        File file = new File(getPath(scripts.templateCreateDB.fileName));
        ArrayList pairsToReplace = new ArrayList() ;
        pairsToReplace.add(new String []{scriptsParameters.dbName.name,dbName});
        pairsToReplace.add(new String []{scriptsParameters.dbOwner.name,connectionInfo.getUserName()});
        file.replaceTextLists(getPath(scripts.tmpSQL.fileName), pairsToReplace);

        return executeSQLFile(getPath(scripts.tmpSQL.fileName));
    }

    public ArrayList<ArrayList<HashMap>> deleteDB(String dbName) {
        File file = new File(getPath(scripts.templateDeleteDB.fileName));
        ArrayList pairsToReplace = new ArrayList() ;
        pairsToReplace.add(new String []{scriptsParameters.dbName.name,dbName});
        pairsToReplace.add(new String []{scriptsParameters.dbOwner.name,connectionInfo.getUserName()});
        file.replaceTextLists(getPath(scripts.tmpSQL.fileName),pairsToReplace);
        return executeSQLFile(getPath(scripts.tmpSQL.fileName));
    }

    public void insertTable(String table, String columnNames, String values) {
        String query = "INSERT INTO " + table + " ( " + columnNames + " ) " + "VALUES" + " ( " + values + " ) ";
        executeQuery(query);
    }

    public void insertTable(String table, ArrayList<String> columnNamesList, ArrayList<String> valuesList) {
        String columnNames = "";
        for(String name:columnNamesList) columnNames+=name+",";
        columnNames=columnNames.substring(0,columnNames.length()-1);
        String query = "INSERT INTO " + table + " ( " + columnNames + " ) " + "VALUES";
        for(String values:valuesList){
            query+= " ( " + values + " ),";
        }
        query=query.substring(0, query.length() - 1);
        executeQuery(query);
    }

    public void insertTable(String table,ArrayList<DBTable> columns){
        ArrayList<String> columnNames = new ArrayList<>(Arrays.asList(columns.get(0).getAsStringList(false).split(",")));
        ArrayList<String> columnValues = new ArrayList<>();
        for (int i = 1; i < columns.size(); i++) {
            DBTable t = columns.get(i).clone();
            t.setValues();
            columnValues.add(t.getAsStringList(true));
        }
        insertTable(table, columnNames, columnValues);
    }

    public void updateTable(String table, String columnNames, String values, Optional<String> where) {
        String query = "UPDATE " + table + " SET " + columnNames + " = " + values;
        if (where.isPresent()) query += " WHERE " + where.get();
        executeQuery(query);
    }

    public List<HashMap> selectTable(String table, String columnNames, Optional<String> where) {
        String query = "SELECT " + columnNames + " FROM " + table;
        if (where.isPresent()) query += " WHERE " + where.get();
        return executeQuery(query);
    }

    public void createTable(String table, String columnNamesAndTypes, Optional<String> primaryKey) {
        String query = "CREATE TABLE " + table + " ( " + columnNamesAndTypes;
        if (primaryKey.isPresent()) query += ", CONSTRAINT pk PRIMARY KEY (" + primaryKey.get() + ")";
        query += " );";
        executeQuery(query);
    }

    public void deleteTable(String table) {
        String query = "DROP TABLE " + table;
        executeQuery(query);
    }

    public void cleanTable(String table) {
        String query = "TRUNCATE TABLE " + table;
        executeQuery(query);
    }

    public boolean existDB(String dbName) {
        boolean exist = false;
        try {
            Connection connection = getConnection(dbName);
            if (connection != null) {
                exist = true;
                connection.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exist;
    }

    public ArrayList<ArrayList<HashMap>> executeSQLFile(String filePath) {
        connect();
        File file = new File(filePath);
        ArrayList<ArrayList<HashMap>> list = new ArrayList<>();
        List<String> linesList = file.getListLinesOfFile();
        ResultSet resultSet = null;
        for (String line : linesList) {
            list.add(executeQuery(line));
        }
        return list;
    }

    public ArrayList<ArrayList<HashMap>> executeSQLFile(String filePath, ArrayList<String[]> pairsToReplace) {
        connect();
        File file = new File(filePath);
        file.replaceTextLists(getPath(scripts.tmpSQL.fileName),pairsToReplace);
        return executeSQLFile(getPath(scripts.tmpSQL.fileName));
    }


    public ArrayList<HashMap> executeQuery(String query) {
        connect();
        querySuccessful=true;
        ArrayList list = new ArrayList();
        try {
            Statement statement = activeConnection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            list = resultSetToArrayList(resultSet);
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            if (!e.getMessage().equals( "No results were returned by the query.")){
                e.printStackTrace();
                querySuccessful=false;
            }

        }
        return list;
    }

    public ArrayList<HashMap> resultSetToArrayList(ResultSet rs) {
        ArrayList list = new ArrayList();
        try {
            ResultSetMetaData md = rs.getMetaData();
            int columns = md.getColumnCount();
            while (rs.next()) {
                HashMap row = new HashMap(columns);
                for (int i = 1; i <= columns; ++i) {
                    row.put(md.getColumnName(i), rs.getObject(i));
                }
                list.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean isQuerySuccessful() {
        return querySuccessful;
    }

}

