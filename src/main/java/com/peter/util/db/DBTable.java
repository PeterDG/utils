package com.peter.util.db;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by Peter on 7/1/2015.
 */
public class DBTable {
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

    public ArrayList<String> getAsList() {
        this.columns = new ArrayList<String>(Arrays.asList(column1, column2, column3, column4, column5, column6, column7, column8, column9, column10, column11, column12, column13, column14, column15, column16, column17, column18, column19, column20));
        return columns;
    }

    public void setList(ArrayList<String> list){
        column1=list.get(0);
        column2=list.get(1);
        column3=list.get(2);
        column4=list.get(3);
        column5=list.get(4);
        column6=list.get(5);
        column7=list.get(6);
        column8=list.get(7);
        column9=list.get(8);
        column10=list.get(9);
        column11=list.get(10);
        column12=list.get(11);
        column13=list.get(12);
        column14=list.get(13);
        column15=list.get(14);
        column16=list.get(15);
        column17=list.get(16);
        column18=list.get(17);
        column19=list.get(18);
        column20=list.get(19);
    }

    public String getAsStringList(boolean values) {
        String s = "";
        if (values) s = "'";
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

    public void setValues(){
        ArrayList<String> list=getAsList();
        for(int i=0;i<list.size();i++){
            String data=list.get(i);
           if(data != null) if(data.contains("RND")) list.set(i,getRND(data));
        }
        setList(list);
    }

    public String getRND(String data){
        String [] splitData=data.replace("RND","").replace("(","").replace(")","").split(",");
        Double min =Double.valueOf(splitData[0]);
        Double max =Double.valueOf(splitData[1]);
        Random r = new Random();
        Double nRnd = r.nextDouble()*(max - min) + min;
        return Double.toString(nRnd);
    }

    public DBTable clone(){
        DBTable table = new DBTable();
        table.setList(this.getAsList());
        return table;
    }
}
