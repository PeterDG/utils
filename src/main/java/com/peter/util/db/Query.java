package com.peter.util.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Peter on 5/4/2016.
 */
public class Query {
    public List<String> query;
    public ArrayList<ArrayList<HashMap>> result;

    public List<String> getQuery() {
        return query;
    }

    public void setQuery(String query, ArrayList result) {
        ArrayList lines = new ArrayList(){};
        lines.add(query);
        this.query = lines;
        this.result = result;
        if(result.size()>0){
            if(result.get(0).getClass()!=ArrayList.class){
                ArrayList map = new ArrayList(){};
                map.add(result);
                this.result=map;
            }
        }
    }

    public void setQuery(List<String> query, ArrayList result) {
        this.query = query;
        this.result = result;
    }

}
