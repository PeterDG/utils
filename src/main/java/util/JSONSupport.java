package util;

import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.path.json.config.JsonPathConfig;
import com.jayway.restassured.response.Response;
//import org.apache.commons.io.FileUtils;
import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import util.jsontocsv.parser.JSONFlattener;
import util.jsontocsv.writer.CSVWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by PEDRO GUTIERREZ on 27/08/2015.
 */
public class JSONSupport {
    public JsonPath jsonPath;
    public JSONObject jsonObj;
    public JsonPathConfig config = new JsonPathConfig("UTF-8").numberReturnType(JsonPathConfig.NumberReturnType.BIG_DECIMAL);
    public FileSupport fileSupport;
    public List<Map<String, String>>  flatJson=new ArrayList<Map<String, String>> ();
    public JSONSupport(String json) {
        create(json);
    }

    public JSONSupport(Response response) {
        String json = response.getBody().asString();
        create(json);
    }
    public JSONSupport(){

    }

    public JSONSupport(String path, String fileName) {
        fileSupport = new FileSupport(path+"/"+fileName);
        jsonPath = new JsonPath(fileSupport.file)
        .using(config);
        jsonPath.config = new JsonPathConfig("UTF-8").numberReturnType(JsonPathConfig.NumberReturnType.BIG_DECIMAL);
    }

    private void create(String json) {
        jsonPath = new JsonPath(json).using(config);
    }

    public Double getDouble(String objectPath) {
        return jsonPath.getDouble(objectPath);
    }

    public String getString(String objectPath) {
        return jsonPath.getString(objectPath);
    }

    public String toString() {
        return jsonPath.prettyPrint();
    }

    public String update() {
        put("body.riskScore", "5678");
        jsonPath.using(config).param("body.riskScore", "4526");
        return "";
    }

//    private static final Configuration configuration = Configuration.builder()
//            .jsonProvider(new JacksonJsonNodeJsonProvider())
//            .mappingProvider(new JacksonMappingProvider())
//            .build();
    public void put(String path,String value){
        jsonObj=new JSONObject(toString());
        List<String> nodes = Arrays.asList(path.split("[,\\.]"));
        JSONObject tmp = null;
        for (int i=0;i<nodes.size()-1;i++){
            tmp=jsonObj.getJSONObject(nodes.get(i));
        }
        String finalNode=nodes.get(nodes.size() - 1);
        tmp.put(finalNode, value);
        jsonPath=new JsonPath(jsonObj.toString());
    }

    public JSONObject getJsonObj(){
        try {
            jsonObj = new JSONObject(jsonPath.prettyPrint());
        } catch (JSONException e){
            jsonObj=new JSONObject();
            jsonObj.put("",new JSONArray(jsonPath.prettyPrint()));
        }
        return  jsonObj;
    }

    public void jsonToCsv(String csvPathFile,String keyForSuffix,String suffix){
        flatJson = JSONFlattener.parseJson(fileSupport.file, "UTF-8", keyForSuffix, suffix);
        CSVWriter.writeToFile(CSVWriter.getCSV(flatJson, ";"), csvPathFile);
    }

    public void jsonToCsv(String csvPathFile){
        flatJson = JSONFlattener.parseJson(fileSupport.file, "UTF-8","","");
        CSVWriter.writeToFile(CSVWriter.getCSV(flatJson, ";"), csvPathFile);
    }

    public void appendJsonMap(String jsonPathFile,String keyForSuffix,String suffix){
        List<Map<String, String>>  flatJsonTemp = JSONFlattener.parseJson(new File(jsonPathFile), "UTF-8",keyForSuffix,suffix);
        flatJson.addAll(flatJsonTemp);
    }

    public void exportCsv(String csvPathFile){
        CSVWriter.writeToFile(CSVWriter.getCSV(flatJson, ";"), csvPathFile);
    }
}
