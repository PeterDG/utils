package com.peter.util.db;

//import com.detectta.console.BundleUtil;
//import com.detectta.console.CommonSteps;
//import gherkin.deps.com.google.gson.JsonElement;
//import gherkin.deps.com.google.gson.JsonParser;


/**
 * Created by acastaneda on 10/1/14.
 */
public class DBSupport {

//    //    Properties properties
//    String host;
//    String port;
//    String dbName;
//    String userName;
//    String password;
//    String url;
//    String strDbType;
//    String strLanguage;
//    String postgreRootPath;
//    boolean resetServer;
//    private Connection connection;
//    public CommonSteps.operationMode mode;
//    public CommonSteps.Language language;
//    private CommonSteps.databaseType dbType;
//    public File file = new File();
//    public CMD cmdSupport = new CMD();
//
//    public enum scripts {
//        getOperationMode("getOperationMode.sql"),
//        executePocProcess("poc/executePocProcess.sql"),
//        getStatePocProcess("poc/getStatePocProcess.sql"),
//        getOperationLanguage("getOperationLanguage.sql"),
//        detectta("../detectta.sql"),
//        detecttaBank("detectta_bank.sql"),
//        dashboard("../dashboard.sql"),
//        partitioning("../partitioning.sql"),
//        templateCreateDTA("template_createDB.sql"),
//        templateDisconnectDB("template_disconnectDB.sql"),
//        templateDeleteDB("template_deleteDB.sql"),
//        tmpSql("tmp.sql"),
//        tmpBat("tmp.bat");
//
//        public final String fileName;
//
//        scripts(final String scriptFileName) {
//            this.fileName = scriptFileName;
//        }
//    }
//
//    public enum scriptsParameters {
//        dbName("p_databaseName");
//
//        public final String name;
//
//        scriptsParameters(final String name) {
//            this.name = name;
//
//        }
//    }
//
//    public void loadParameters() {
//        try {
//            this.host = PropertiesLoaderUtils.loadProperties(new ClassPathResource("/configuration.properties")).getProperty("database.host");
//            this.port = PropertiesLoaderUtils.loadProperties(new ClassPathResource("/configuration.properties")).getProperty("database.port");
//            this.dbName = PropertiesLoaderUtils.loadProperties(new ClassPathResource("/configuration.properties")).getProperty("database.name");
//            this.userName = PropertiesLoaderUtils.loadProperties(new ClassPathResource("/configuration.properties")).getProperty("database.username");
//            this.password = PropertiesLoaderUtils.loadProperties(new ClassPathResource("/configuration.properties")).getProperty("database.password");
//            this.strDbType = PropertiesLoaderUtils.loadProperties(new ClassPathResource("/configuration.properties")).getProperty("database.manager");
//            this.strLanguage = PropertiesLoaderUtils.loadProperties(new ClassPathResource("/configuration.properties")).getProperty("database.language");
//            this.postgreRootPath = PropertiesLoaderUtils.loadProperties(new ClassPathResource("/configuration.properties")).getProperty("postgre.rootPath");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        setDBType(strDbType);
//    }
//
//    public void setMode(CommonSteps.operationMode mode) {
//        this.mode = mode;
//        switch (mode) {
//            case SYNC:
//                try {
//                    this.host = PropertiesLoaderUtils.loadProperties(new ClassPathResource("/configuration.properties")).getProperty("database.host");
//                    this.port = PropertiesLoaderUtils.loadProperties(new ClassPathResource("/configuration.properties")).getProperty("database.port");
//                    this.dbName = PropertiesLoaderUtils.loadProperties(new ClassPathResource("/configuration.properties")).getProperty("database.name");
//                    this.userName = PropertiesLoaderUtils.loadProperties(new ClassPathResource("/configuration.properties")).getProperty("database.username");
//                    this.password = PropertiesLoaderUtils.loadProperties(new ClassPathResource("/configuration.properties")).getProperty("database.password");
//                    this.strDbType = PropertiesLoaderUtils.loadProperties(new ClassPathResource("/configuration.properties")).getProperty("database.manager");
//                    this.strLanguage = PropertiesLoaderUtils.loadProperties(new ClassPathResource("/configuration.properties")).getProperty("database.language");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                break;
//            case ASYNC:
//                try {
//                    this.host = PropertiesLoaderUtils.loadProperties(new ClassPathResource("/configuration.properties")).getProperty("database.host.tr");
//                    this.port = PropertiesLoaderUtils.loadProperties(new ClassPathResource("/configuration.properties")).getProperty("database.port.tr");
//                    this.dbName = PropertiesLoaderUtils.loadProperties(new ClassPathResource("/configuration.properties")).getProperty("database.name.tr");
//                    this.userName = PropertiesLoaderUtils.loadProperties(new ClassPathResource("/configuration.properties")).getProperty("database.username.tr");
//                    this.password = PropertiesLoaderUtils.loadProperties(new ClassPathResource("/configuration.properties")).getProperty("database.password.tr");
//                    this.strDbType = PropertiesLoaderUtils.loadProperties(new ClassPathResource("/configuration.properties")).getProperty("database.manager.tr");
//                    this.strLanguage = PropertiesLoaderUtils.loadProperties(new ClassPathResource("/configuration.properties")).getProperty("database.language.tr");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                break;
//        }
//        resetServer=false;
//        setDBType(strDbType);
//        setLanguage(strLanguage);
//        setDBLanguage(language, false);
//        setDBMode(false);
//        resetServer(resetServer);
//    }
//
//    public void resetServer(boolean reset) {
//        if (reset) {
//            DetectTAServer detectTAServer = new DetectTAServer();
//            detectTAServer.reload();
//        }
//    }
//
//    public void executePOCProcess(){
//        List<String> executePoc=file.getListLinesOfFile(dbType.scriptTestPath + scripts.executePocProcess.fileName);
//        List<String> queryPoc=file.getListLinesOfFile(dbType.scriptTestPath + scripts.getStatePocProcess.fileName);
//        executeQuery(executePoc.get(0), url, userName, password);
//        while ( executeQuery(queryPoc.get(0), url, userName, password).get(0).equals("0")){
//            CommonSteps.sleep(1000);
//        }
//    }
//
//    public void setDBDetectTA(boolean resetServer) {
//        String path = null;
//        deleteDB(dbName);
//        createDB(dbName);
//        executeScript(dbType.scriptPath + scripts.detectta.fileName);
//        executeScript(dbType.scriptPath + scripts.detecttaBank.fileName);
//        executeScript(dbType.scriptPath + scripts.dashboard.fileName);
//        executeScript(dbType.scriptPath + scripts.partitioning.fileName);
//        this.resetServer = true;
//        resetServer(resetServer);
//    }
//
//    public void deleteDB(String dbName) {
//        if (existDB(dbName)) {
//            file.replace(dbType.scriptTestPath + scripts.templateDeleteDB.fileName, dbType.scriptTestPath + scripts.tmpSql.fileName, scriptsParameters.dbName.name, dbName);
//            executeQueryFromFile(dbType.scriptTestPath + scripts.tmpSql.fileName, "jdbc:postgresql://localhost:5432/postgres", userName, password);
//        }
//    }
//
//    public void createDB(String dbName) {
//        file.replace(dbType.scriptTestPath + scripts.templateCreateDTA.fileName, dbType.scriptTestPath + scripts.tmpSql.fileName, scriptsParameters.dbName.name, dbName);
//        executeQueryFromFile(dbType.scriptTestPath + scripts.tmpSql.fileName, "jdbc:postgresql://localhost:5432/postgres", userName, password);
//    }
//
//    public void insetData(String table, String columnNames, String values) {
//        String query ="INSERT INTO " + table + " ( " + columnNames + " ) " + "VALUES" + " ( " + values + " ) ";
//        executeQuery(query, url, userName, password);
//    }
//
//    public void updateData(String table, String columnNames, String values, String where) {
//        String query ="UPDATE " + table + " SET " + columnNames + " = " + values + " WHERE "+where;
//        executeQuery(query, url, userName, password);
//    }
//
//    public List<String> selectData(String table, String columnNames, String where) {
//        String query ="SELECT " +columnNames+" FROM " + table + " WHERE "+where;
//        return executeQuery(query, url, userName, password);
//    }
//
//    public List<String> selectData(String table, String columnNames) {
//        String query ="SELECT " +columnNames+" FROM " + table;
//        return executeQuery(query, url, userName, password);
//    }
//
//    public void cleanTable(String table) {
//        String query ="TRUNCATE TABLE " + table;
//        executeQuery(query, url, userName, password);
//    }
//    public boolean existDB(String dbName) {
//        boolean exist = false;
//        try {
//            connection = DriverManager.getConnection(url, userName, password);
//            if (connection != null) exist = true;
//            connection.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return exist;
//    }
//
//    public CommonSteps.operationMode getDBMode() {
//        CommonSteps.operationMode mode = CommonSteps.operationMode.SYNC;
//        String path = dbType.scriptTestPath + scripts.getOperationMode.fileName;
//        List<String> outList = executeQueryFromFile(CommonSteps.projectRootPath + path);
//        if (Boolean.valueOf(outList.get(0))) mode = CommonSteps.operationMode.ASYNC;
//        return mode;
//    }
//
//    public void setDBMode(boolean resetServer) {
//        if (this.mode != getDBMode()) {
//            String path = dbType.scriptPath + mode.setUpQueryFileName;
//            executeQueryFromFile(CommonSteps.projectRootPath + path);
//            this.resetServer = true;
//            resetServer(resetServer);
//        }
//    }
//
//    public CommonSteps.Language getDBLanguage() {
//        CommonSteps.Language language = CommonSteps.Language.ENGLISH;
//        String path = dbType.scriptTestPath + scripts.getOperationLanguage.fileName;
//        List<String> outList = executeQueryFromFile(CommonSteps.projectRootPath + path);
//        for (CommonSteps.Language lg : CommonSteps.Language.values()) {
//            if (lg.name.equals(outList.get(0))) language = lg;
//        }
//        return language;
//    }
//
//    public void setDBLanguage(CommonSteps.Language language, boolean resetServer) {
//        if (!language.equals( getDBLanguage())) {
//            String path = dbType.scriptPath + language.setUpQueryFileName;
//            executeQueryFromFile(CommonSteps.projectRootPath + path);
//            this.resetServer = true;
//            resetServer(resetServer);
//        }
//    }
//
//    public void setLanguage(String languageStr) {
//        for (CommonSteps.Language lg : CommonSteps.Language.values()) {
//            if (lg.name.equals(languageStr)) language = lg;
//        }
//    }
//
//    public void setDBType(String databaseType) {
//        switch (databaseType) {
//            case "postgreSQL":
//            case "PostgreSQL":
//            case "postgre":
//            case "Postgre":
//                setDbType(CommonSteps.databaseType.PostgesSQL);
//                break;
//            case "microsoftSQL":
//            case "MicrosoftSQL":
//            case "microsoft":
//            case "Microsoft":
//                setDbType(CommonSteps.databaseType.MicrosoftSQL);
//                break;
//        }
//    }
//
//    public void setDbType(CommonSteps.databaseType dbType) {
//        this.dbType = dbType;
//        switch (dbType) {
//            case PostgesSQL:
//                this.url = "jdbc:postgresql://" + host + ":" + port + "/" + dbName;
//                try {
//                    DriverManager.registerDriver(new org.postgresql.Driver());
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//                break;
//            case MicrosoftSQL:
//                this.url = "jdbc:jtds:sqlserver://" + host + ":" + port + "/" + dbName;
//                try {
//                    DriverManager.registerDriver(new net.sourceforge.jtds.jdbc.Driver());
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//                break;
//        }
//    }
//
//    public DBSupport(CommonSteps.operationMode mode) {
//        setMode(mode);
//    }
//
//    public DBSupport() {
//        loadParameters();
//    }
//
//    public DBSupport(CommonSteps.operationMode mode, CommonSteps.databaseType dbType) {
//        setMode(mode);
//        setDbType(dbType);
//    }
//
//    public String getDbUrl() {
//        return url;
//    }
//
//    public String getUsername() {
//        return userName;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public Connection getConnection() throws SQLException {
//        Properties properties = new Properties();
//        properties.put("user", userName);
//        properties.put("password", password);
//
//        try {
//            Class.forName("org.postgresql.Driver");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//            return null;
//        }
//
//        return DriverManager.getConnection(url, properties);
//    }
//
//    public String getSAAName(int saaId) throws SQLException {
//        String SELECT_SAA_NAME = "select saa_name from suspicious_activity_analyzer order by saa_id";
//        connection = DriverManager.getConnection(url, userName, password);
//        PreparedStatement statement = connection.prepareStatement(SELECT_SAA_NAME);
////        statement.setInt(1, saaId);
//        String saaName = "";
//        ResultSet resultSet = statement.executeQuery();
//
//        for (int i = 0; i < saaId; i++) {
//            resultSet.next();
//            saaName = resultSet.getString("saa_name");
//        }
//
//        statement.close();
//        connection.close();
//
//        return saaName;
//    }
//
//    public Boolean isRunningSynchronously() throws SQLException {
//        String SELECT_SAA_NAME = "SELECT ca_value from conf_advanced WHERE ca_identifier = 'allow.synchronous.evaluation'";
//        connection = DriverManager.getConnection(url, userName, password);
//        PreparedStatement statement = connection.prepareStatement(SELECT_SAA_NAME);
//        String result = "";
//        ResultSet resultSet = statement.executeQuery();
//        while (resultSet.next()) {
//            result = resultSet.getString("ca_value");
//        }
//
//        statement.close();
//        connection.close();
//
//        return Boolean.valueOf(result);
//    }
//
//    public void cleanLearning() throws SQLException {
//
//        String Query = "UPDATE detect_common_configurations SET dcc_conf_content = '0' WHERE dcc_identifier IN ('le.process.finish.date','le.process.id','le.process.month','le.process.year')";
//        String QueryA = "TRUNCATE TABLE process_detail, indicators, indicators_evaluation, indicators_last, batch_step_execution_context, batch_step_execution, batch_job_execution_context, batch_job_execution CASCADE";
//        if (dbType == CommonSteps.databaseType.MicrosoftSQL) QueryA = sqlTranslator(QueryA);
//        connection = DriverManager.getConnection(url, userName, password);
//        PreparedStatement statement = connection.prepareStatement(QueryA);
//        try {
//            statement.executeQuery();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
////        statement = connection.prepareStatement(QueryA);
//        statement = connection.prepareStatement(Query);
//        try {
//            statement.executeQuery();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        statement.close();
//        connection.close();
//
//    }
//
//    public boolean isLearningClean() {
//        String queryCount = "SELECT dcc_conf_content FROM detect_common_configurations WHERE dcc_identifier IN ('le.process.month')";
//
//        ResultSet resultSet;
//        String result = null;
//        try {
//            connection = DriverManager.getConnection(url, userName, password);
//            PreparedStatement statement = connection.prepareStatement(queryCount);
//            resultSet = statement.executeQuery();
//            while (resultSet.next()) {
//                result = resultSet.getString(1);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return !(Integer.parseInt(result) > 0);
//    }
//
//    public JSONObject readJSONIndicator(String sharedKey) {
//        JSONObject jsonObj = null;
//        String jsonField = "i_indicators";
//        String query = "SELECT " + jsonField + " FROM indicators WHERE i_shared_key='" + sharedKey + "'";
//        jsonObj = getJsonObject(query, jsonField).get(0);
//        return jsonObj;
//    }
//
//
//    public List<JSONObject> readJSONRiskQualification(String confirmationCode) {
//        List<JSONObject> jsonObjList = new ArrayList<JSONObject>();
//        String jsonField = "rq_risk_detail";
//        String query = "SELECT " + jsonField + " FROM risk_qualification WHERE rq_confirmation_code='" + confirmationCode + "'";
//        jsonObjList = getJsonObject(query, jsonField);
//        return jsonObjList;
//    }
//
//    public List<JSONObject> getJsonObject(String query, String jsonField) {
//        List<JSONObject> jsonObjList = new ArrayList<JSONObject>();
//        String result = null;
//        ResultSet resultSet = null;
//        try {
//            connection = DriverManager.getConnection(url, userName, password);
//            Statement statement = connection.createStatement();
//            resultSet = statement.executeQuery(query);
//            while (resultSet.next()) {
//                result = resultSet.getString(jsonField);
//                jsonObjList.add(new JSONObject(result));
//            }
//            statement.close();
//
//            connection.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return jsonObjList;
//    }
//
//    public JsonElement readIndicator(String sharedKey) {
//        JsonElement jsonObj = null;
//        String jsonField = "i_indicators";
//        String query = "SELECT " + jsonField + " FROM indicators WHERE i_shared_key='" + sharedKey + "'";
//        jsonObj = getJson(query, jsonField).get(0);
//        return jsonObj;
//    }
//
//
//    public List<JsonElement> readRiskQualificationList(String confirmationCode) {
//        List<JsonElement> jsonObjList = new ArrayList<JsonElement>();
//        String jsonField = "rq_risk_detail";
//        String query = "SELECT " + jsonField + " FROM risk_qualification WHERE rq_confirmation_code='" + confirmationCode + "'";
//        jsonObjList = getJson(query, jsonField);
//        return jsonObjList;
//    }
//
//    public JsonElement readRiskQualification(String confirmationCode, String indicatorIdentifier) {
//        String jsonField = "rq_risk_detail";
//        String query = "SELECT " + jsonField + " FROM risk_qualification WHERE rq_confirmation_code='" + confirmationCode + "'" + " AND " + jsonField + " LIKE '%" + indicatorIdentifier + "%'";
//        return getJson(query, jsonField).get(0);
//    }
//
//    public List<JsonElement> getJson(String query, String jsonField) {
//        List<JsonElement> jsonObjList = new ArrayList<JsonElement>();
//        String result = null;
//        ResultSet resultSet = null;
//        try {
//            connection = DriverManager.getConnection(url, userName, password);
//            Statement statement = connection.createStatement();
//            resultSet = statement.executeQuery(query);
//            while (resultSet.next()) {
//                result = resultSet.getString(jsonField);
//                JsonParser parser = new JsonParser();
//                jsonObjList.add(parser.parse(result));
//            }
//            statement.close();
//
//            connection.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (JsonException e) {
//            e.printStackTrace();
//        }
//        return jsonObjList;
//    }
//
//    public String sqlTranslator(String query) {
//        query = query.replaceAll("CASCADE", "");
//        if (query.contains("TRUNCATE") && query.contains(",")) {
//            query = query.replaceAll(",", "; DELETE ");
//            query = query.replaceAll("TRUNCATE TABLE", "DELETE ");
//        }
//        return query;
//    }
//
//    public List<String> executeQueryFromFile(String filePath) {
////        File file = new File();
////        List<String> linesList = file.getListLinesOfFile(filePath);
////        List<String> outList = new ArrayList<>();
////        ResultSet resultSet = null;
////        for (String line : linesList) {
////            try {
////                connection = DriverManager.getConnection(url, userName, password);
////                Statement statement = connection.createStatement();
////                resultSet = statement.executeQuery(line);
////                while (resultSet.next()) {
////                    outList.add(resultSet.getString(1));
////                }
////                statement.close();
////                connection.close();
////            } catch (SQLException e) {
////                e.printStackTrace();
////            }
////        }
//        return executeQueryFromFile(filePath,url,userName,password);
//    }
//
//    public List<String> executeQueryFromFile(String filePath, String url,String userName, String password) {
//        File file = new File();
//        List<String> linesList = file.getListLinesOfFile(filePath);
//        List<String> outList = new ArrayList<>();
//        ResultSet resultSet = null;
//        for (String line : linesList) {
//            try {
//                connection = DriverManager.getConnection(url, userName, password);
//                Statement statement = connection.createStatement();
//                resultSet = statement.executeQuery(line);
//                while (resultSet.next()) {
//                    outList.add(resultSet.getString(1));
//                }
//                statement.close();
//                connection.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return outList;
//    }
//
//
//    public void executeScript(String filePath) {
//        String cmd = "\"" + postgreRootPath + "psql\"" + " postgresql://" + userName + ":" + password + "@localhost:5432/" + dbName + " < \"" + filePath + "\"" + " \n exit ";
//        file.writeLine(CommonSteps.projectRootPath + dbType.scriptTestPath, scripts.tmpBat.fileName, cmd, true);
//        cmdSupport.executeCMD("cmd /c start " + CommonSteps.projectRootPath + dbType.scriptTestPath + scripts.tmpBat.fileName);
////        try {
////            connection = DriverManager.getConnection(url, userName, password);
////            ScriptRunner runner = new ScriptRunner(connection, false, false);
////            InputStreamReader reader = new InputStreamReader(new FileInputStream(filePath));
////            runner.runScript(reader);
////            reader.close();
////            connection.close();
////        } catch (SQLException e) {
////            e.printStackTrace();
////        } catch (FileNotFoundException e) {
////            e.printStackTrace();
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
//    }
//
//
//    public List<String> executeQuery(String query, String url, String userName, String password) {
//        List<String> out = new ArrayList<String>();
//        try {
//            connection = DriverManager.getConnection(url, userName, password);
//            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery(query);
//            while(resultSet.next()) {
//                out.add(resultSet.getString(1));
//            }
//            statement.close();
//            connection.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return out;
//    }


}
