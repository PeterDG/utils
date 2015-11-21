package sys;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

/**
 * Created by PEDRO GUTIERREZ on 27/08/2015.
 */
public class Init {
    public String rootPath;
    public String targetPath;
    public static Init instance;

    public static Init getInstance() {
        if (instance == null) {
            instance = new Init();
        }
        return instance;
    }

    public Init() {
        loadRoutes();
    }

    public void loadRoutes(){
        ClassPathResource contextPath = new ClassPathResource("");
        try {
            targetPath = contextPath.getFile().toString()+"\\";
        } catch (IOException e) {
            e.printStackTrace();
        }
        rootPath= targetPath.replaceAll("target+.*","");
    }
}
