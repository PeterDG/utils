package com.peter.util.db;

import com.google.common.base.*;
import com.google.common.base.Optional;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.apache.log4j.Logger;


import java.util.*;


/**
 * @author acabra
 * @version 10/04/2015
 */
public class MongoDBManagerImpl implements DBManager {

    private final Logger logger = Logger.getLogger(MongoDBManagerImpl.class);

    public ConnectionInfo connectionInfo;
    public MongoClient activeConnection;

    public MongoDBManagerImpl() {

    }

    public MongoDBManagerImpl(ConnectionInfo connectionInfo) {
        this.connectionInfo = connectionInfo;
    }

    public Object getConnection() {
        try {
            activeConnection.getConnectPoint();
        } catch (Exception e) {
            createConnection();
        }
        return activeConnection;
    }

    @Override
    public ArrayList<HashMap> executeQuery(String query) {
        return null;
    }

    @Override
    public ArrayList<ArrayList<HashMap>> executeSQLFile(String filePath) {
        return null;
    }

    @Override
    public ArrayList<ArrayList<HashMap>> createDB(String dbName) {
        return null;
    }

    @Override
    public ArrayList<ArrayList<HashMap>> deleteDB(String dbName) {
        return null;
    }

    @Override
    public boolean existDB(String dbName0) {
        return false;
    }

    @Override
    public void createTable(String table, String columnNamesAndTypes, Optional<String> primaryKey) {

    }

    @Override
    public void deleteTable(String dbName) {

    }

    @Override
    public void insetTable(String table, String columnNames, String values) {

    }

    @Override
    public void updateTable(String table, String columnNames, String values, Optional<String> where) {

    }

    @Override
    public List<HashMap> selectTable(String table, String columnNames, Optional<String> where) {
        return null;
    }

    @Override
    public void cleanTable(String dbName) {

    }

    @Override
    public boolean isQuerySuccessful() {
        return false;
    }

    public void createConnection() {
        String db_mongo_server = connectionInfo.getServer();
        Integer db_mongo_port = connectionInfo.getPort();
        String db_mongo_database = connectionInfo.getDBName();
        if (connectionInfo.getCredentials().isPresent()) {
            String db_mongo_user = connectionInfo.getUserName();
            char[] db_mongo_pwds = connectionInfo.getPassword().toCharArray();

            List<MongoCredential> credentialList = new ArrayList<>();
            credentialList.add(MongoCredential.createMongoCRCredential(db_mongo_user, db_mongo_database, db_mongo_pwds));

            ArrayList<ServerAddress> seeds = new ArrayList<>();
            ServerAddress serverAddress = null;
            serverAddress = new ServerAddress(db_mongo_server, db_mongo_port);
            seeds.add(serverAddress);
            activeConnection = new MongoClient(seeds, credentialList);
        } else {
            activeConnection = new MongoClient(db_mongo_server, db_mongo_port);
        }
    }

//    public MongoDBManagerImpl(int db_mongo_port, String db_mongo_server, String db_mongo_database, boolean authenticateMongoClient) {
//        this.db_mongo_port = db_mongo_port;
//        this.db_mongo_server = db_mongo_server;
//        this.db_mongo_database = db_mongo_database;
//        this.isMongoClientAuthenticated = authenticateMongoClient;
//        this.userHistoricalCollection = Parameters.dbMongoCollectionMetricsUser;
//        this.populationHistoricalCollection = Parameters.dbMongoCollectionMetricsPopulation;
//        this.contextCollection = Parameters.dbMongoCollectionContext;
//        this.locationCityCollection = Parameters.dbMongoCollectionCity;
//        this.db_mongo_location = Parameters.dbMongoLocation;
//
//    }
//
//
//
//    private MongoClient getMongoClientInstance() {
//        if (isMongoClientAuthenticated) {
//            String db_mongo_user = Parameters.dbMongoUser;
//            char[] db_mongo_pwds = Parameters.dbMongoPass.toCharArray();
//
//            List<MongoCredential> credentialList = new ArrayList<>();
//            credentialList.add(MongoCredential.createCredential(db_mongo_user, db_mongo_database, db_mongo_pwds));
//
//            ArrayList<ServerAddress> seeds = new ArrayList<>();
//            ServerAddress serverAddress = new ServerAddress(db_mongo_server, db_mongo_port);
//            seeds.add(serverAddress);
//            return new MongoClient(seeds, credentialList);
//        } else {
//            return new MongoClient(db_mongo_server, db_mongo_port);
//        }
//    }
//
//    @Override
//    public Location getLocation(String[] locationStr){
//        Location location = new Location(locationStr);
//        ArrayList ranges = new ArrayList<Document>();
//        Bson filter=null;
//        Iterator<Document> iterator;
//        for(int i=0;i<locationStr.length;i++){
//            locationStr[i]=locationStr[i].toUpperCase();
//        }
//        try ( MongoClient mongoClient = getMongoClientInstance()) {
//            MongoCollection<Document> collection = mongoClient.getDatabase(db_mongo_location).getCollection(locationCityCollection);
//           if(locationStr.length==3) filter = and(or(eq("country", locationStr[0]), eq("country-name", locationStr[0])), eq("region", locationStr[1]),eq("city", locationStr[2]));
//           if(locationStr.length==2) filter = and(or(eq("country", locationStr[0]),eq("country-name", locationStr[0])), or(eq("region", locationStr[1]), eq("city", locationStr[1])));
//           if(locationStr.length==1) filter = and(or(eq("country", locationStr[0]), eq("country-name", locationStr[0])));
//            iterator = collection.find(filter).sort(new Document("country", -1)).iterator();
//            while (iterator.hasNext()) ranges.add(iterator.next());
//            mongoClient.close();
//        }
//
//        location.setIpRangesList(ranges);
//
//        return location;
//    }
//
//    @Override
//    public LocationResponse getLocationInfoFromIpNumber(long ipNumber, String ipAddress) throws TechnicalException {
//        String initial_column_name = "start_num";
//        String ending_column_name = "end_num";
//        try ( MongoClient mongoClient = getMongoClientInstance()){
//            //Bson filter = and(lte(initial_column_name, param), gte(ending_column_name, param));//start_num >= ipNumber //end_num =< ipNumber
//            Bson filter = lte(initial_column_name, ipNumber);
//            MongoCollection<Document> collection = mongoClient.getDatabase(db_mongo_location).getCollection(locationCityCollection);
//            Iterator<Document> iterator = collection.find(filter).sort(new Document(initial_column_name, -1)).iterator();
//            if (iterator.hasNext()) {
//                Document locationRecord = iterator.next();
//                long initial = Long.parseLong(locationRecord.get(initial_column_name) + "");
//                long ending = Long.parseLong(locationRecord.get(ending_column_name)+"");
//                if (initial<= ipNumber && ipNumber<=ending)
//                    return LocationResponseMapper.getLocationResponse(ipAddress, locationRecord);
//            }
//            throw new TechnicalException("Error: IpAddress information not found ip: ");
//        }
//    }
//
////
////    @Override
////    public LocationResponse getLocationInfoFromIpNumber(long ipNumber, String ipAddress) throws TechnicalException {
////        String initial_column_name = "start_num";
////        String ending_column_name = "end_num";
////        try ( MongoClient mongoClient = getMongoClientInstance()){
////            //Bson filter = and(lte(initial_column_name, param), gte(ending_column_name, param));//start_num >= ipNumber //end_num =< ipNumber
////            Bson filter = lte(initial_column_name, ipNumber);
////            MongoCollection<Document> collection = mongoClient.getDatabase(db_mongo_database).getCollection(locationCollection);
////            Iterator<Document> iterator = collection.find(filter).sort(new Document(initial_column_name, -1)).iterator();
////            if (iterator.hasNext()) {
////                Document locationRecord = iterator.next();
////                long initial = Long.parseLong(locationRecord.get(initial_column_name) + "");
////                long ending = Long.parseLong(locationRecord.get(ending_column_name)+"");
////                if (initial<= ipNumber && ipNumber<=ending)
////                    return LocationResponseMapper.getLocationResponse(ipAddress, locationRecord);
////            }
////            throw new TechnicalException("Error: IpAddress information not found ip: ");
////        }
////    }
////
////    @Override
////    public int insertContext(final ContextObject context) throws TechnicalException {
////        try ( MongoClient mongoClient = getMongoClientInstance()){
////            MongoCollection<Document> collection = mongoClient.getDatabase(db_mongo_database).getCollection(contextCollection);
////            Document documentWithID = ContextObjectMapper.toDocument(context).append("_id", new ObjectId());
////            collection.insertOne(documentWithID);
////            return documentWithID.get("_id").toString().hashCode();
////        } catch (Exception e){
////            throw new TechnicalException("Error inserting documents: " + e.getMessage());
////        }
////    }
////
//    @Override
//    public List<ContextObject> getContextsBasedOnDateAndUsername(final LocalDateTime initialDate,
//                                                                 final LocalDateTime endingDate,
//                                                                 final String usernameRegex) {
//        try ( MongoClient mongoClient = getMongoClientInstance()) {
//            MongoCollection<Document> collection = mongoClient.getDatabase(db_mongo_database).getCollection(contextCollection);
//            String fieldDateName = "serverInfo.date";
//            String fieldUsername = "serverInfo.username";
//            Bson filter = and(regex(fieldUsername, usernameRegex),
//                    gte(fieldDateName, DateHelper.getDateFromLDT(initialDate)),
//                    lte(fieldDateName, DateHelper.getDateFromLDT(endingDate)));
//            FindIterable<Document> documentsList = collection.find(filter)
//                    .sort(new BasicDBObject(fieldDateName, 1));
//            return null;//ContextObjectMapper.toContextObject(documentsList);
//        }
//    }
//
//    @Override
//    public long removeAllContextsBasedOnDateAndUsername(final LocalDateTime initialDate,
//                                                                 final LocalDateTime endingDate,
//                                                                 final String usernameRegex) {
//        try ( MongoClient mongoClient = getMongoClientInstance()) {
//            MongoCollection<Document> collection = mongoClient.getDatabase(db_mongo_database).getCollection(contextCollection);
//            String fieldDateName = "serverInfo.date";
//            String fieldUsername = "serverInfo.username";
//            Bson filter = and(regex(fieldUsername, usernameRegex),
//                    gte(fieldDateName, DateHelper.getDateFromLDT(initialDate)),
//                    lte(fieldDateName, DateHelper.getDateFromLDT(endingDate)));
//            DeleteResult result = collection.deleteMany(filter);
//            return result.getDeletedCount();
//        }
//    }
//
//    @Override
//    public long removeAllContextsBasedOnUsername(final String usernameRegex) {
//        try ( MongoClient mongoClient = getMongoClientInstance()) {
//            MongoCollection<Document> collection = mongoClient.getDatabase(db_mongo_database).getCollection(contextCollection);
//            String fieldUsername = "serverInfo.username";
//            Bson filter = regex(fieldUsername, usernameRegex);
//            DeleteResult result = collection.deleteMany(filter);
//            return result.getDeletedCount();
//        }
//    }
//
//    @Override
//    public long removeHistoricalUserMetrics(String usernameRegex) {
//        try ( MongoClient mongoClient = getMongoClientInstance()){
//            MongoCollection<Document> collection = mongoClient.getDatabase(db_mongo_database).getCollection(userHistoricalCollection);
//            String fieldUsername = "_id";
//            Bson filter = regex(fieldUsername, usernameRegex);
//            DeleteResult result = collection.deleteMany(filter);
//            return result.getDeletedCount();
//        }
//    }

//
//    @Override
//    public List<String> getUserNamesPopulationList(final LocalDateTime initialDate,
//                                                   final LocalDateTime endingDate) {
//        try ( MongoClient mongoClient = getMongoClientInstance()){
//            MongoCollection<Document> collection = mongoClient.getDatabase(db_mongo_database).getCollection(contextCollection);
//            List<String> userNames = new ArrayList<>();
//            String fieldDateName = "serverInfo.date";
//            Bson filter = and(gte(fieldDateName, DateHelper.getDateFromLDT(initialDate)),
//                    lte(fieldDateName, DateHelper.getDateFromLDT(endingDate)));
//            Iterator<Document> iterator = collection.find(filter).sort(eq(fieldDateName, 1)).iterator();
//            while (iterator.hasNext()) {
//                Document next = iterator.next();
//                Document serverInfo = (Document) next.get("serverInfo");
//                String username = (String) serverInfo.get("username");
//                if (username.length() > 0 && !userNames.contains(username) && !username.equals("undefined")) {
//                    userNames.add(username);
//                }
//            }
//            return userNames;
//        }
//    }
//
//    @Override
//    public void insertHistoricalPopulationMetrics(
//            final PopulationMetricsDTO populationMetricsDTO) throws TechnicalException {
//        Document mainDocument = new MetricsObjectDocumentMapper()
//                .toPopulationMetricsDocument(populationMetricsDTO.getMetrics(),
//                        populationMetricsDTO.getTitle(),
//                        populationMetricsDTO.getDate());
//        try ( MongoClient mongoClient = getMongoClientInstance()){
//            if(populationHistoricalCollection.isEmpty()) throw new TechnicalException("Collection Name not present");
//            MongoCollection<Document> collection = mongoClient.getDatabase(db_mongo_database).getCollection(populationHistoricalCollection);
//            Bson filter = eq("name", mainDocument.get("name") + "");
//            if(mainDocument.containsKey("name") && collection.find(filter).iterator().hasNext()) {
//                collection.findOneAndReplace(filter, mainDocument);
//            } else {
//                collection.insertOne(mainDocument);
//            }
//            String afterInsertTime = DateHelper.getCurrentTimeAsString();
//            logger.info("[" + afterInsertTime + "] Time after insertion on database");
//        }
//    }
//
//    @Override
//    public void insertHistoricalUserMetrics(UserMetricsDTO userMetricsDTO) throws TechnicalException {
//        Document mainDocument = new MetricsObjectDocumentMapper().toUserMetricsDocument(
//                userMetricsDTO.getMetrics(), userMetricsDTO.getDate(), Optional.of(userMetricsDTO.getUsername()));
//        try ( MongoClient mongoClient = getMongoClientInstance()){
//            if(userHistoricalCollection.isEmpty()) throw new TechnicalException("Collection Name not present");
//            if(!mainDocument.containsKey("_id")) throw new TechnicalException("Document for user does not contain id");
//            MongoCollection<Document> collection = mongoClient.getDatabase(db_mongo_database).getCollection(userHistoricalCollection);
//            Bson filter = eq("_id", mainDocument.get("_id") + "");
//            if(collection.find(filter).iterator().hasNext())
//                collection.findOneAndReplace(filter, mainDocument);
//            else
//                collection.insertOne(mainDocument);
//            String afterInsertTime = DateHelper.getCurrentTimeAsString();
//            logger.info("[" + afterInsertTime + "] Time after insertion on database");
//        }
//    }
//
//    @Override
//    public Optional<HistoricalMetricsObjectResponse> getLatestAvailablePopulationMetrics() {
//        try ( MongoClient mongoClient = getMongoClientInstance()){
//            if (!this.populationHistoricalCollection.isEmpty()) {
//                MongoCollection<Document> collection = mongoClient
//                        .getDatabase(db_mongo_database)
//                        .getCollection(this.populationHistoricalCollection);
//                Iterator<Document> documentsIterator = collection.find().sort(new BasicDBObject("date", -1)).iterator();
//                if(documentsIterator.hasNext())
//                    return Optional.of(new MetricsObjectDocumentMapper()
//                            .toHistoricalMetricsObjectResponse(documentsIterator.next()));
//            }
//            return Optional.absent();
//        }
//    }
//
//    @Override
//    public RiskScoringConfigurationDTO getRiskScoringConfiguration() {
//        if (!config.isPresent()) {
//            config = Optional.of(RiskScoringConfigurationDTO.getDefaultConfiguration());
//        }
//        return config.get();
//    }
//
//    @Override
//    public void updateRiskScoreToContext(int contextId, double riskScore) {
//        try (MongoClient mongoClient = getMongoClientInstance()){
//
//        }
//    }
//
//    @Override
//    public int updateRiskScoringConfiguration(RiskScoringConfigurationDTO riskScoringConfiguration, String updateAuthor) {
//        return 0;
//    }
//
//    @Override
//    public Optional<HistoricalMetricsObjectResponse> getUserHistoricalMetrics(String user) {
//        try ( MongoClient mongoClient = getMongoClientInstance()){
//            if(!userHistoricalCollection.isEmpty()) {
//                MongoCollection<Document> collection = mongoClient
//                        .getDatabase(db_mongo_database)
//                        .getCollection(userHistoricalCollection);
//                Iterator<Document> documentsIterator = collection.find(eq("_id", user)).iterator();
//                if (documentsIterator.hasNext()) {
//                    return Optional.of(new MetricsObjectDocumentMapper()
//                            .toHistoricalMetricsObjectResponse(documentsIterator.next()));
//                }
//            }
//            return Optional.absent();
//        }
//    }
//
////    private void createMockTextFileContext(LocalDateTime initialDate, LocalDateTime endingDate, String username, List<String> listElements) {
////        if(listElements.size() > 0) {
////            MockObjectCreatorImpl.createMockTextFileContext("Contexts_" + username, listElements, initialDate, endingDate);
////        }
////    }
//
//    @Override
//    public List<LocationRelation> getUniqueLocationRelationList(final LocalDateTime initialDate,
//                                                                final LocalDateTime endingDate,
//                                                                final String username) {
//        try ( MongoClient mongoClient = getMongoClientInstance()) {
//            MongoCollection<Document> collection = mongoClient.getDatabase(db_mongo_database).getCollection(contextCollection);
//            String fieldDateName = "serverInfo.date";
//            String fieldUsername = "serverInfo.username";
//            Bson filter = and(eq(fieldUsername, username),
//                    gte(fieldDateName, DateHelper.getDateFromLDT(initialDate)),
//                    lte(fieldDateName, DateHelper.getDateFromLDT(endingDate)));
//            Bson fields = fields(include("serverInfo.latitude", "serverInfo.longitude", "serverInfo.date"), excludeId());
//            Set<String> controlSet = new HashSet<>();
//            List<LocationRelation> locationRelationList = new ArrayList<>();
//            Iterable<Document> sort = collection.find(filter).projection(fields).sort(eq(fieldDateName, 1));
//            for(Document x: sort){
//                if (x.get("serverInfo") instanceof Document) {
//                    Document serverInfo = (Document) x.get("serverInfo");
//                    final double latitude = serverInfo.getDouble("latitude");
//                    final double longitude = serverInfo.getDouble("longitude");
//                    final String yearMonthString = DateHelper.getStringYearMonthFromDate(DateHelper.getLDTFromDate(serverInfo.getDate("date")));
//                    final String filteredDocument =  latitude + " "
//                            + longitude + " "
//                            + yearMonthString ;
//                    if (!controlSet.contains(filteredDocument)) {
//                        controlSet.add(filteredDocument);
//                        locationRelationList.add(new LocationRelation(yearMonthString, latitude, longitude));
//                    }
//                }
//            }
//            return locationRelationList;
//        }
//    }

}

//        List<ContextObject> contextObjects = ContextObjectMapper.toContextObject(documentsList);
//        List<String> listElements = new ArrayList<>();
//        ObjectMapper mapper = new ObjectMapper();
//        for (ContextObject contextObject : contextObjects)
//            try {
//                listElements.add(mapper.writeValueAsString(contextObject));
//            } catch (JsonProcessingException e) {
//                e.printStackTrace();
//            }
//; //DO NOT REMOVE THIS LINE!