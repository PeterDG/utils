package com.peter.util.db;

import java.util.ArrayList;

/**
 * Created by Peter on 7/13/2016.
 */
public class DBTable {
    public String name;
    public DBRow headers;
    public ArrayList<DBRow> values=new ArrayList<>();

    public DBTable(String name) {
        this.name = name;
    }

    public DBTable(String name,DBRow headers) {
        this.headers = headers;
        this.name = name;
    }

    public DBTable(String name, DBRow headers, ArrayList<DBRow> values) {
        this.name = name;
        this.headers = headers;
        this.values = values;
    }

    public DBRow getHeaders() {
        return headers;
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

    public void addRow(DBRow row){
        this.values.add(row);
    }

    @Override
    public DBTable clone(){
        return new DBTable(name,headers.clone(),cloneRows());
    }

    public ArrayList<DBRow> cloneRows(){
        ArrayList<DBRow> rows=new ArrayList<>();
        for(DBRow row:values){
            rows.add(row.clone());
        }
        return rows;
    }
}
