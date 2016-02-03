package com.peter.util.data;

import com.peter.util.data.util.ArraysList;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by PEDRO GUTIERREZ on 10/12/2015.
 */
public class CSVFile implements DataPackage {

    public String token;
    public File file;

    public CSVFile(String path) {
        this.token = ",";
        this.file = new File(path);
    }

    public String get(int row, int column){
        List<String> listLinesOfFile = file.getListLinesOfFile();
        return listLinesOfFile.get(row)
                .toString()
                .split(token)[column];
    }

    public int countLines() {
        List<String> listLinesOfFile = file.getListLinesOfFile();
        return listLinesOfFile.size();
    }

    public void export(ArrayList<ArrayList<String>> lines, boolean rowsList) {
        file.clean();
        if(!rowsList) lines= ArraysList.transpose(lines);
        for (ArrayList<String> lns : lines) {
            String line = "";
            for (String str : lns) {
                line = line + str + ",";
            }
            file.writeLine(line, false);
        }
    }

    @Override
    public Integer getColumnsNumber() {
        List<String> listLinesOfFile = file.getListLinesOfFile();
        return listLinesOfFile.get(0).toString().split(token).length;
    }

    @Override
    public Integer getRowsNumber() {
        List<String> listLinesOfFile = file.getListLinesOfFile();
        return listLinesOfFile.size();
    }

    @Override
    public CSVFile getSource() {
        return this;
    }

    @Override
    public String getOutputDirName() {
        String date = new SimpleDateFormat("dd-MM-yy_HH.mm.ss").format(new Date());
        return file.file.getName() + "_" + date;
    }
}
