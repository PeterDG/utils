package com.peter.util.data;

import org.dom4j.DocumentFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Peter on 2/29/2016.
 */
public class XMLTest {
    public XML xml;
    public String strXml;
    public String strXmlNoAttributes;
    public String strXpathTest;
    public String xmlSOAPRequest;
    public String xmlSOAPResponse;

    @Before
    public void before() {
        strXml = "<?xml version=\"1.0\"?>\n" +
                "<class>\n" +
                "   <student rollno=\"393\">\n" +
                "      <firstname>dinkar</firstname>\n" +
                "      <lastname>kad</lastname>\n" +
                "      <nickname>dinkar</nickname>\n" +
                "      <marks>85</marks>\n" +
                "   </student>\n" +
                "   <student rollno=\"493\">\n" +
                "      <firstname>Vaneet</firstname>\n" +
                "      <lastname>Gupta</lastname>\n" +
                "      <nickname>vinni</nickname>\n" +
                "      <marks>95</marks>\n" +
                "   </student>\n" +
                "   <student rollno=\"593\">\n" +
                "      <firstname>jasvir</firstname>\n" +
                "      <lastname>singn</lastname>\n" +
                "      <nickname>jazz</nickname>\n" +
                "      <marks>90</marks>\n" +
                "   </student>\n" +
                "</class>";
        strXmlNoAttributes = "<?xml version=\"1.0\"?>\n" +
                "<class>\n" +
                "   <student>\n" +
                "      <firstname>dinkar</firstname>\n" +
                "      <lastname>kad</lastname>\n" +
                "      <lastname>Gupta</lastname>\n" +
                "      <nickname>dinkar</nickname>\n" +
                "      <marks>85</marks>\n" +
                "   </student>\n" +
                "   <student>\n" +
                "      <firstname>Vaneet</firstname>\n" +
                "      <lastname>Gupta</lastname>\n" +
                "      <nickname>vinni</nickname>\n" +
                "      <marks>95</marks>\n" +
                "   </student>\n" +
                "   <student>\n" +
                "      <firstname>jasvir</firstname>\n" +
                "      <lastname>singn</lastname>\n" +
                "      <nickname>jazz</nickname>\n" +
                "      <marks>90</marks>\n" +
                "   </student>\n" +
                "</class>";
        xmlSOAPRequest=
                "<ris-_calculateRiskScore>\n"+
                "         <sharedKey>1234</sharedKey>\n" +
                "         <clientEnvironment>Environment</clientEnvironment>\n" +
                "</ris-_calculateRiskScore>\n";

    }


    @Test
    public void testGetNodes() throws Exception {
        xml = new XML(strXml);
        assertTrue(xml.getNodes("/class/student").size() > 0);
    }

    @Test
    public void testGetElement() throws Exception {
        xml = new XML(strXml);
        String result = xml.getElement("/class/student/firstname");
        assertTrue(result.equals("dinkar"));
    }

    @Test
    public void testGetNoExistentElement() throws Exception {
        xml = new XML(strXml);
        String result = xml.getElement("/class/student/noExist");
        assertTrue(result==null);
    }

    @Test
    public void testSetElement() throws Exception {
        xml = new XML(strXml);
        xml.setElement("/class/student/firstname", "NewName");
        String result = xml.getElement("/class/student/firstname");
        assertTrue(result.equals("NewName"));
    }

    @Test
    public void testSetElementCData() throws Exception {
        xml = new XML(xmlSOAPRequest);
        xml.setElement("/ris-_calculateRiskScore/clientEnvironment", "<![CDATA[NewName]]>");
        String result = xml.getElement("/ris-_calculateRiskScore/clientEnvironment");
        assertTrue(result.equals("NewName"));
    }

    @Test
    public void testSetElementB() throws Exception {
        xml = new XML(strXml);
        xml.setElement("/class/student[@rollno='493']/firstname", "<![CDATA[NewName]]>");
        String result = xml.getElement("/class/student[@rollno='493']/firstname");
        assertTrue(result.equals("NewName"));
    }

    @Test
    public void testSetElementC() throws Exception {
        xml = new XML(strXml);
        String test ="{\\n    \\\"documentFeatures\\\": {\\n        \\\"CSS-2.0\\\": true,\\n        \\\"CSS-4.0\\\": true,\\n        \\\"Traversal\\\": true,\\n        \\\"HTML\\\": true,\\n        \\\"Range-1.0\\\": true,\\n        \\\"XML-3.0\\\": true,\\n        \\\"Events-4.0\\\": true,\\n        \\\"MutationEvents-3.0\\\": true,\\n        \\\"Events-2.0\\\": true,\\n        \\\"MutationEvents-1.0\\\": true,\\n        \\\"Range-3.0\\\": true,\\n        \\\"HTML-3.0\\\": true,\\n        \\\"UIEvents\\\": true,\\n        \\\"UIEvents-1.0\\\": true,\\n        \\\"CSS2-3.0\\\": true,\\n        \\\"Views-1.0\\\": true,\\n        \\\"UIEvents-3.0\\\": true,\\n        \\\"Traversal-4.0\\\": true,\\n        \\\"Views-3.0\\\": true,\\n        \\\"CSS2-1.0\\\": true,\\n        \\\"Traversal-2.0\\\": true,\\n        \\\"Views\\\": true,\\n        \\\"HTMLEvents-1.0\\\": true,\\n        \\\"HTMLEvents-3.0\\\": true,\\n        \\\"StyleSheets-1.0\\\": true,\\n        \\\"StyleSheets-3.0\\\": true,\\n        \\\"MouseEvents-2.0\\\": true,\\n        \\\"Range\\\": true,\\n        \\\"MouseEvents-4.0\\\": true,\\n        \\\"MouseEvents\\\": true,\\n        \\\"CSS2\\\": true,\\n        \\\"HTMLEvents\\\": true,\\n        \\\"StyleSheets\\\": true,\\n        \\\"CSS-3.0\\\": true,\\n        \\\"CSS-1.0\\\": true,\\n        \\\"MutationEvents\\\": true,\\n        \\\"CSS2-4.0\\\": true,\\n        \\\"Range-4.0\\\": true,\\n        \\\"Events\\\": true,\\n        \\\"MutationEvents-4.0\\\": true,\\n        \\\"Range-2.0\\\": true,\\n        \\\"Events-3.0\\\": true,\\n        \\\"MutationEvents-2.0\\\": true,\\n        \\\"HTML-4.0\\\": true,\\n        \\\"Views-2.0\\\": true,\\n        \\\"CSS2-2.0\\\": true,\\n        \\\"UIEvents-2.0\\\": true,\\n        \\\"Traversal-3.0\\\": true,\\n        \\\"Views-4.0\\\": true,\\n        \\\"UIEvents-4.0\\\": true,\\n        \\\"Traversal-1.0\\\": true,\\n        \\\"CSS\\\": true,\\n        \\\"HTMLEvents-2.0\\\": true,\\n        \\\"HTMLEvents-4.0\\\": true,\\n        \\\"Events-1.0\\\": true,\\n        \\\"XML-4.0\\\": true,\\n        \\\"StyleSheets-2.0\\\": true,\\n        \\\"MouseEvents-1.0\\\": true,\\n        \\\"StyleSheets-4.0\\\": true,\\n        \\\"MouseEvents-3.0\\\": true,\\n        \\\"XML\\\": true\\n    },\\n    \\\"mimeTypes\\\": [\\n        {\\n            \\\"suffixes\\\": \\\"spl\\\",\\n            \\\"description\\\": \\\"FutureSplash movie\\\",\\n            \\\"type\\\": \\\"application/futuresplash\\\"\\n        },\\n        {\\n            \\\"suffixes\\\": \\\"swf\\\",\\n            \\\"description\\\": \\\"Adobe Flash movie\\\",\\n            \\\"type\\\": \\\"application/x-shockwave-flash\\\"\\n        },\\n        {\\n            \\\"suffixes\\\": \\\"None\\\",\\n            \\\"description\\\": \\\"Unknown type\\\",\\n            \\\"type\\\": \\\"application/x-vnd.google.oneclickctrl.9\\\"\\n        },\\n        {\\n            \\\"suffixes\\\": \\\"None\\\",\\n            \\\"description\\\": \\\"Unknown type\\\",\\n            \\\"type\\\": \\\"application/x-vnd.google.update3webcontrol.3\\\"\\n        }\\n    ],\\n    \\\"canvas\\\": \\\"2b400453fa8aa3a5436e22c20db09752620fc0c9ccbd7240b96ee18de511812150e015d419c832f969cb8b9cb4acec4020ba7b38dbc7b1e04d55937af3b23db2####\\\",\\n    \\\"flashVars\\\": {\\n        \\\"fl.isEmbeddedInAcrobat\\\": \\\"false\\\",\\n        \\\"fl.os\\\": \\\"Windows 7\\\",\\n        \\\"fl.screenDPI\\\": \\\"72\\\",\\n        \\\"fl.maxLevelIDC\\\": \\\"5.1\\\",\\n        \\\"fl.timeZoneOffset\\\": \\\"UTC-300\\\",\\n        \\\"fl.screenResolutionX\\\": \\\"1008\\\",\\n        \\\"fl.hasPrinting\\\": \\\"true\\\",\\n        \\\"fl.screenResolutionY\\\": \\\"647\\\",\\n        \\\"fl.hasIME\\\": \\\"true\\\",\\n        \\\"fl.playerType\\\": \\\"PlugIn\\\",\\n        \\\"fl.hasVideoEncoder\\\": \\\"true\\\",\\n        \\\"fl.isDebugger\\\": \\\"false\\\",\\n        \\\"fl.hasAudioEncoder\\\": \\\"true\\\",\\n        \\\"fl.hasTLS\\\": \\\"true\\\",\\n        \\\"fl.hasMP3\\\": \\\"true\\\",\\n        \\\"fl.hasAccessibility\\\": \\\"true\\\",\\n        \\\"fl.screenColor\\\": \\\"color\\\",\\n        \\\"fl.manufacturer\\\": \\\"Adobe Windows\\\",\\n        \\\"fl.hasAudio\\\": \\\"true\\\",\\n        \\\"fl.cpuArchitecture\\\": \\\"x86\\\",\\n        \\\"fl.hasScreenPlayback\\\": \\\"false\\\",\\n        \\\"fl._internal\\\": \\\"5\\\",\\n        \\\"fl.touchscreenType\\\": \\\"none\\\",\\n        \\\"fl.avHardwareDisable\\\": \\\"false\\\",\\n        \\\"fl.hasStreamingAudio\\\": \\\"true\\\",\\n        \\\"fl.hasScreenBroadcast\\\": \\\"false\\\",\\n        \\\"fl.supports64BitProcesses\\\": \\\"false\\\",\\n        \\\"fl.hasEmbeddedVideo\\\": \\\"true\\\",\\n        \\\"fl.hasStreamingVideo\\\": \\\"true\\\",\\n        \\\"fl.pixelAspectRatio\\\": \\\"1\\\",\\n        \\\"fl.supports32BitProcesses\\\": \\\"true\\\",\\n        \\\"fl.language\\\": \\\"es\\\",\\n        \\\"fl.localFileReadDisable\\\": \\\"false\\\",\\n        \\\"fl.serverString\\\": \\\"A=t&SA=t&SV=t&EV=t&MP3=t&AE=t&VE=t&ACC=t&PR=t&SP=f&SB=f&DEB=f&V=WIN%2019%2C0%2C0%2C226&M=Adobe%20Windows&R=1008x647&COL=color&AR=1.0&OS=Windows%207&ARCH=x86&L=es&IME=t&PR32=t&PR64=f&PT=PlugIn&AVD=f&LFD=f&WD=f&TLS=t&ML=5.1&DP=72\\\",\\n        \\\"fl.version\\\": \\\"WIN 19,0,0,226\\\"\\n    },\\n    \\\"flashFonts\\\": [\\n        \\\"Aharoni\\\",\\n        \\\"Andalus\\\",\\n        \\\"Angsana New\\\",\\n        \\\"AngsanaUPC\\\",\\n        \\\"Aparajita\\\",\\n        \\\"Arabic Transparent\\\",\\n        \\\"Arabic Typesetting\\\",\\n        \\\"Arial\\\",\\n        \\\"Arial Baltic\\\",\\n        \\\"Arial Black\\\",\\n        \\\"Arial CE\\\",\\n        \\\"Arial CYR\\\",\\n        \\\"Arial Greek\\\",\\n        \\\"Arial TUR\\\",\\n        \\\"Batang\\\",\\n        \\\"BatangChe\\\",\\n        \\\"Browallia New\\\",\\n        \\\"BrowalliaUPC\\\",\\n        \\\"Calibri\\\",\\n        \\\"Cambria\\\",\\n        \\\"Cambria Math\\\",\\n        \\\"Candara\\\",\\n        \\\"Comic Sans MS\\\",\\n        \\\"Consolas\\\",\\n        \\\"Constantia\\\",\\n        \\\"Corbel\\\",\\n        \\\"Cordia New\\\",\\n        \\\"CordiaUPC\\\",\\n        \\\"Courier New\\\",\\n        \\\"Courier New Baltic\\\",\\n        \\\"Courier New CE\\\",\\n        \\\"Courier New CYR\\\",\\n        \\\"Courier New Greek\\\",\\n        \\\"Courier New TUR\\\",\\n        \\\"DaunPenh\\\",\\n        \\\"David\\\",\\n        \\\"DFKai-SB\\\",\\n        \\\"DilleniaUPC\\\",\\n        \\\"DokChampa\\\",\\n        \\\"Dotum\\\",\\n        \\\"DotumChe\\\",\\n        \\\"Ebrima\\\",\\n        \\\"Estrangelo Edessa\\\",\\n        \\\"EucrosiaUPC\\\",\\n        \\\"Euphemia\\\",\\n        \\\"FangSong\\\",\\n        \\\"Franklin Gothic Medium\\\",\\n        \\\"FrankRuehl\\\",\\n        \\\"FreesiaUPC\\\",\\n        \\\"Gabriola\\\",\\n        \\\"Gautami\\\",\\n        \\\"Georgia\\\",\\n        \\\"Gisha\\\",\\n        \\\"Gulim\\\",\\n        \\\"GulimChe\\\",\\n        \\\"Gungsuh\\\",\\n        \\\"GungsuhChe\\\",\\n        \\\"Impact\\\",\\n        \\\"IrisUPC\\\",\\n        \\\"Iskoola Pota\\\",\\n        \\\"JasmineUPC\\\",\\n        \\\"KaiTi\\\",\\n        \\\"Kalinga\\\",\\n        \\\"Kartika\\\",\\n        \\\"Khmer UI\\\",\\n        \\\"KodchiangUPC\\\",\\n        \\\"Kokila\\\",\\n        \\\"Lao UI\\\",\\n        \\\"Latha\\\",\\n        \\\"Leelawadee\\\",\\n        \\\"Levenim MT\\\",\\n        \\\"LilyUPC\\\",\\n        \\\"Lucida Console\\\",\\n        \\\"Lucida Sans Unicode\\\",\\n        \\\"Malgun Gothic\\\",\\n        \\\"Mangal\\\",\\n        \\\"Marlett\\\",\\n        \\\"Meiryo\\\",\\n        \\\"Meiryo UI\\\",\\n        \\\"Microsoft Himalaya\\\",\\n        \\\"Microsoft JhengHei\\\",\\n        \\\"Microsoft New Tai Lue\\\",\\n        \\\"Microsoft PhagsPa\\\",\\n        \\\"Microsoft Sans Serif\\\",\\n        \\\"Microsoft Tai Le\\\",\\n        \\\"Microsoft Uighur\\\",\\n        \\\"Microsoft YaHei\\\",\\n        \\\"Microsoft Yi Baiti\\\",\\n        \\\"MingLiU\\\",\\n        \\\"MingLiU-ExtB\\\",\\n        \\\"MingLiU_HKSCS\\\",\\n        \\\"MingLiU_HKSCS-ExtB\\\",\\n        \\\"Miriam\\\",\\n        \\\"Miriam Fixed\\\",\\n        \\\"Mongolian Baiti\\\",\\n        \\\"MoolBoran\\\",\\n        \\\"MS Gothic\\\",\\n        \\\"MS Mincho\\\",\\n        \\\"MS PGothic\\\",\\n        \\\"MS PMincho\\\",\\n        \\\"MS UI Gothic\\\",\\n        \\\"MV Boli\\\",\\n        \\\"Narkisim\\\",\\n        \\\"NSimSun\\\",\\n        \\\"Nyala\\\",\\n        \\\"Palatino Linotype\\\",\\n        \\\"Plantagenet Cherokee\\\",\\n        \\\"PMingLiU\\\",\\n        \\\"PMingLiU-ExtB\\\",\\n        \\\"Raavi\\\",\\n        \\\"Rod\\\",\\n        \\\"Sakkal Majalla\\\",\\n        \\\"Segoe Print\\\",\\n        \\\"Segoe Script\\\",\\n        \\\"Segoe UI\\\",\\n        \\\"Segoe UI Light\\\",\\n        \\\"Segoe UI Semibold\\\",\\n        \\\"Segoe UI Symbol\\\",\\n        \\\"Shonar Bangla\\\",\\n        \\\"Shruti\\\",\\n        \\\"SimHei\\\",\\n        \\\"Simplified Arabic\\\",\\n        \\\"Simplified Arabic Fixed\\\",\\n        \\\"SimSun\\\",\\n        \\\"SimSun-ExtB\\\",\\n        \\\"Sylfaen\\\",\\n        \\\"Symbol\\\",\\n        \\\"Tahoma\\\",\\n        \\\"Times New Roman\\\",\\n        \\\"Times New Roman Baltic\\\",\\n        \\\"Times New Roman CE\\\",\\n        \\\"Times New Roman CYR\\\",\\n        \\\"Times New Roman Greek\\\",\\n        \\\"Times New Roman TUR\\\",\\n        \\\"Traditional Arabic\\\",\\n        \\\"Trebuchet MS\\\",\\n        \\\"Tunga\\\",\\n        \\\"Utsaah\\\",\\n        \\\"Vani\\\",\\n        \\\"Verdana\\\",\\n        \\\"Vijaya\\\",\\n        \\\"Vrinda\\\",\\n        \\\"Webdings\\\",\\n        \\\"Wingdings\\\"\\n    ],\\n    \\\"serverInfo\\\": {\\n        \\\"ipAddress\\\": \\\"192.168.113.4\\\",\\n        \\\"username\\\": \\\"testUser\\\"\\n    },\\n    \\\"plugins\\\": [\\n        {\\n            \\\"filename\\\": \\\"npGoogleUpdate3.dll\\\",\\n            \\\"name\\\": \\\"Google Update\\\",\\n            \\\"description\\\": \\\"570bfb8bbc141986d034a1841f9346d2\\\"\\n        },\\n        {\\n            \\\"filename\\\": \\\"NPSWF32_19_0_0_226.dll\\\",\\n            \\\"name\\\": \\\"Shockwave Flash\\\",\\n            \\\"description\\\": \\\"62c3fe24efea04f0fd128f3434b1eb78\\\"\\n        }\\n    ],\\n    \\\"osStyleColors\\\": {\\n        \\\"AppWorkspace\\\": \\\"rgb(171, 171, 171)\\\",\\n        \\\"GrayText\\\": \\\"rgb(109, 109, 109)\\\",\\n        \\\"Highlight\\\": \\\"rgb(51, 153, 255)\\\",\\n        \\\"ActiveBorder\\\": \\\"rgb(180, 180, 180)\\\",\\n        \\\"WindowText\\\": \\\"rgb(0, 0, 0)\\\",\\n        \\\"ThreeDLightShadow\\\": \\\"rgb(227, 227, 227)\\\",\\n        \\\"HighlightText\\\": \\\"rgb(255, 255, 255)\\\",\\n        \\\"MenuText\\\": \\\"rgb(0, 0, 0)\\\",\\n        \\\"ThreeDHighlight\\\": \\\"rgb(255, 255, 255)\\\",\\n        \\\"WindowFrame\\\": \\\"rgb(100, 100, 100)\\\",\\n        \\\"ThreeDDarkShadow\\\": \\\"rgb(105, 105, 105)\\\",\\n        \\\"InactiveCaption\\\": \\\"rgb(191, 205, 219)\\\",\\n        \\\"Menu\\\": \\\"rgb(240, 240, 240)\\\",\\n        \\\"ButtonHighlight\\\": \\\"rgb(255, 255, 255)\\\",\\n        \\\"Scrollbar\\\": \\\"rgb(200, 200, 200)\\\",\\n        \\\"Window\\\": \\\"rgb(255, 255, 255)\\\",\\n        \\\"InactiveBorder\\\": \\\"rgb(244, 247, 252)\\\",\\n        \\\"ActiveCaption\\\": \\\"rgb(153, 180, 209)\\\",\\n        \\\"Background\\\": \\\"rgb(0, 0, 0)\\\",\\n        \\\"ButtonFace\\\": \\\"rgb(240, 240, 240)\\\",\\n        \\\"CaptionText\\\": \\\"rgb(0, 0, 0)\\\",\\n        \\\"ButtonShadow\\\": \\\"rgb(160, 160, 160)\\\",\\n        \\\"ThreeDShadow\\\": \\\"rgb(160, 160, 160)\\\",\\n        \\\"InfoText\\\": \\\"rgb(0, 0, 0)\\\",\\n        \\\"ButtonText\\\": \\\"rgb(0, 0, 0)\\\",\\n        \\\"InactiveCaptionText\\\": \\\"rgb(67, 78, 84)\\\",\\n        \\\"ThreeDFace\\\": \\\"rgb(240, 240, 240)\\\",\\n        \\\"InfoBackground\\\": \\\"rgb(255, 255, 225)\\\"\\n    },\\n    \\\"jsFonts\\\": [\\n        \\n    ],\\n    \\\"jsGeneralData\\\": {\\n        \\\"navigator_vendor\\\": \\\"\\\",\\n        \\\"operaVersion\\\": false,\\n        \\\"cookieEnabled\\\": true,\\n        \\\"CPU\\\": false,\\n        \\\"navigator_userLanguage\\\": false,\\n        \\\"SunJavaPlugin\\\": true,\\n        \\\"screen_deviceXDPI\\\": false,\\n        \\\"navigator_browserLanguage\\\": false,\\n        \\\"SVGViewer\\\": false,\\n        \\\"screen_height\\\": 647,\\n        \\\"timezoneOffset\\\": 300,\\n        \\\"AdobeShockwave\\\": false,\\n        \\\"Silverlight_supportedUserAgent\\\": false,\\n        \\\"browser_version\\\": \\\"44.0\\\",\\n        \\\"screen_logicalYDPI\\\": false,\\n        \\\"screen_width\\\": 1008,\\n        \\\"RealPlayer\\\": false,\\n        \\\"adobeReader\\\": false,\\n        \\\"AjaxXMLParser\\\": \\\" via DOMParser\\\",\\n        \\\"dotNetFramework\\\": \\\"\\\",\\n        \\\"layout_version\\\": \\\"44.0\\\",\\n        \\\"isMozilla\\\": \\\"44.0\\\",\\n        \\\"screen_deviceYDPI\\\": false,\\n        \\\"RealOneComponents\\\": false,\\n        \\\"WebKit\\\": false,\\n        \\\"browser_name\\\": \\\"firefox\\\",\\n        \\\"screen_pixelDepth\\\": 24,\\n        \\\"navigator_appMinorVersion\\\": false,\\n        \\\"flashVersion\\\": \\\"Shockwave Flash 19.0 r0\\\",\\n        \\\"WebKitNightlyBuild\\\": false,\\n        \\\"googleChromeVersion\\\": false,\\n        \\\"crypto_version\\\": false,\\n        \\\"screen_logicalXDPI\\\": false,\\n        \\\"WebKitBrowserName\\\": false,\\n        \\\"googleChromeLayout\\\": false,\\n        \\\"WebKitMobileDevice\\\": false,\\n        \\\"RealJukebox\\\": false,\\n        \\\"navigator_appCodeName\\\": \\\"Mozilla\\\",\\n        \\\"navigator_appVersion\\\": \\\"5.0 (Windows)\\\",\\n        \\\"screen_bufferDepth\\\": false,\\n        \\\"operaLayout\\\": false,\\n        \\\"window_offscreenBuffering\\\": false,\\n        \\\"screen_updateInterval\\\": false,\\n        \\\"MicrosoftScriptControl\\\": false,\\n        \\\"navigator_oscpu\\\": \\\"Windows NT 6.1\\\",\\n        \\\"geckoProduct\\\": \\\"Gecko 20100101\\\",\\n        \\\"navigator_securityPolicy\\\": false,\\n        \\\"navigator_systemLanguage\\\": false,\\n        \\\"XMLSerializer\\\": true,\\n        \\\"layout_name\\\": \\\"gecko\\\",\\n        \\\"isGoogleChrome\\\": false,\\n        \\\"RealOne\\\": false,\\n        \\\"screen_fontSmoothingEnabled\\\": false,\\n        \\\"navigator_userAgent\\\": \\\"Mozilla/5.0 (Windows NT 6.1; rv:44.0) Gecko/20100101 Firefox/44.0\\\",\\n        \\\"QuickTimePlayer\\\": false,\\n        \\\"isOpera\\\": false,\\n        \\\"google_gears\\\": false,\\n        \\\"activex\\\": false,\\n        \\\"navigator_platform\\\": \\\"Win32\\\",\\n        \\\"navigator_appName\\\": \\\"Netscape\\\",\\n        \\\"navigator_onLine\\\": true,\\n        \\\"document_defaultCharset\\\": false,\\n        \\\"geckoVendor\\\": false,\\n        \\\"RealPlayerG2\\\": false,\\n        \\\"os_name\\\": \\\"win\\\",\\n        \\\"XMLHttpRequest\\\": \\\" via XMLHttpRequest object\\\",\\n        \\\"navigator_language\\\": \\\"en-US\\\",\\n        \\\"screen_colorDepth\\\": 24,\\n        \\\"IEVersion\\\": false,\\n        \\\"citrix\\\": false,\\n        \\\"openOfficePlugIn\\\": false\\n    }\\n}]]";
        xml.setElement("/class/student[@rollno='493']/firstname", DocumentFactory.getInstance().createCDATA(test).asXML());
        String result = xml.getElement("/class/student[@rollno='493']/firstname");
        assertTrue(result.equals(test));
    }

    @Test
    public void testToString() throws Exception {
        xml = new XML(strXml);
        xml.setElement("/class/student[@rollno='493']/firstname", "NewName");
        String strXmlMod = xml.toString();
        XML xmlMod = new XML(strXmlMod);
        String result = xmlMod.getElement("/class/student[@rollno='493']/firstname");
        assertTrue(result.equals("NewName"));
    }

    @Test
    public void testFindXpath() throws Exception {
        xml = new XML(strXml);
        String result = xml.getElement("//firstname");
        assertTrue(result.equals("dinkar"));
    }

    @Test
    public void testFindXpathSecondElement() throws Exception {
        xml = new XML(strXml);
        String result = xml.getElement("//student[@rollno='493']/firstname");
        assertTrue(result.equals("Vaneet"));
    }
}