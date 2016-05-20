package com.peter.util.db;

import com.google.common.base.Optional;
import com.mongodb.MongoClient;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Created by Peter on 1/23/2016.
 */
public class MongoDBManagerImplTest {
    public DBManager connectionWithDB;
    public DBManager connection;
    public ConnectionInfo connectionInfo;
    public ConnectionInfo connectionInfoWithDB;
    @Before
    public void before(){
        connectionInfo=new ConnectionInfo("jdbc:mongo://192.168.243.197:27017", Optional.absent());
        connection =DBManagerFactory.buildDBManager(connectionInfo);
        connectionInfoWithDB=new ConnectionInfo("jdbc:mongo://192.168.243.197:27017/historicContext", Optional.absent());
        connectionWithDB =DBManagerFactory.buildDBManager(connectionInfoWithDB);
    }
    @Test
    public void testGetConnection() throws Exception {
        MongoClient mongoClient=(MongoClient) connection.connect();
        assertTrue(mongoClient.getConnectPoint().equals("192.168.243.197:27017"));
    }

    @Test
    public void testCloseConnection() throws Exception {
        MongoClient mongoClient=(MongoClient) connection.connect();
        mongoClient.close();
        DBManager dbA = DBManagerFactory.buildDBManager(connectionInfo);
        MongoClient mongoClientA=(MongoClient) connection.connect();
        assertTrue(mongoClient!=mongoClientA);
    }

    @Test
    public void testNotCloseConnection() throws Exception {
        MongoClient mongoClient=(MongoClient) connection.connect();
        DBManager dbA = DBManagerFactory.buildDBManager(connectionInfo);
        MongoClient mongoClientA=(MongoClient) connection.connect();
        assertTrue(mongoClient==mongoClientA);
    }

    @Test
    public void testGetCommandList() throws Exception {
//         connection.executeQuery("SELECT * FROM textTable");
//         connection.executeQuery("select * from textTable");
    }

    @Test
    public void testExecuteQuerySelectSimple() throws Exception {
        MongoClient mongoClient=(MongoClient) connectionWithDB.connect();
        ArrayList<HashMap> result = connectionWithDB.executeQuery("SELECT * FROM historicalPopulationMetrics");
        assertTrue(result.size()==2);
    }

    @Test
    public void testExecuteQuerySelectComplex() throws Exception {
        MongoClient mongoClient=(MongoClient) connectionWithDB.connect();
        ArrayList<HashMap> result = connectionWithDB.executeQuery("SELECT date,metrics FROM historicalPopulationMetrics");
        assertTrue(!result.get(0).containsKey("_id"));
        assertTrue(result.get(0).containsKey("date"));
        assertTrue(result.get(0).containsKey("metrics"));
    }

    @Test
    public void testExecuteQuerySelectWithWhereOneStament() throws Exception {
        MongoClient mongoClient=(MongoClient) connectionWithDB.connect();
//        ArrayList<HashMap> result = connectionWithDB.executeQuery("SELECT _id,date FROM historicalUsernMetrics WHERE (_id='dbetancourt' OR _id='dcastaneda') AND (date='2015-06-23T18:28:30.859Z' OR date='2015-06-23T03:52:07.796Z') ");
        ArrayList<HashMap> result = connectionWithDB.executeQuery("SELECT _id,date FROM historicalUserMetrics WHERE _id='dbetancourt'");
        assertTrue(!result.get(0).containsKey("metrics"));
        assertTrue(result.get(0).containsKey("_id"));
        assertTrue(result.get(0).containsKey("date"));
        assertTrue(result.get(0).get("_id").equals("dbetancourt"));
    }

    @Test
    public void testExecuteQuerySelectWithWhereWithRegExpPositive() throws Exception {
        MongoClient mongoClient=(MongoClient) connectionWithDB.connect();
        ArrayList<HashMap> result =  connectionWithDB.executeQuery("SELECT * FROM historicalUserMetrics WHERE _id ~'dbet.*ourt'");
        assertTrue(result.get(0).get("_id").equals("dbetancourt"));
    }

    @Test
    public void testExecuteQuerySelectWithWhereWithRegExpNegative() throws Exception {
        MongoClient mongoClient=(MongoClient) connectionWithDB.connect();
        ArrayList<HashMap> result =  connectionWithDB.executeQuery("SELECT * FROM historicalUserMetrics WHERE _id ~'dbet.*ou5rt'");
        assertTrue(result.size()==0);
    }

    @Test
    public void testExecuteQuerySelectWithWhereWithGT() throws Exception {
        MongoClient mongoClient=(MongoClient) connectionWithDB.connect();
        ArrayList<HashMap> result =  connectionWithDB.executeQuery("SELECT * FROM historicalUserMetrics WHERE date >'2015-06-23T18:28:30.859Z'");
        assertTrue(result.size()==2);
    }

    @Test
    public void testExecuteQuerySelectWithWhereWithGTE() throws Exception {
        MongoClient mongoClient=(MongoClient) connectionWithDB.connect();
        ArrayList<HashMap> result =  connectionWithDB.executeQuery("SELECT * FROM historicalUserMetrics WHERE date >='2015-06-23T18:28:30.859Z'");
        assertTrue(result.size()==3);
    }

    @Test
    public void testExecuteQuerySelectWithWhereWithLTE() throws Exception {
        MongoClient mongoClient=(MongoClient) connectionWithDB.connect();
        ArrayList<HashMap> result =  connectionWithDB.executeQuery("SELECT * FROM historicalUserMetrics WHERE date <='2015-06-23T18:28:30.859Z'");
        assertTrue(result.size()==3);
    }

    @Test
    public void testExecuteQuerySelectWithWhereWithLT() throws Exception {
        MongoClient mongoClient=(MongoClient) connectionWithDB.connect();
        ArrayList<HashMap> result =  connectionWithDB.executeQuery("SELECT * FROM historicalUserMetrics WHERE date <'2015-06-23T18:28:30.859Z'");
        assertTrue(result.size()==2);
    }

    @Test
    public void testExecuteQuerySelectWithWhereWithAnd() throws Exception {
        MongoClient mongoClient=(MongoClient) connectionWithDB.connect();
        ArrayList<HashMap> result =  connectionWithDB.executeQuery("SELECT * FROM historicalUserMetrics WHERE date <='2015-06-23T03:52:07.796Z' AND date >= '2015-06-23T03:52:07.796Z'");
        assertTrue(result.size()==1);
    }

    @Test
    public void testExecuteQuerySelectWithWhereWithAnd2() throws Exception {
        MongoClient mongoClient=(MongoClient) connectionWithDB.connect();
        ArrayList<HashMap> result =  connectionWithDB.executeQuery("SELECT * FROM context WHERE serverInfo.date >='2014-09-16T23:03:52.000Z' AND serverInfo.date <= '2014-09-17T14:41:21.000Z'");
        assertTrue(result.size()==3);
    }

    @Test
    public void testExecuteQueryInsertWithColumnNames() throws Exception {
        MongoClient mongoClient=(MongoClient) connectionWithDB.connect();
        ArrayList<HashMap> result =  connectionWithDB.executeQuery("INSERT INTO simulatedContext ( ctx_date ,ctx_user ,ctx_latitude ,ctx_longitude ,ctx_body ,ctx_risk ) VALUES ( '2011-03-30 15:05:00' ,'UserMonthly' ,'0.0' ,'0.0' ,'null' ,'0.5795924069441956' )");
    }

    @Test
    public void testExecuteQueryInsertWithOutColumnNames() throws Exception {
        MongoClient mongoClient=(MongoClient) connectionWithDB.connect();
        String context="{ \"serverInfo\" : { \"date\" : \"2015-03-18T05:29:03.000+0000\", \"country_code\" : \"CO\", \"region_name\" : \"Antioquia\", \"city_name\" : \"Medellin\", \"latitude\" : 6.25184, \"longitude\" : -75.56359, \"ipAddress\" : \"190.85.66.109\", \"username\" : \"GiovanyMoreno\" }, \"flashFonts\" : [ \"Abyssinica SIL\", \"Andale Mono\", \"Arial\", \"Arial Black\", \"Bitstream Charter\", \"Century Schoolbook L\", \"Comic Sans MS\", \"Courier 10 Pitch\", \"Courier New\", \"DejaVu Sans\", \"DejaVu Sans Mono\", \"DejaVu Serif\", \"Dingbats\", \"Droid Arabic Naskh\", \"Droid Sans\", \"Droid Sans Armenian\", \"Droid Sans Ethiopic\", \"Droid Sans Georgian\", \"Droid Sans Mono\", \"Droid Serif\", \"FreeMono\", \"FreeSans\", \"FreeSerif\", \"gargi\", \"Garuda\", \"Georgia\", \"Impact\", \"KacstArt\", \"KacstBook\", \"KacstDecorative\", \"KacstDigital\", \"KacstFarsi\", \"KacstLetter\", \"KacstNaskh\", \"KacstOffice\", \"KacstOne\", \"KacstPen\", \"KacstPoster\", \"KacstQurn\", \"KacstScreen\", \"KacstTitle\", \"KacstTitleL\", \"Kedage\", \"Khmer OS\", \"Khmer OS System\", \"Kinnari\", \"Liberation Mono\", \"Liberation Sans\", \"Liberation Sans Narrow\", \"Liberation Serif\", \"LKLUG\", \"Lohit Bengali\", \"Lohit Devanagari\", \"Lohit Gujarati\", \"Lohit Punjabi\", \"Lohit Tamil\", \"Loma\", \"Mallige\", \"Meera\", \"Monospace\", \"mry_KacstQurn\", \"Mukti Narrow\", \"NanumBarunGothic\", \"NanumGothic\", \"NanumMyeongjo\", \"Nimbus Mono L\", \"Nimbus Roman No9 L\", \"Nimbus Sans L\", \"Norasi\", \"OpenSymbol\", \"ori1Uni\", \"Padauk\", \"Padauk Book\", \"Phetsarath OT\", \"Pothana2000\", \"Purisa\", \"Rachana\", \"Rekha\", \"Saab\", \"Sans\", \"Sawasdee\", \"Serif\", \"Standard Symbols L\", \"Symbol\", \"TakaoPGothic\", \"Tibetan Machine Uni\", \"Times New Roman\", \"Tlwg Typist\", \"Tlwg Typo\", \"TlwgMono\", \"TlwgTypewriter\", \"Trebuchet MS\", \"Ubuntu\", \"Ubuntu Condensed\", \"Ubuntu Mono\", \"Ume Gothic\", \"Ume Gothic C4\", \"Ume Gothic C5\", \"Ume Gothic O5\", \"Ume Gothic S4\", \"Ume Gothic S5\", \"Ume Mincho\", \"Ume Mincho S3\", \"Ume P Gothic\", \"Ume P Gothic C4\", \"Ume P Gothic C5\", \"Ume P Gothic O5\", \"Ume P Gothic S4\", \"Ume P Gothic S5\", \"Ume P Mincho\", \"Ume P Mincho S3\", \"Ume UI Gothic\", \"Ume UI Gothic O5\", \"Umpush\", \"UnBatang\", \"UnDinaru\", \"UnDotum\", \"UnGraphic\", \"UnGungseo\", \"UnPilgi\", \"URW Bookman L\", \"URW Chancery L\", \"URW Gothic L\", \"URW Palladio L\", \"Vemana2000\", \"Verdana\", \"Waree\", \"Webdings\", \"WenQuanYi Micro Hei\", \"WenQuanYi Micro Hei Mono\" ], \"flashVars\" : { \"fl_screenResolutionY\" : \"1080\", \"fl_hasAccessibility\" : \"true\", \"fl_hasAudioEncoder\" : \"true\", \"fl_hasVideoEncoder\" : \"true\", \"fl__internal\" : \"5\", \"fl_isEmbeddedInAcrobat\" : \"false\", \"fl_hasAudio\" : \"true\", \"fl_screenResolutionX\" : \"1920\", \"fl_manufacturer\" : \"Google Pepper\", \"fl_touchscreenType\" : \"none\", \"fl_timeZoneOffset\" : \"UTC-300\", \"fl_hasIME\" : \"true\", \"fl_hasScreenPlayback\" : \"false\", \"fl_hasStreamingAudio\" : \"true\", \"fl_hasMP3\" : \"true\", \"fl_hasStreamingVideo\" : \"true\", \"fl_hasEmbeddedVideo\" : \"true\", \"fl_screenDPI\" : \"72\", \"fl_supports64BitProcesses\" : \"true\", \"fl_pixelAspectRatio\" : \"1\", \"fl_language\" : \"en\", \"fl_playerType\" : \"PlugIn\", \"fl_cpuArchitecture\" : \"x86\", \"fl_supports32BitProcesses\" : \"true\", \"fl_isDebugger\" : \"false\", \"fl_maxLevelIDC\" : \"5.1\", \"fl_avHardwareDisable\" : \"false\", \"fl_hasTLS\" : \"true\", \"fl_screenColor\" : \"color\", \"fl_os\" : \"Linux\", \"fl_localFileReadDisable\" : \"false\", \"fl_serverString\" : \"A=t&SA=t&SV=t&EV=t&MP3=t&AE=t&VE=t&ACC=t&PR=t&SP=f&SB=f&DEB=f&V=LNX%2014%2C0%2C0%2C177&M=Google%20Pepper&R=1920x1080&COL=color&AR=1.0&OS=Linux&ARCH=x86&L=en&IME=t&PR32=t&PR64=t&PT=PlugIn&AVD=f&LFD=f&WD=f&TLS=t&ML=5.1&DP=72\", \"fl_hasPrinting\" : \"true\", \"fl_version\" : \"LNX 14,0,0,177\", \"fl_hasScreenBroadcast\" : \"false\" }, \"jsFonts\" : [  ], \"documentFeatures\" : { \"XML-3_0\" : true, \"Traversal\" : true, \"HTML\" : true, \"CSS2-3_0\" : true, \"HTML-3_0\" : true, \"Views-1_0\" : true, \"UIEvents-1_0\" : true, \"Views-3_0\" : true, \"UIEvents-3_0\" : true, \"CSS2-1_0\" : true, \"HTMLEvents-2_0\" : true, \"HTMLEvents-4_0\" : true, \"StyleSheets-2_0\" : true, \"UIEvents\" : true, \"MouseEvents-2_0\" : true, \"StyleSheets-4_0\" : true, \"MouseEvents-4_0\" : true, \"Range-2_0\" : true, \"Views\" : true, \"Events-4_0\" : true, \"MutationEvents-3_0\" : true, \"Range-4_0\" : true, \"Traversal-1_0\" : true, \"MutationEvents-1_0\" : true, \"Range\" : true, \"Traversal-3_0\" : true, \"MouseEvents\" : true, \"CSS-3_0\" : true, \"CSS2\" : true, \"CSS-1_0\" : true, \"Events-2_0\" : true, \"HTMLEvents\" : true, \"StyleSheets\" : true, \"XML-4_0\" : true, \"CSS2-2_0\" : true, \"HTML-4_0\" : true, \"UIEvents-2_0\" : true, \"CSS2-4_0\" : true, \"Views-2_0\" : true, \"UIEvents-4_0\" : true, \"Views-4_0\" : true, \"HTMLEvents-1_0\" : true, \"MutationEvents\" : true, \"HTMLEvents-3_0\" : true, \"Events\" : true, \"MouseEvents-1_0\" : true, \"StyleSheets-1_0\" : true, \"MouseEvents-3_0\" : true, \"StyleSheets-3_0\" : true, \"Range-1_0\" : true, \"CSS\" : true, \"MutationEvents-4_0\" : true, \"Events-3_0\" : true, \"MutationEvents-2_0\" : true, \"Range-3_0\" : true, \"Traversal-4_0\" : true, \"Traversal-2_0\" : true, \"CSS-4_0\" : true, \"CSS-2_0\" : true, \"XML\" : true, \"Events-1_0\" : true }, \"mimeTypes\" : [ { \"type\" : \"application/futuresplash\", \"description\" : \"FutureSplash Player\", \"suffixes\" : \"spl\" }, { \"type\" : \"application/pdf\", \"description\" : \"Portable Document Format\", \"suffixes\" : \"pdf\" }, { \"type\" : \"application/vnd.chromium.remoting-viewer\", \"description\" : \"Unknown type\", \"suffixes\" : \"None\" }, { \"type\" : \"application/x-google-chrome-print-preview-pdf\", \"description\" : \"Portable Document Format\", \"suffixes\" : \"pdf\" }, { \"type\" : \"application/x-nacl\", \"description\" : \"Native Client Executable\", \"suffixes\" : \"None\" }, { \"type\" : \"application/x-pnacl\", \"description\" : \"Portable Native Client Executable\", \"suffixes\" : \"None\" }, { \"type\" : \"application/x-ppapi-widevine-cdm\", \"description\" : \"Widevine Content Decryption Module\", \"suffixes\" : \"None\" }, { \"type\" : \"application/x-shockwave-flash\", \"description\" : \"Shockwave Flash\", \"suffixes\" : \"swf\" } ], \"plugins\" : [ { \"name\" : \"Chrome PDF Viewer\", \"description\" : \"d41d8cd98f00b204e9800998ecf8427e\", \"filename\" : \"libpdf.so\", \"version\" : null }, { \"name\" : \"Chrome Remote Desktop Viewer\", \"description\" : \"99882976076860da0023577f679e6692\", \"filename\" : \"internal-remoting-viewer\", \"version\" : null }, { \"name\" : \"Native Client\", \"description\" : \"d41d8cd98f00b204e9800998ecf8427e\", \"filename\" : \"libppGoogleNaClPluginChrome.so\", \"version\" : null }, { \"name\" : \"Shockwave Flash\", \"description\" : \"c809ff0e397acc05473accc725b08b3e\", \"filename\" : \"libpepflashplayer.so\", \"version\" : null }, { \"name\" : \"Widevine Content Decryption Module\", \"description\" : \"7de642232df05b29e3644f06ba88a7c3\", \"filename\" : \"libwidevinecdmadapter.so\", \"version\" : null } ], \"osStyleColors\" : { \"ActiveBorder\" : \"rgb(255, 255, 255)\", \"ActiveCaption\" : \"rgb(204, 204, 204)\", \"AppWorkspace\" : \"rgb(255, 255, 255)\", \"Background\" : \"rgb(99, 99, 206)\", \"ButtonFace\" : \"rgb(221, 221, 221)\", \"ButtonHighlight\" : \"rgb(221, 221, 221)\", \"ButtonShadow\" : \"rgb(136, 136, 136)\", \"ButtonText\" : \"rgb(0, 0, 0)\", \"CaptionText\" : \"rgb(0, 0, 0)\", \"GrayText\" : \"rgb(128, 128, 128)\", \"Highlight\" : \"rgb(181, 213, 255)\", \"HighlightText\" : \"rgb(0, 0, 0)\", \"InactiveBorder\" : \"rgb(255, 255, 255)\", \"InactiveCaption\" : \"rgb(255, 255, 255)\", \"InactiveCaptionText\" : \"rgb(127, 127, 127)\", \"InfoBackground\" : \"rgb(251, 252, 197)\", \"InfoText\" : \"rgb(0, 0, 0)\", \"Menu\" : \"rgb(247, 247, 247)\", \"MenuText\" : \"rgb(0, 0, 0)\", \"Scrollbar\" : \"rgb(255, 255, 255)\", \"ThreeDDarkShadow\" : \"rgb(102, 102, 102)\", \"ThreeDFace\" : \"rgb(192, 192, 192)\", \"ThreeDHighlight\" : \"rgb(221, 221, 221)\", \"ThreeDLightShadow\" : \"rgb(192, 192, 192)\", \"ThreeDShadow\" : \"rgb(136, 136, 136)\", \"Window\" : \"rgb(255, 255, 255)\", \"WindowFrame\" : \"rgb(204, 204, 204)\", \"WindowText\" : \"rgb(0, 0, 0)\" }, \"jsGeneralData\" : { \"navigator_vendor\" : \"Google Inc.\", \"operaVersion\" : \"false\", \"cookieEnabled\" : \"true\", \"CPU\" : \"false\", \"navigator_userLanguage\" : \"false\", \"SunJavaPlugin\" : \"false\", \"screen_deviceXDPI\" : \"false\", \"navigator_browserLanguage\" : \"false\", \"SVGViewer\" : \"false\", \"screen_height\" : \"1080\", \"timezoneOffset\" : \"300\", \"AdobeShockwave\" : \"false\", \"Silverlight_supportedUserAgent\" : \"false\", \"browser_version\" : \"37.0.2062.94\", \"screen_logicalYDPI\" : \"false\", \"screen_width\" : \"1920\", \"RealPlayer\" : \"false\", \"adobeReader\" : \"false\", \"AjaxXMLParser\" : \" via DOMParser\", \"dotNetFramework\" : \"\", \"layout_version\" : \"537.36\", \"isMozilla\" : \"false\", \"screen_deviceYDPI\" : \"false\", \"RealOneComponents\" : \"false\", \"WebKit\" : \"537.36\", \"browser_name\" : \"chrome\", \"screen_pixelDepth\" : \"24\", \"navigator_appMinorVersion\" : \"false\", \"flashVersion\" : \"Shockwave Flash 14.0 r0\", \"WebKitNightlyBuild\" : \"No\", \"googleChromeVersion\" : \"37\", \"crypto_version\" : \"false\", \"screen_logicalXDPI\" : \"false\", \"WebKitBrowserName\" : \"Google Chrome 37.0.2062.94\", \"googleChromeLayout\" : \"webkit5\", \"WebKitMobileDevice\" : \"false\", \"RealJukebox\" : \"false\", \"navigator_appCodeName\" : \"Mozilla\", \"navigator_appVersion\" : \"5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.94 Safari/537.36\", \"screen_bufferDepth\" : \"false\", \"operaLayout\" : \"false\", \"window_offscreenBuffering\" : \"false\", \"screen_updateInterval\" : \"false\", \"MicrosoftScriptControl\" : \"false\", \"navigator_oscpu\" : \"false\", \"geckoProduct\" : \"Gecko 20030107\", \"navigator_securityPolicy\" : \"false\", \"navigator_systemLanguage\" : \"false\", \"XMLSerializer\" : \"true\", \"layout_name\" : \"webkit\", \"isGoogleChrome\" : \"true\", \"RealOne\" : \"false\", \"screen_fontSmoothingEnabled\" : \"false\", \"navigator_userAgent\" : \"Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.94 Safari/537.36\", \"QuickTimePlayer\" : \"false\", \"isOpera\" : \"false\", \"google_gears\" : \"false\", \"activex\" : \"false\", \"navigator_platform\" : \"Linux x86_64\", \"navigator_appName\" : \"Netscape\", \"navigator_onLine\" : \"true\", \"document_defaultCharset\" : \"true\", \"geckoVendor\" : \"\", \"RealPlayerG2\" : \"false\", \"os_name\" : \"linux\", \"XMLHttpRequest\" : \" via XMLHttpRequest object\", \"navigator_language\" : \"en-US\", \"screen_colorDepth\" : \"24\", \"IEVersion\" : \"false\", \"citrix\" : \"false\", \"openOfficePlugIn\" : \"false\" }, \"canvas\" : \"211f2aaa5f266bd2c03abb201c0c05535b6222f69b6ef4da3b8deaf5ef16e85a1232793c48630857e08f0f779f69ba24fea69024659f4e67f528f8be181a5608####\" }";
        ArrayList<HashMap> result =  connectionWithDB.executeQuery("INSERT INTO simulatedContext VALUES ( '"+context+"')");
    }

    @Test
    public void testExecuteQueryTruncateTable() throws Exception {
        MongoClient mongoClient=(MongoClient) connectionWithDB.connect();
        ArrayList<HashMap> result =  connectionWithDB.executeQuery("TRUNCATE TABLE simulatedContext");
    }

    @Test
    public void testExecuteQueryCleanTable() throws Exception {
        MongoClient mongoClient=(MongoClient) connectionWithDB.connect();
        connectionWithDB.cleanTable("simulatedContext");
    }

    @Test
    public void testExecuteSQLFile() throws Exception {
        MongoClient mongoClient=(MongoClient) connectionWithDB.connect();
        ArrayList pairsToReplace = new ArrayList() ;
        pairsToReplace.add(new String []{"p_table","historicalPopulationMetrics"});
        ArrayList<ArrayList<HashMap>> data = connectionWithDB.executeSQLFile("src/main/test/resources/db/mongo/select.sql", pairsToReplace);
        assertTrue(data.get(0).size()>0);
    }

    @Test
    public void testGetLastQuery() throws Exception {
        MongoClient mongoClient=(MongoClient) connectionWithDB.connect();
        ArrayList<HashMap> result =  connectionWithDB.executeQuery("SELECT * FROM historicalUserMetrics WHERE date <'2015-06-23T18:28:30.859Z'");
        assertTrue(connectionWithDB.getLastQuery().query.size()==1);
    }

    @Test
    public void testSelectWithLimit() throws Exception {
        MongoClient mongoClient=(MongoClient) connectionWithDB.connect();
        ArrayList<HashMap> result = connectionWithDB.executeQuery("SELECT * FROM historicalUserMetrics LIMIT 1");
        assertTrue(result.size()==1);
    }

    @Test
    public void testSelectWithLimitAll() throws Exception {
        MongoClient mongoClient=(MongoClient) connectionWithDB.connect();
        ArrayList<HashMap> result = connectionWithDB.executeQuery("SELECT * FROM historicalUserMetrics LIMIT 0");
        assertTrue(result.size()==5);
    }

    @Test
    public void testSelectWithoutLimit() throws Exception {
        MongoClient mongoClient=(MongoClient) connectionWithDB.connect();
        ArrayList<HashMap> result = connectionWithDB.executeQuery("SELECT * FROM historicalUserMetrics");
        assertTrue(result.size()==5);
    }

    @Test
    public void testSelectWithOffset() throws Exception {
        MongoClient mongoClient=(MongoClient) connectionWithDB.connect();
        ArrayList<HashMap> result = connectionWithDB.executeQuery("SELECT * FROM historicalUserMetrics LIMIT 1 OFFSET 3");
        assertTrue(result.size()==1);
        assertTrue(result.get(0).get("_id").equals("EoinHorgan"));
    }

    @Test
    public void testSelectWithoutOffsetAll() throws Exception {
        MongoClient mongoClient=(MongoClient) connectionWithDB.connect();
        ArrayList<HashMap> result = connectionWithDB.executeQuery("SELECT * FROM historicalUserMetrics LIMIT 1 OFFSET 0");
        assertTrue(result.size()==1);
        assertTrue(result.get(0).get("_id").equals("rlinero"));
    }

    @Test
    public void testSelectWithoutOffset() throws Exception {
        MongoClient mongoClient=(MongoClient) connectionWithDB.connect();
        ArrayList<HashMap> result = connectionWithDB.executeQuery("SELECT * FROM historicalUserMetrics");
        assertTrue(result.size()==5);
    }

    @Test
    public void testSelectCount() throws Exception {
        MongoClient mongoClient=(MongoClient) connectionWithDB.connect();
        ArrayList<HashMap> result = connectionWithDB.executeQuery("SELECT COUNT(*) FROM historicalUserMetrics");
        assertTrue(result.get(0).get("count").equals(5l));
    }

    @Test
    public void testSelectCountWhere() throws Exception {
        MongoClient mongoClient=(MongoClient) connectionWithDB.connect();
        ArrayList<HashMap> result =  connectionWithDB.executeQuery("SELECT COUNT(*) FROM context WHERE serverInfo.date >='2014-09-16T23:03:52.000Z' AND serverInfo.date <= '2014-09-17T14:41:21.000Z'");
        assertTrue(result.get(0).get("count").equals(3l));
    }

    @Test
    public void testSelectWithLimitOrderASC() throws Exception {
        MongoClient mongoClient=(MongoClient) connectionWithDB.connect();
        ArrayList<HashMap> result = connectionWithDB.executeQuery("SELECT * FROM historicalUserMetrics ORDER BY date ASC LIMIT 0");
        assertTrue(result.get(0).get("_id").equals("rlinero"));
    }

    @Test
    public void testSelectWithLimitOrderASCSecondLevel() throws Exception {
        MongoClient mongoClient=(MongoClient) connectionWithDB.connect();
        ArrayList<HashMap> result = connectionWithDB.executeQuery("SELECT * FROM context ORDER BY serverInfo.date ASC LIMIT 5");
        assertTrue(result.get(0).get("_id").toString().equals("5586385618025318465b1aaf"));
    }

    @Test
    public void testSelectWithLimitOrderDES() throws Exception {
        MongoClient mongoClient=(MongoClient) connectionWithDB.connect();
        ArrayList<HashMap> result = connectionWithDB.executeQuery("SELECT * FROM historicalUserMetrics ORDER BY date DESC LIMIT 0");
        assertTrue(result.get(0).get("_id").equals("lnino"));
    }

    @Test
    public void testSelectWithLimitOrderDESCSecondLevel() throws Exception {
        MongoClient mongoClient=(MongoClient) connectionWithDB.connect();
        ArrayList<HashMap> result = connectionWithDB.executeQuery("SELECT * FROM context ORDER BY serverInfo.date DESC LIMIT 5");
        assertTrue(result.get(0).get("_id").toString().equals("561d1e1f15fa5b5db9c95318"));
    }

}