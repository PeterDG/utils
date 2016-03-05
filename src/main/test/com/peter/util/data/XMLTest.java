package com.peter.util.data;

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
    public String xmlSOAPResponse;
    public String xmlSOAPResponse2;

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
    public void testSetElement() throws Exception {
        xml = new XML(strXml);
        xml.setElement("/class/student/firstname", "NewName");
        String result = xml.getElement("/class/student/firstname");
        assertTrue(result.equals("NewName"));
    }

    @Test
    public void testSetElementB() throws Exception {
        xml = new XML(strXml);
        xml.setElement("/class/student[@rollno='493']/firstname", "NewName");
        String result = xml.getElement("/class/student[@rollno='493']/firstname");
        assertTrue(result.equals("NewName"));
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