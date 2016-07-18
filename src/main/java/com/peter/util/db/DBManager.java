package com.peter.util.db;


import com.google.common.base.Optional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Peter on 1/22/2016.
 */
public interface DBManager {

 Object connect();
 ArrayList<HashMap> executeQuery(String query);
 ArrayList<HashMap> executeQueryWithLimits(String query, int from, int to);
  ArrayList<HashMap> executePaginatedQuery(String query, int fromIndex, int pageSize, int page);

 ArrayList<ArrayList<HashMap>> executeSQLFile(String filePath);
 ArrayList<ArrayList<HashMap>> executeSQLFile(String filePath, ArrayList<String[]> pairsToReplace);

 ArrayList<ArrayList<HashMap>> executeSQLFileWithLimits(String filePath, int fromIndex, int toIndex);
 ArrayList<ArrayList<HashMap>> executeSQLFileWithLimits(String filePath, ArrayList<String[]> pairsToReplace, int fromIndex, int toIndex);
 ArrayList<ArrayList<HashMap>> executePaginatedSQLFile(String filePath, int fromIndex, int pageSize, int page);
 ArrayList<ArrayList<HashMap>> executePaginatedSQLFile(String filePath, ArrayList<String[]> pairsToReplace, int fromIndex, int pageSize, int page);

 ArrayList<ArrayList<HashMap>> createDB(String dbName);
 ArrayList<ArrayList<HashMap>> deleteDB(String dbName);
 boolean existDB(String dbName);
 void createTable(String table, String columnNamesAndTypes, Optional<String> primaryKey);
 void deleteTable(String dbName);
 void insertTable(DBTable dbTable);
 void insertTable(String table, String columnNames, String values);
 void insertTable(String table,  ArrayList<String> columnNamesList, ArrayList<String> valuesList);
 void updateTable(String table, String columnNames, String values,  Optional<String> where);
 void updateTable(DBTable dbTable,  Optional<String> where);
 List<HashMap> selectTable(String table, String columnNames, Optional<String> where);
 List<HashMap> selectTable(DBTable table, Optional<String> where);
 void cleanTable(String dbName);
 void cleanTable(String dbName,String options);
 boolean isQuerySuccessful();
 void closeConnection();
 void commit();
 Query getLastQuery();
 int countRowsTable(String table,Optional<String> where);
 int countRowsTable(DBTable dbTable,Optional<String> where);
}
