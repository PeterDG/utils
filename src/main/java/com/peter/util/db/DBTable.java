package com.peter.util.db;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Peter on 7/13/2016.
 */
public class DBTable {
    public String name;
    public DBRow headers;
    public ArrayList<DBRow> values = new ArrayList<>();
    public ArrayList<DBDataType> types;
    public static String TIMESTAMP_FORMAT="YYYY-MM-DD HH24:MI:SS.FF6";

    public DBTable(String name) {
        initializeDataTypes();
        this.name = name;
    }

    public DBTable(String name, DBRow headers) {
        this(name);
        this.headers = headers;
    }

    public DBTable(String name, DBRow headers, ArrayList<DBRow> values) {
        this(name, headers);
        this.values = values;
    }

    public DBTable(String name, ArrayList<DBDataType> types,DBRow headers) {
        this(name, headers);
        this.types = types;
    }

    public DBTable(String name, ArrayList<DBDataType> types, DBRow headers, ArrayList<DBRow> values) {
        this(name, headers,values);
        this.types = types;
    }

    public DBRow getHeaders() {
        return headers;
    }

    public ArrayList<DBDataType> getTypes() {
        return types;
    }

    public void setTypes(ArrayList<DBDataType> types) {
        this.types = types;
    }

    public void setHeaders(DBRow headers) {
        this.headers = headers;
    }

    public ArrayList<DBRow> getValues() {
        return values;
    }

    public void setValues(ArrayList<DBRow> values) {
        this.values = values;
    }

    public void addRow(DBRow row) {
        this.values.add(row);
    }

    public void cleanRows() {
        this.values.clear();
    }

    @Override
    public DBTable clone() {
        return new DBTable(name,cloneTypes(), headers.clone(), cloneRows());
    }

    public ArrayList<DBRow> cloneRows() {
        ArrayList<DBRow> rows = new ArrayList<>();
        for (DBRow row : values) {
            rows.add(row.clone());
        }
        return rows;
    }

    public ArrayList<DBDataType> cloneTypes() {
        ArrayList<DBDataType> typesList = new ArrayList<>();
        for (DBDataType type : types) {
            typesList.add(type);
        }
        return typesList;
    }

    public void initializeDataTypes() {
        types = new ArrayList<DBDataType>(Arrays.asList(
                DBDataType.TEXT,
                DBDataType.TEXT,
                DBDataType.TEXT,
                DBDataType.TEXT,
                DBDataType.TEXT,
                DBDataType.TEXT,
                DBDataType.TEXT,
                DBDataType.TEXT,
                DBDataType.TEXT,
                DBDataType.TEXT,
                DBDataType.TEXT,
                DBDataType.TEXT,
                DBDataType.TEXT,
                DBDataType.TEXT,
                DBDataType.TEXT,
                DBDataType.TEXT,
                DBDataType.TEXT,
                DBDataType.TEXT,
                DBDataType.TEXT,
                DBDataType.TEXT,
                DBDataType.TEXT,
                DBDataType.TEXT,
                DBDataType.TEXT,
                DBDataType.TEXT,
                DBDataType.TEXT,
                DBDataType.TEXT,
                DBDataType.TEXT,
                DBDataType.TEXT,
                DBDataType.TEXT,
                DBDataType.TEXT
        ));
    }

    public enum DBDataType {
        NUMBER(DBRow.Grouper.NN,"%s","%s"),
        TEXT(DBRow.Grouper.AP,"%s","%s"),
        TIMESTAMP(DBRow.Grouper.AP,"TO_TIMESTAMP(%s","%s, %s"+TIMESTAMP_FORMAT+"%s)");

        public DBRow.Grouper grouper;
        public String prefix;
        public String suffix;

        DBDataType(DBRow.Grouper grouper, String prefix, String suffix) {
            this.grouper = grouper;
            this.prefix= prefix.replace("%s",grouper.operator);
            this.suffix = suffix.replace("%s",grouper.operator);
        }
    }
}
