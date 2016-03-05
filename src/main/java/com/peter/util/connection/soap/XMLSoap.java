package com.peter.util.connection.soap;

import com.peter.util.data.XML;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Peter on 2/29/2016.
 */
public class XMLSoap {
    public String strXML;
    public XML xml;
    public int replacePosition;
    public String replaceTag;
    public String replace2Points = "_-";

    public XMLSoap(String strXML) {
        this.strXML = strXML;
        strSoap2Xml();
    }

    public void findReplacePosition() {
        replacePosition = 0;
        Pattern pattern = Pattern.compile("xmlns:[\\w\\-_]+=\"http://[\\w_\\-%]*\\.?[\\w_\\-%]+\\.[\\w_\\-%]+(/[\\w_\\-%]+)*\"");
        Matcher matcher = pattern.matcher(strXML);
        while (matcher.find())
            replacePosition = matcher.end();
    }

    public void findReplaceTag() {
        Pattern pattern = Pattern.compile("<(.*?)>");
        Matcher matcher = pattern.matcher(strXML);
        matcher.find(replacePosition);
        replaceTag = matcher.group(0).replaceAll("<|>", "");
    }

    public String replaceTag(String str) {
        Pattern pattern = Pattern.compile("<" + replaceTag + ">");
        Matcher matcher = pattern.matcher(strXML);
        matcher.find();
        int startPos = matcher.start();
        pattern = Pattern.compile("</" + replaceTag + ">");
        matcher = pattern.matcher(strXML);
        matcher.find();
        int endPos = matcher.end();
        String strA = strXML.substring(0, startPos);
        String strB = strXML.substring(endPos, strXML.length());
        return strA + str + strB;
    }

    public String getTag(String str) {
        Pattern pattern = Pattern.compile("<" + replaceTag + ">");
        Matcher matcher = pattern.matcher(str);
        matcher.find();
        int startPos = matcher.start();
        pattern = Pattern.compile("</" + replaceTag + ">");
        matcher = pattern.matcher(str);
        matcher.find();
        int endPos = matcher.end();
        return str.substring(startPos, endPos);
    }

    private void strSoap2Xml() {
        findReplacePosition();
        findReplaceTag();
        String strXml = getTag(strXML);
        strXml = strXml.replace(":", replace2Points);
        this.xml = new XML(strXml);
    }

    public void xml2strSoap() {
        String str = this.xml.toString();
        str = str.replace(replace2Points, ":");
        strXML = replaceTag(str);
    }

    public String toString() {
        xml2strSoap();
        return strXML;
    }

    public void setElement(String xPathExpression, String value){
        xml.setElement(xPathExpression,value);
        xml2strSoap();
    }

    public String getElement(String xPathExpression){
        return xml.getElement(xPathExpression);
    }




}
