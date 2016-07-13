package com.peter.util.db;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by Peter on 7/1/2015.
 */
public class DBRow {
    public String column1;
    public String column2;
    public String column3;
    public String column4;
    public String column5;
    public String column6;
    public String column7;
    public String column8;
    public String column9;
    public String column10;
    public String column11;
    public String column12;
    public String column13;
    public String column14;
    public String column15;
    public String column16;
    public String column17;
    public String column18;
    public String column19;
    public String column20;
    public ArrayList<String> columns;

    public DBRow() {
    }

    public DBRow(ArrayList<String> columns) {
        this.columns = columns;
        setList(columns);
    }

    public ArrayList<String> getAsList() {
        this.columns = new ArrayList<String>();
        if (column1 != null) columns.add(column1);
        if (column2 != null) columns.add(column2);
        if (column3 != null) columns.add(column3);
        if (column4 != null) columns.add(column4);
        if (column5 != null) columns.add(column5);
        if (column6 != null) columns.add(column6);
        if (column7 != null) columns.add(column7);
        if (column8 != null) columns.add(column8);
        if (column9 != null) columns.add(column9);
        if (column10 != null) columns.add(column10);
        if (column11 != null) columns.add(column11);
        if (column12 != null) columns.add(column12);
        if (column13 != null) columns.add(column13);
        if (column14 != null) columns.add(column14);
        if (column15 != null) columns.add(column15);
        if (column16 != null) columns.add(column16);
        if (column17 != null) columns.add(column17);
        if (column18 != null) columns.add(column18);
        if (column19 != null) columns.add(column19);
        if (column20 != null) columns.add(column20);

        return columns;
    }

    public void setList(ArrayList<String> list) {
        if (list.size() > 0) column1 = list.get(0);
        if (list.size() > 1) column2 = list.get(1);
        if (list.size() > 2) column3 = list.get(2);
        if (list.size() > 3) column4 = list.get(3);
        if (list.size() > 4) column5 = list.get(4);
        if (list.size() > 5) column6 = list.get(5);
        if (list.size() > 6) column7 = list.get(6);
        if (list.size() > 7) column8 = list.get(7);
        if (list.size() > 8) column9 = list.get(8);
        if (list.size() > 9) column10 = list.get(9);
        if (list.size() > 10) column11 = list.get(10);
        if (list.size() > 11) column12 = list.get(11);
        if (list.size() > 12) column13 = list.get(12);
        if (list.size() > 13) column14 = list.get(13);
        if (list.size() > 14) column15 = list.get(14);
        if (list.size() > 15) column16 = list.get(15);
        if (list.size() > 16) column17 = list.get(16);
        if (list.size() > 17) column18 = list.get(17);
        if (list.size() > 18) column19 = list.get(18);
        if (list.size() > 19) column20 = list.get(19);
    }

    public String getAsStringList(Grouper grouper) {
        String s = grouper.operator;
        return (column1 != null ? s + column1 + s : "") +
                (column2 != null ? " ," + s + column2 + s : "") +
                (column3 != null ? " ," + s + column3 + s : "") +
                (column4 != null ? " ," + s + column4 + s : "") +
                (column5 != null ? " ," + s + column5 + s : "") +
                (column6 != null ? " ," + s + column6 + s : "") +
                (column7 != null ? " ," + s + column7 + s : "") +
                (column8 != null ? " ," + s + column8 + s : "") +
                (column9 != null ? " ," + s + column9 + s : "") +
                (column10 != null ? " ," + s + column10 + s : "") +
                (column11 != null ? " ," + s + column11 + s : "") +
                (column12 != null ? " ," + s + column12 + s : "") +
                (column13 != null ? " ," + s + column13 + s : "") +
                (column14 != null ? " ," + s + column14 + s : "") +
                (column15 != null ? " ," + s + column15 + s : "") +
                (column16 != null ? " ," + s + column16 + s : "") +
                (column17 != null ? " ," + s + column17 + s : "") +
                (column18 != null ? " ," + s + column18 + s : "") +
                (column19 != null ? " ," + s + column19 + s : "") +
                (column20 != null ? " ," + s + column20 + s : "");
    }

    public String getAsStringList() {
        return getAsStringList(Grouper.NN);
    }

    public void setValues() {
        ArrayList<String> list = getAsList();
        for (int i = 0; i < list.size(); i++) {
            String data = list.get(i);
            if (data != null) if (data.contains("RND")) list.set(i, getRND(data));
        }
        setList(list);
    }

    public String getRND(String data) {
        String[] splitData = data.replace("RND", "").replace("(", "").replace(")", "").split(",");
        Double min = Double.valueOf(splitData[0]);
        Double max = Double.valueOf(splitData[1]);
        Random r = new Random();
        Double nRnd = r.nextDouble() * (max - min) + min;
        return Double.toString(nRnd);
    }

    public DBRow clone() {
        DBRow table = new DBRow();
        table.setList(this.getAsList());
        return table;
    }

    public enum Grouper {
        NN(""),
        AP("'"),
        SS("$$");

        public String operator;

        Grouper(String operator) {
            this.operator = operator;
        }
    }
}
