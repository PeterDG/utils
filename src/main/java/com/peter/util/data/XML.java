package com.peter.util.data;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

/**
 * Created by Peter on 2/29/2016.
 */
public class XML {
    public SAXReader reader;
    public Document xml;

    public XML(String strXML) {
        reader = new SAXReader();
        try {
            xml = reader.read(new StringReader(strXML));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public List getNodes(String xPathExpression) {
        List nodes = xml.selectNodes(xPathExpression);
        return nodes;
    }

    public String getElement(String xPathExpression) {
        String nodes = xml.selectSingleNode(xPathExpression).getText();
        return nodes;
    }

    public void setElement(String xPathExpression, String value) {
        xml.selectSingleNode(xPathExpression).setText(value);
    }

    public String toString(){
        return prettyPrint(xml);
    }

    private String prettyPrint( Document document ) {
        String strDocument = null;
        try {
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding(document.getXMLEncoding());
            StringWriter stringWriter = new StringWriter();
            XMLWriter writer = new XMLWriter( stringWriter, format );
            // XMLWriter has a bug that is avoided if we reparse the document
            // prior to calling XMLWriter.write()
            writer.write(DocumentHelper.parseText(document.asXML()));
            writer.close();
            strDocument = stringWriter.toString();
            strDocument =strDocument.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n","");
        }
        catch ( Exception e ){
            e.printStackTrace();
            return( null );
        }
        return( strDocument );
    }

}