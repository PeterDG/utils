package com.peter.util.data;

/**
 * Created by PEDRO GUTIERREZ on 10/12/2015.
 */
public interface DataPackage {

    String get(int row, int column);

    Integer getColumnsNumber();

    Integer getRowsNumber();

    Object getSource();

    String getOutputDirName();
}
