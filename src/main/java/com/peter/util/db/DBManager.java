package com.peter.util.db;


import com.google.common.base.Optional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Peter on 1/22/2016.
 */
public interface DBManager {

 Object getConnection();
 ArrayList<HashMap> executeQuery(String query);
 ArrayList<ArrayList<HashMap>> executeSQLFile(String filePath);
 ArrayList<ArrayList<HashMap>> createDB(String dbName);
 ArrayList<ArrayList<HashMap>> deleteDB(String dbName);
 boolean existDB(String dbName);
 void createTable(String table, String columnNamesAndTypes, Optional<String> primaryKey);
 void deleteTable(String dbName);
 void insertTable(String table, String columnNames, String values);
 void insertTable(String table, String columnNames, ArrayList<String> valuesList);
 void updateTable(String table, String columnNames, String values,  Optional<String> where);
 List<HashMap> selectTable(String table, String columnNames, Optional<String> where);
 void cleanTable(String dbName);
 boolean isQuerySuccessful();
 void closeConnection();
 void commit();
}
