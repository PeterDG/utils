package com.peter.util.data.util;


import com.peter.util.time.Time;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Peter on 5/13/2015.
 */
public class ArraysList {

    public static ArrayList<String> date2String(ArrayList<Date> dateList, String formatter){
        ArrayList<String> StringList = new ArrayList<String>();
       for(Date date:dateList){
           StringList.add(Time.date2String(date, formatter));
       }
        return StringList;
    }

    public static ArrayList<String> integer2String(ArrayList<Integer> integerList){
        ArrayList<String> StringList = new ArrayList<String>();
        for(Integer integer:integerList){
            StringList.add(String.valueOf(integer));
        }
        return StringList;
    }

    public static <T> ArrayList<ArrayList<T>> transpose(ArrayList<ArrayList<T>> table) {
        ArrayList<ArrayList<T>> ret = new ArrayList<>();
        final int N = table.get(0).size();
        for (int i = 0; i < N; i++) {
            ArrayList<T> col = new ArrayList<T>();
            for (List<T> row : table) {
                col.add(row.get(i));
            }
            ret.add(col);
        }
        return ret;
    }
}
