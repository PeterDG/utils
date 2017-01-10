package com.peter.util.connection;

import com.peter.util.connection.request.RequestType;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Peter on 1/10/2017.
 */
import static com.peter.util.connection.request.RequestType.Type;
/**
 * Created by Peter on 1/10/2017.
 */
public class HTTP {

    public static Integer TIMEOUT_CONNECTION = 60;

    public static String connect(String url, Type requestMethod, Map<String, String> properties, String raw) {
        String result = null;
        try {
            URL u = new URL(url);
            HttpURLConnection con = (HttpURLConnection) u.openConnection();
            con.setRequestMethod(requestMethod.toString());
            for (Map.Entry property : properties.entrySet())
                con.setRequestProperty(property.getKey().toString(), property.getValue().toString());
            con.setRequestProperty("Content-type", "text/xml; charset=utf-8");
            con.setRequestProperty("SOAPAction", url);
//            con.setConnectTimeout(TIMEOUT_CONNECTION);
            OutputStream reqStream = con.getOutputStream();
            reqStream.write(raw.getBytes());
            reqStream.flush();
            reqStream.close();
            int responseCode = con.getResponseCode();
            if (responseCode == con.HTTP_OK) {
                InputStream in = new BufferedInputStream(con.getInputStream());
                result = new String(readBytes(in));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String connect(Type requestMethod,final String url, String raw) {
        HashMap<String, String> properties = new HashMap();
        switch(requestMethod){
            case SOAP:
                properties = new HashMap() {{
                    put("SOAPAction", url);
                    put("Content-type", "text/xml; charset=utf-8");
                }};
                break;
            case GET:
                break;
            case POST:
                break;
            case PUT:
                break;
        }
        String response = connect(url, RequestType.Type.POST, properties, raw);
        return response;
    }

    private static byte[] readBytes(InputStream inputStream) throws IOException {
        // this dynamically extends to take the bytes you read
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();

        // this is storage overwritten on each iteration with bytes
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        // we need to know how may bytes were read to write them to the
        // byteBuffer
        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }

        // and then we can return your byte array.
        return byteBuffer.toByteArray();
    }
}
