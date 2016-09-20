package com.peter.util.db;


import com.google.common.base.Optional;
import com.peter.util.data.File;
import com.peter.util.sys.Environment;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by Peter on 1/22/2016.
 */
public class OracleDBManagerImpl implements DBManager {

    public ConnectionInfo connectionInfo;
    public Connection activeConnection;
    public boolean querySuccessful;
    public DBManagerType dbManagerType;
    public Query lastQuery;

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

    public String getPath(String scriptFileName) {
        String dir;
        switch (scriptFileName) {
            default:
                dir = dbManagerType.scriptsPath;
                break;
            case "tmp.sql":
                dir = Environment.getInstance().targetPath;
                break;
        }
        dir += scriptFileName;
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

    public OracleDBManagerImpl(ConnectionInfo connectionInfo) {
        this.connectionInfo = connectionInfo;
        this.dbManagerType = connectionInfo.getDBType();
        lastQuery = new Query();
    }

    public OracleDBManagerImpl() {
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
        } catch (SQLRecoverableException e) {
            System.out.println("Connection with " + connectionInfo.getJdbcUrl() + " could not be established!!");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return activeConnection;
    }

    public void commit() {
        try {
            activeConnection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Query getLastQuery() {
        return lastQuery;
    }

    @Override
    public int countRowsTable(String table, Optional<String> where) {
        String whereStr = where.isPresent() ? where.get() : "";
        String query = "SELECT COUNT(*) FROM " + table + " " + whereStr;
        return ((BigDecimal) executeQuery(query).get(0).get("COUNT(*)")).intValue();
    }

    @Override
    public int countRowsTable(DBTable dbTable, Optional<String> where) {
        String table = dbTable.name;
        return countRowsTable(table, where);
    }

    public void closeConnection() {
        try {
            activeConnection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ArrayList<HashMap>> createDB(String dbName) {
        File file = new File(getPath(scripts.templateCreateDB.fileName));
        ArrayList pairsToReplace = new ArrayList();
        pairsToReplace.add(new String[]{scriptsParameters.dbName.name, dbName});
        pairsToReplace.add(new String[]{scriptsParameters.dbOwner.name, connectionInfo.getUserName()});
        file.replaceTextLists(getPath(scripts.tmpSQL.fileName), pairsToReplace);

        return executeSQLFile(getPath(scripts.tmpSQL.fileName));
    }

    public ArrayList<ArrayList<HashMap>> deleteDB(String dbName) {
        File file = new File(getPath(scripts.templateDeleteDB.fileName));
        ArrayList pairsToReplace = new ArrayList();
        pairsToReplace.add(new String[]{scriptsParameters.dbName.name, dbName});
        pairsToReplace.add(new String[]{scriptsParameters.dbOwner.name, connectionInfo.getUserName()});
        file.replaceTextLists(getPath(scripts.tmpSQL.fileName), pairsToReplace);
        return executeSQLFile(getPath(scripts.tmpSQL.fileName));
    }

    public void insertTable(String table, String columnNames, String values) {
        String query = "INSERT INTO " + table + " ( " + columnNames + " ) " + "VALUES" + " ( " + values + " ) ";
        executeQuery(query);
    }

    public void insertTable(String table, ArrayList<String> columnNamesList, ArrayList<String> valuesList) {
        String columnNames = "";
        for (String name : columnNamesList) columnNames += name + ",";
        columnNames = columnNames.substring(0, columnNames.length() - 1);
        String query = "INSERT INTO " + table + " ( " + columnNames + " ) " + "VALUES";
        for (String values : valuesList) {
            values = setBooleanValues(values);
            query += " ( " + values + " ),";
        }
        query = query.substring(0, query.length() - 1);
        executeQuery(query);
    }

    public void insertTable(DBTable table) {
        ArrayList<String> headers = table.headers.getAsList();
        ArrayList<String> values = new ArrayList<>();
        for (int i = 0; i < table.values.size(); i++) {
            DBRow t = table.values.get(i).clone();
            t.setValues();
            values.add(t.getAsStringList(table.getTypes()));
        }
        insertTable(table.name, headers, values);
    }

    public void updateTable(String table, String columnNames, String values, Optional<String> where) {
        values = setBooleanValues(values);
        String query = getSetForUpdateQuery(columnNames, values);
        query = "UPDATE " + table + " SET " + query.substring(0, query.length() - 1);
        if (where.isPresent()) query += " WHERE " + where.get();
        executeQuery(query);
    }

    private String getSetForUpdateQuery(String columnNames, String values) {
        String[] headerSplit = columnNames.split(",");
        String[] valuesSplit = values.split(",");
        String query = "";
        if (headerSplit.length > 1) {
            for (int i = 0; i < headerSplit.length; i++) {
                query += getSetForUpdateQuery(headerSplit[i], valuesSplit[i]);
            }
        } else {
            query += columnNames + " = " + values + ",";
        }
        return query;
    }

    private String setBooleanValues(String values) {
        String finalValues = "";
        if (values.toUpperCase().contains("TRUE") || values.toUpperCase().contains("FALSE")) {
            String[] valuesSplit = values.split(",");
            for (String value : valuesSplit) {
                switch (value.trim().toUpperCase()) {
                    case "TRUE":
                        finalValues += "1";
                        break;
                    case "FALSE":
                        finalValues += "0";
                        break;
                    default:
                        finalValues += value.trim();
                }
                finalValues += ",";
            }
            finalValues = finalValues.substring(0, finalValues.length() - 1);
        } else {
            finalValues = values;
        }

        return finalValues;
    }

    @Override
    public void updateTable(DBTable dbTable, Optional<String> where) {
        String table = dbTable.name;
        String columnNames = dbTable.headers.getAsStringList(DBRow.Grouper.NN);
        String values = dbTable.values.get(0).getAsStringList(dbTable.getTypes());
        updateTable(table, columnNames, values, where);
    }

    public List<HashMap> selectTable(String table, String columnNames, Optional<String> where) {
        String query = "SELECT " + columnNames + " FROM " + table;
        if (where.isPresent()) query += " WHERE " + where.get();
        return executeQuery(query);
    }

    @Override
    public List<HashMap> selectTable(DBTable dbTable, Optional<String> where) {
        String table = dbTable.name;
        String columnNames = dbTable.headers.getAsStringList(DBRow.Grouper.NN);
        return selectTable(table, columnNames, where);
    }

    public void createTable(String table, String columnNamesAndTypes, Optional<String> primaryKey) {
        String query = "CREATE TABLE " + table + " ( " + columnNamesAndTypes;
        if (primaryKey.isPresent()) query += ", CONSTRAINT pk PRIMARY KEY (" + primaryKey.get() + ")";
        query += " );";
        executeQuery(query);
    }

    public void deleteTable(String table) {
        String query = "DELETE " + table;
        executeQuery(query);
    }

    public void cleanTable(String table) {
        String query = "TRUNCATE TABLE " + table;
        executeQuery(query);
    }

    @Override
    public void cleanTable(String table, String options) {
        String query;
        switch (options.toLowerCase()) {
            case "cascade":
                query = "SELECT p.table_name \"Parent Table\", c.table_name \"Child Table\",\n" +
                        "       p.constraint_name \"Parent Constraint\", c.constraint_name \"Child Constraint\"\n" +
                        "FROM user_constraints p\n" +
                        "  JOIN user_constraints c ON(p.constraint_name=c.r_constraint_name)\n" +
                        "WHERE (p.constraint_type = 'P' OR p.constraint_type = 'U')\n" +
                        "      AND c.constraint_type = 'R'\n" +
                        "      AND p.table_name = UPPER('" + table + "')";
                ArrayList<HashMap> constraintsList = executeQuery(query);
                for (HashMap map : constraintsList) {
                    ArrayList<HashMap> result = executeQuery("TRUNCATE TABLE " + map.get("Child Table"));
                }
                break;
        }
        query = "DELETE " + table;
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
        lastQuery.setQuery(linesList, list);
        return list;
    }

    public ArrayList<ArrayList<HashMap>> executeSQLFile(String filePath, ArrayList<String[]> pairsToReplace) {
        connect();
        File file = new File(filePath);
        file.replaceTextLists(getPath(scripts.tmpSQL.fileName), pairsToReplace);
        return executeSQLFile(getPath(scripts.tmpSQL.fileName));
    }

    @Override
    public ArrayList<ArrayList<HashMap>> executeSQLFileWithLimits(String filePath, int fromIndex, int toIndex) {
        return null;
    }

    @Override
    public ArrayList<ArrayList<HashMap>> executeSQLFileWithLimits(String filePath, ArrayList<String[]> pairsToReplace, int fromIndex, int toIndex) {
        return null;
    }

    @Override
    public ArrayList<ArrayList<HashMap>> executePaginatedSQLFile(String filePath, int fromIndex, int pageSize, int page) {
        return null;
    }

    @Override
    public ArrayList<ArrayList<HashMap>> executePaginatedSQLFile(String filePath, ArrayList<String[]> pairsToReplace, int fromIndex, int pageSize, int page) {
        return null;
    }


    public ArrayList<HashMap> executeQuery(String query) {
        connect();
        querySuccessful = true;
        ArrayList list = new ArrayList();
        try (Statement statement = activeConnection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            list = resultSetToArrayList(resultSet);
        } catch (SQLException e) {
            if (!e.getMessage().equals("No results were returned by the query.")) {
                e.printStackTrace();
                querySuccessful = false;
            }
        }
        lastQuery.setQuery(query, list);
        return list;
    }

    @Override
    public ArrayList<HashMap> executeQueryWithLimits(String query, int fromIndex, int toIndex) {
        int limit = toIndex - fromIndex;
        String exp = " " + "LIMIT " + limit + " OFFSET " + fromIndex;
        return executeQuery(query + exp);
    }

    @Override
    public ArrayList<HashMap> executePaginatedQuery(String query, int fromIndex, int pageSize, int page) {
        int offset = page * pageSize;
        return executeQueryWithLimits(query, fromIndex + offset, fromIndex + offset + pageSize);
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
            if (!e.getMessage().contains("fetch out of sequence") && !e.getMessage().contains("no statement parsed")) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public boolean isQuerySuccessful() {
        return querySuccessful;
    }

}

