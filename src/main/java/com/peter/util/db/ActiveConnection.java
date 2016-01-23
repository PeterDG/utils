package com.peter.util.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author acabra
 * @version 10/04/2015
 */
public class ActiveConnection implements AutoCloseable {
    private ResultSet resultSet;
    private Connection connection;
    private Statement stmt;
    private int count;

    public ActiveConnection(ResultSet resultSet, Connection connection, Statement stmt, int count){
        this.connection = connection;
        this.stmt = stmt;
        this.resultSet= resultSet;
        this.count = count;
    }

    public ResultSet getResultSet() {
        return resultSet;
    }

    public int getCount() {
        return count;
    }

    @Override
    public void close() throws IOException {
        try {
            if (resultSet != null && !resultSet.isClosed()) {
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (stmt != null && !stmt.isClosed()) {
                stmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
