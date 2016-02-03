package com.peter.util.data;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by PEDRO GUTIERREZ on 10/12/2015.
 */
public class ListOfListsOfStrings  implements DataPackage {
    public ArrayList<ArrayList<String>> list;

    public ListOfListsOfStrings(ArrayList<ArrayList<String>> list) {
        this.list = list;
    }

    @Override
    public String get(int row, int column) {
        return list.get(row).get(column);
    }

    @Override
    public Integer getColumnsNumber() {
        return list.get(0).size();
    }

    @Override
    public Integer getRowsNumber() {
        return list.size();
    }

    @Override
    public ArrayList<ArrayList<String>> getSource() {
        return list;
    }

    @Override
    public String getOutputDirName() {
        String date = new SimpleDateFormat("dd-MM-yy_HH.mm.ss").format(new Date());
        return "pattern_"+date;
    }

}
