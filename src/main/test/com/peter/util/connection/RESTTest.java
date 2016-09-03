package com.peter.util.connection;

import com.jayway.restassured.response.Response;
import com.peter.util.connection.request.RequestType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.peter.util.data.JSON;
import com.peter.util.sys.Environment;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by Peter on 11/20/15.
 */
public class RESTTest {
    public String accessPointHost;
    public String accessPointPort;
    public REST rest;
    public String accessPointToken;
    public String queryInstruments;
    public String url;
    public String urlA;
    public String urlB;
    private String urlC;

    @Before
    public void setUp() throws Exception {
        accessPointHost="https://api-fxpractice.oanda.com/v1";
        accessPointPort="443";
        url="https://api-fxpractice.oanda.com:443/v1/candles";
        accessPointToken="96adf2bb5787f47f8a42e8a188ddbe27-6abfff05cf00171e5bee37d64ffa2eca";
        queryInstruments="EUR_USD";
        urlA="http://httpbin.org/post";
        urlB="http://httpbin.org/response-headers?key=value&key1=value1";
        urlC="http://httpbin.org//post";
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGet() throws Exception {
        rest = new REST(accessPointHost, accessPointPort, "candles");
        Map<String, String> headersMaps = new HashMap<String,String>() {{put("Authorization", "Bearer "+accessPointToken);}};
        Map<String, String> paramsMaps = new HashMap<String,String>() {{put("instrument",queryInstruments);}};
        Response response = rest.get(headersMaps,paramsMaps);
//        paramsMaps = new HashMap<String,String>() {{put("accountId","8978014");}};
//        rest = new REST(accessPointHost, accessPointPort, "instruments");
//        response = rest.get(headersMaps,paramsMaps);
        JSON json = new JSON(response);
        json.exportCsv(Environment.getInstance().rootPath +  "\\"+"test"+".csv");
        assertTrue(response!=null);
    }

    @Test
    public void testGetConstructorOnlyHostWithoutPort() throws Exception {
        rest = new REST(accessPointHost, "candles");
        Map<String, String> headersMaps = new HashMap<String,String>() {{put("Authorization", "Bearer "+accessPointToken);}};
        Map<String, String> paramsMaps = new HashMap<String,String>() {{put("instrument",queryInstruments);}};
        Response response = rest.get(headersMaps,paramsMaps);
        assertTrue(response!=null);
    }

    @Test
    public void testGetConstructorHostWithPort() throws Exception {
        rest = new REST("https://api-fxpractice.oanda.com"+":" +accessPointPort, "/v1/candles");
        Map<String, String> headersMaps = new HashMap<String,String>() {{put("Authorization", "Bearer "+accessPointToken);}};
        Map<String, String> paramsMaps = new HashMap<String,String>() {{put("instrument",queryInstruments);}};
        Response response = rest.get(headersMaps,paramsMaps);
        assertTrue(response!=null);
    }

    @Test
    public void testGetConstructorURL() throws Exception {
        rest = new REST(url);
        Map<String, String> headersMaps = new HashMap<String,String>() {{put("Authorization", "Bearer "+accessPointToken);}};
        Map<String, String> paramsMaps = new HashMap<String,String>() {{put("instrument",queryInstruments);}};
        Response response = rest.get(headersMaps,paramsMaps);
        assertTrue(response!=null);
    }

    @Test
    public void testPostWithPathParameters() throws Exception {
        rest = new REST(urlA, RequestType.Type.POST);
        Map<String, String> pathParams = new HashMap<String,String>() {{put("key","Value");}};
//        String body="{\"documentFeatures\":{\"CSS-2.0\":true,\"CSS-4.0\":true,\"Traversal\":true,\"HTML\":true,\"Range-1.0\":true,\"XML-3.0\":true,\"Events-4.0\":true,\"MutationEvents-3.0\":true,\"Events-2.0\":true,\"MutationEvents-1.0\":true,\"Range-3.0\":true,\"HTML-3.0\":true,\"UIEvents\":true,\"UIEvents-1.0\":true,\"CSS2-3.0\":true,\"Views-1.0\":true,\"UIEvents-3.0\":true,\"Traversal-4.0\":true,\"Views-3.0\":true,\"CSS2-1.0\":true,\"Traversal-2.0\":true,\"Views\":true,\"HTMLEvents-1.0\":true,\"HTMLEvents-3.0\":true,\"StyleSheets-1.0\":true,\"StyleSheets-3.0\":true,\"MouseEvents-2.0\":true,\"Range\":true,\"MouseEvents-4.0\":true,\"MouseEvents\":true,\"CSS2\":true,\"HTMLEvents\":true,\"StyleSheets\":true,\"CSS-3.0\":true,\"CSS-1.0\":true,\"MutationEvents\":true,\"CSS2-4.0\":true,\"Range-4.0\":true,\"Events\":true,\"MutationEvents-4.0\":true,\"Range-2.0\":true,\"Events-3.0\":true,\"MutationEvents-2.0\":true,\"HTML-4.0\":true,\"Views-2.0\":true,\"CSS2-2.0\":true,\"UIEvents-2.0\":true,\"Traversal-3.0\":true,\"Views-4.0\":true,\"UIEvents-4.0\":true,\"Traversal-1.0\":true,\"CSS\":true,\"HTMLEvents-2.0\":true,\"HTMLEvents-4.0\":true,\"Events-1.0\":true,\"XML-4.0\":true,\"StyleSheets-2.0\":true,\"MouseEvents-1.0\":true,\"StyleSheets-4.0\":true,\"MouseEvents-3.0\":true,\"XML\":true},\"mimeTypes\":[{\"suffixes\":\"spl\",\"description\":\"FutureSplash movie\",\"type\":\"application/futuresplash\"},{\"suffixes\":\"swf\",\"description\":\"Adobe Flash movie\",\"type\":\"application/x-shockwave-flash\"},{\"suffixes\":\"None\",\"description\":\"Unknown type\",\"type\":\"application/x-vnd.google.oneclickctrl.9\"},{\"suffixes\":\"None\",\"description\":\"Unknown type\",\"type\":\"application/x-vnd.google.update3webcontrol.3\"}],\"flashVars\":{\"fl.isEmbeddedInAcrobat\":\"false\",\"fl.os\":\"Windows 7\",\"fl.screenDPI\":\"72\",\"fl.maxLevelIDC\":\"5.1\",\"fl.timeZoneOffset\":\"UTC-300\",\"fl.screenResolutionX\":\"1008\",\"fl.hasPrinting\":\"true\",\"fl.screenResolutionY\":\"647\",\"fl.hasIME\":\"true\",\"fl.playerType\":\"PlugIn\",\"fl.hasVideoEncoder\":\"true\",\"fl.isDebugger\":\"false\",\"fl.hasAudioEncoder\":\"true\",\"fl.hasTLS\":\"true\",\"fl.hasMP3\":\"true\",\"fl.hasAccessibility\":\"true\",\"fl.screenColor\":\"color\",\"fl.manufacturer\":\"Adobe Windows\",\"fl.hasAudio\":\"true\",\"fl.cpuArchitecture\":\"x86\",\"fl.hasScreenPlayback\":\"false\",\"fl._internal\":\"5\",\"fl.touchscreenType\":\"none\",\"fl.avHardwareDisable\":\"false\",\"fl.hasStreamingAudio\":\"true\",\"fl.hasScreenBroadcast\":\"false\",\"fl.supports64BitProcesses\":\"false\",\"fl.hasEmbeddedVideo\":\"true\",\"fl.hasStreamingVideo\":\"true\",\"fl.pixelAspectRatio\":\"1\",\"fl.supports32BitProcesses\":\"true\",\"fl.language\":\"es\",\"fl.localFileReadDisable\":\"false\",\"fl.serverString\":\"A=t&SA=t&SV=t&EV=t&MP3=t&AE=t&VE=t&ACC=t&PR=t&SP=f&SB=f&DEB=f&V=WIN%2019%2C0%2C0%2C226&M=Adobe%20Windows&R=1008x647&COL=color&AR=1.0&OS=Windows%207&ARCH=x86&L=es&IME=t&PR32=t&PR64=f&PT=PlugIn&AVD=f&LFD=f&WD=f&TLS=t&ML=5.1&DP=72\",\"fl.version\":\"WIN 19,0,0,226\"},\"flashFonts\":[\"Aharoni\",\"Andalus\",\"Angsana New\",\"AngsanaUPC\",\"Aparajita\",\"Arabic Transparent\",\"Arabic Typesetting\",\"Arial\",\"Arial Baltic\",\"Arial Black\",\"Arial CE\",\"Arial CYR\",\"Arial Greek\",\"Arial TUR\",\"Batang\",\"BatangChe\",\"Browallia New\",\"BrowalliaUPC\",\"Calibri\",\"Cambria\",\"Cambria Math\",\"Candara\",\"Comic Sans MS\",\"Consolas\",\"Constantia\",\"Corbel\",\"Cordia New\",\"CordiaUPC\",\"Courier New\",\"Courier New Baltic\",\"Courier New CE\",\"Courier New CYR\",\"Courier New Greek\",\"Courier New TUR\",\"DaunPenh\",\"David\",\"DFKai-SB\",\"DilleniaUPC\",\"DokChampa\",\"Dotum\",\"DotumChe\",\"Ebrima\",\"Estrangelo Edessa\",\"EucrosiaUPC\",\"Euphemia\",\"FangSong\",\"Franklin Gothic Medium\",\"FrankRuehl\",\"FreesiaUPC\",\"Gabriola\",\"Gautami\",\"Georgia\",\"Gisha\",\"Gulim\",\"GulimChe\",\"Gungsuh\",\"GungsuhChe\",\"Impact\",\"IrisUPC\",\"Iskoola Pota\",\"JasmineUPC\",\"KaiTi\",\"Kalinga\",\"Kartika\",\"Khmer UI\",\"KodchiangUPC\",\"Kokila\",\"Lao UI\",\"Latha\",\"Leelawadee\",\"Levenim MT\",\"LilyUPC\",\"Lucida Console\",\"Lucida Sans Unicode\",\"Malgun Gothic\",\"Mangal\",\"Marlett\",\"Meiryo\",\"Meiryo UI\",\"Microsoft Himalaya\",\"Microsoft JhengHei\",\"Microsoft New Tai Lue\",\"Microsoft PhagsPa\",\"Microsoft Sans Serif\",\"Microsoft Tai Le\",\"Microsoft Uighur\",\"Microsoft YaHei\",\"Microsoft Yi Baiti\",\"MingLiU\",\"MingLiU-ExtB\",\"MingLiU_HKSCS\",\"MingLiU_HKSCS-ExtB\",\"Miriam\",\"Miriam Fixed\",\"Mongolian Baiti\",\"MoolBoran\",\"MS Gothic\",\"MS Mincho\",\"MS PGothic\",\"MS PMincho\",\"MS UI Gothic\",\"MV Boli\",\"Narkisim\",\"NSimSun\",\"Nyala\",\"Palatino Linotype\",\"Plantagenet Cherokee\",\"PMingLiU\",\"PMingLiU-ExtB\",\"Raavi\",\"Rod\",\"Sakkal Majalla\",\"Segoe Print\",\"Segoe Script\",\"Segoe UI\",\"Segoe UI Light\",\"Segoe UI Semibold\",\"Segoe UI Symbol\",\"Shonar Bangla\",\"Shruti\",\"SimHei\",\"Simplified Arabic\",\"Simplified Arabic Fixed\",\"SimSun\",\"SimSun-ExtB\",\"Sylfaen\",\"Symbol\",\"Tahoma\",\"Times New Roman\",\"Times New Roman Baltic\",\"Times New Roman CE\",\"Times New Roman CYR\",\"Times New Roman Greek\",\"Times New Roman TUR\",\"Traditional Arabic\",\"Trebuchet MS\",\"Tunga\",\"Utsaah\",\"Vani\",\"Verdana\",\"Vijaya\",\"Vrinda\",\"Webdings\",\"Wingdings\"],\"serverInfo\":{\"ipAddress\":\"192.168.113.4\",\"username\":\"Jsmith\"},\"plugins\":[{\"filename\":\"npGoogleUpdate3.dll\",\"name\":\"Google Update\",\"description\":\"570bfb8bbc141986d034a1841f9346d2\"},{\"filename\":\"NPSWF32_19_0_0_226.dll\",\"name\":\"Shockwave Flash\",\"description\":\"62c3fe24efea04f0fd128f3434b1eb78\"}],\"osStyleColors\":{\"AppWorkspace\":\"rgb(171, 171, 171)\",\"GrayText\":\"rgb(109, 109, 109)\",\"Highlight\":\"rgb(51, 153, 255)\",\"ActiveBorder\":\"rgb(180, 180, 180)\",\"WindowText\":\"rgb(0, 0, 0)\",\"ThreeDLightShadow\":\"rgb(227, 227, 227)\",\"HighlightText\":\"rgb(255, 255, 255)\",\"MenuText\":\"rgb(0, 0, 0)\",\"ThreeDHighlight\":\"rgb(255, 255, 255)\",\"WindowFrame\":\"rgb(100, 100, 100)\",\"ThreeDDarkShadow\":\"rgb(105, 105, 105)\",\"InactiveCaption\":\"rgb(191, 205, 219)\",\"Menu\":\"rgb(240, 240, 240)\",\"ButtonHighlight\":\"rgb(255, 255, 255)\",\"Scrollbar\":\"rgb(200, 200, 200)\",\"Window\":\"rgb(255, 255, 255)\",\"InactiveBorder\":\"rgb(244, 247, 252)\",\"ActiveCaption\":\"rgb(153, 180, 209)\",\"Background\":\"rgb(0, 0, 0)\",\"ButtonFace\":\"rgb(240, 240, 240)\",\"CaptionText\":\"rgb(0, 0, 0)\",\"ButtonShadow\":\"rgb(160, 160, 160)\",\"ThreeDShadow\":\"rgb(160, 160, 160)\",\"InfoText\":\"rgb(0, 0, 0)\",\"ButtonText\":\"rgb(0, 0, 0)\",\"InactiveCaptionText\":\"rgb(67, 78, 84)\",\"ThreeDFace\":\"rgb(240, 240, 240)\",\"InfoBackground\":\"rgb(255, 255, 225)\"},\"jsFonts\":[],\"jsGeneralData\":{\"navigator_vendor\":\"\",\"operaVersion\":false,\"cookieEnabled\":true,\"CPU\":false,\"navigator_userLanguage\":false,\"SunJavaPlugin\":true,\"screen_deviceXDPI\":false,\"navigator_browserLanguage\":false,\"SVGViewer\":false,\"screen_height\":647,\"timezoneOffset\":300,\"AdobeShockwave\":false,\"Silverlight_supportedUserAgent\":false,\"browser_version\":\"44.0\",\"screen_logicalYDPI\":false,\"screen_width\":1008,\"RealPlayer\":false,\"adobeReader\":false,\"AjaxXMLParser\":\" via DOMParser\",\"dotNetFramework\":\"\",\"layout_version\":\"44.0\",\"isMozilla\":\"44.0\",\"screen_deviceYDPI\":false,\"RealOneComponents\":false,\"WebKit\":false,\"browser_name\":\"firefox\",\"screen_pixelDepth\":24,\"navigator_appMinorVersion\":false,\"flashVersion\":\"Shockwave Flash 19.0 r0\",\"WebKitNightlyBuild\":false,\"googleChromeVersion\":false,\"crypto_version\":false,\"screen_logicalXDPI\":false,\"WebKitBrowserName\":false,\"googleChromeLayout\":false,\"WebKitMobileDevice\":false,\"RealJukebox\":false,\"navigator_appCodeName\":\"Mozilla\",\"navigator_appVersion\":\"5.0 (Windows)\",\"screen_bufferDepth\":false,\"operaLayout\":false,\"window_offscreenBuffering\":false,\"screen_updateInterval\":false,\"MicrosoftScriptControl\":false,\"navigator_oscpu\":\"Windows NT 6.1\",\"geckoProduct\":\"Gecko 20100101\",\"navigator_securityPolicy\":false,\"navigator_systemLanguage\":false,\"XMLSerializer\":true,\"layout_name\":\"gecko\",\"isGoogleChrome\":false,\"RealOne\":false,\"screen_fontSmoothingEnabled\":false,\"navigator_userAgent\":\"Mozilla/5.0 (Windows NT 6.1; rv:44.0) Gecko/20100101 Firefox/44.0\",\"QuickTimePlayer\":false,\"isOpera\":false,\"google_gears\":false,\"activex\":false,\"navigator_platform\":\"Win32\",\"navigator_appName\":\"Netscape\",\"navigator_onLine\":true,\"document_defaultCharset\":false,\"geckoVendor\":false,\"RealPlayerG2\":false,\"os_name\":\"win\",\"XMLHttpRequest\":\" via XMLHttpRequest object\",\"navigator_language\":\"en-US\",\"screen_colorDepth\":24,\"IEVersion\":false,\"citrix\":false,\"openOfficePlugIn\":false}}";
        rest.queryParams(pathParams);
//        rest.body(body);
        Response response = rest.post();
        assertTrue(response.getStatusCode()==200);
    }

    @Test
    public void testPostWithPathParametersInUrl() throws Exception {
        rest = new REST(urlB, RequestType.Type.POST);
        Response response = rest.post();
        assertTrue(response.getStatusCode()==405);
    }

    @Test
    public void testPOSTConstructorURL() throws Exception {
        rest = new REST(urlA);
        Response response = rest.post();

    }

    @Test
    public void testMalformedURL() throws Exception {
        rest = new REST(urlC);
        Response response = rest.post();
        assertTrue("Response code:"+String.valueOf(response.getStatusCode()),response.getStatusCode()==200);
    }
}