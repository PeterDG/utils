package util;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;

/**
 * Created by PEDRO GUTIERREZ on 26/08/2015.
 */
public class Parameters {

    public static String accessPointHost;
    public static String accessPointPort;
    public static String accessPointToken;
    public static String pricesService;
    public static String queryInstruments;



    public Parameters() {
        ClassPathResource classPathResource = new ClassPathResource("/configuration.properties");
        load(classPathResource);
    }

    public void load(ClassPathResource classPathResource){
        try {
           this.accessPointHost= PropertiesLoaderUtils.loadProperties(classPathResource).getProperty("accessPoint.host");
           this.accessPointPort= PropertiesLoaderUtils.loadProperties(classPathResource).getProperty("accessPoint.port");
           this.accessPointToken= PropertiesLoaderUtils.loadProperties(classPathResource).getProperty("accessPoint.token");
           this.pricesService= PropertiesLoaderUtils.loadProperties(classPathResource).getProperty("services.prices");
           this.queryInstruments= PropertiesLoaderUtils.loadProperties(classPathResource).getProperty("query.instruments");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
