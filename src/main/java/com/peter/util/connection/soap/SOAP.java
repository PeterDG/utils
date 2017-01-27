package com.peter.util.connection.soap;

import com.jayway.restassured.response.Response;
import com.peter.util.connection.REST;
import com.peter.util.connection.request.RequestType;
import com.peter.util.data.File;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Peter on 2/29/2016.
 */
public class SOAP implements RequestType {
    public REST rest;
    public String strXMLRequest;
    public XMLSoap xmlSoapResponse;
    public XMLSoap xmlSoapRequest;
    public ArrayList<SOAPParams> paramsList;
    public static String NULL_VALUE = "&lt;null&gt;";
    public static String SOAP_NULL_VALUE = "xsi:nil=\"true\" />";

    public SOAP(String url, String strXMLRequest) {
        this.strXMLRequest = strXMLRequest;
        this.rest = new REST(url);
    }

    public SOAP(String url, String strXMLTemplateRequest, ArrayList<SOAPParams> paramsList) {
        this.paramsList = paramsList;
        buildRequest(strXMLTemplateRequest, paramsList);
        this.rest = new REST(url);
    }

    public SOAP(String url, ArrayList<SOAPParams> paramsList, String xmlTemplateRequestPath) {
        this.paramsList = paramsList;
        String strXMLTemplateRequest = new File(xmlTemplateRequestPath).toString();
        buildRequest(strXMLTemplateRequest, paramsList);
        this.rest = new REST(url);
    }


    public XMLSoap send() {
        Map<String, String> headersMaps = new HashMap<String, String>() {{
            put("Content-Type", "text/xml");
        }};
        Response response = rest.post(headersMaps, strXMLRequest);
        String strResp = response.getBody().asString();
        xmlSoapResponse = new XMLSoap(strResp);
        return xmlSoapResponse;
    }

    public void buildRequest(String strXMLTemplateRequest, List<SOAPParams> soapParameters) {
        xmlSoapRequest = new XMLSoap(strXMLTemplateRequest);
        for (SOAPParams data : soapParameters) {
            String paramName = data.paramName;
            String paramValue = data.paramValue;
            xmlSoapRequest.setElement("//" + paramName, paramValue);
        }
        strXMLRequest = xmlSoapRequest.strXML;
        strXMLRequest = setNullParameter(strXMLRequest);
    }

    public String setNullParameter(String strXMLRequest) {
        strXMLRequest = strXMLRequest.replaceAll(">" + NULL_VALUE + "<", "");
        Pattern patt = Pattern.compile("<\\w+\\/\\w+>");
        Matcher m = patt.matcher(strXMLRequest);
        StringBuffer sb = new StringBuffer(strXMLRequest.length());
        while (m.find()) {
            String text = m.group();
            m.appendReplacement(sb, Matcher.quoteReplacement(text.split("/")[0] + " " + SOAP_NULL_VALUE));
        }
        m.appendTail(sb);
        return sb.toString();
    }

    @Override
    public String dispatch() {
        send();
        return xmlSoapResponse.strXML;
    }
}
