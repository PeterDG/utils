package com.peter.util.data;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.peter.util.sys.Environment;

import static org.junit.Assert.*;

/**
 * Created by Peter on 3/4/2016.
 */
public class FileTest {
    public String path;
    public Environment environment;
    public File file;

    @Before
    public void setUp() throws Exception {
        environment = new Environment();

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testToString() throws Exception {
        path = environment.rootPath + "src\\main\\resources\\db\\scripts\\postgres\\template_createDB.sql";
        file = new File(path);
        String strFile = file.toString();
        String hoppedStr="CREATE DATABASE \"p_dbName\" WITH OWNER = p_dbOwner";
        assertTrue(strFile.equals(hoppedStr));
    }

    @Test
    public void testToStringMultiline() throws Exception {
        path = environment.rootPath + "src\\main\\resources\\templates\\WSRiskScoringService.xsl";
        file = new File(path);
        String strFile = file.toString().replaceAll("[\r\n]+","");
        String hoppedStr="<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ris=\"http://soap.easysol.net/detect/riskScoringService\">\r\n" +
                "<soapenv:Body>\r\n" +
                "    <ris:calculateRiskScore>\r\n" +
                "        <sharedKey>1234</sharedKey>\r\n" +
                "        <clientEnvironment>Environmet</clientEnvironment>\n" +
                "    </ris:calculateRiskScore>\r\n" +
                "</soapenv:Body>\r\n" +
                "</soapenv:Envelope>";
        assertTrue(strFile.equals(hoppedStr.replaceAll("[\r\n]+","")));
    }

    @Test
    public void testReplaceTextLists() throws Exception {
        path = environment.rootPath + "src\\main\\resources\\db\\scripts\\postgres\\template_createDB.sql";
        file = new File(path);
        File copy = file.copy(file.getDirectory() + "/" + "copy.sql");
        copy.removeLastCharacter();
        String hoppedStr="CREATE DATABASE \"p_dbName\" WITH OWNER = p_dbOwne";
        String receivedStr = copy.toString();
        copy.delete();
        assertTrue(receivedStr.equals(hoppedStr));
    }

    @Test
    public void testCopyDelete() throws Exception {
        path = environment.rootPath + "src\\main\\resources\\db\\scripts\\postgres\\template_createDB.sql";
        file = new File(path);
        File copy = file.copy(file.getDirectory() + "/" + "copy.sql");
        boolean createdCopy =copy.exist();
        copy.delete();
        assertTrue(createdCopy && !copy.exist());
    }

    @Test
    public void testGetListFilesOfDirectory() throws Exception {
      File.getListFilesOfDirectory("E:\\Development\\Projects\\utils\\src\\main\\java\\com\\peter\\util\\connection");
    }
}