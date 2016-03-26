package sys;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

/**
 * Created by PEDRO GUTIERREZ on 27/08/2015.
 */
public class Environment {
    public String rootPath;
    public String targetPath;
    public String resourcesPath;
    public static Environment instance;

    public static Environment getInstance() {
        if (instance == null) {
            instance = new Environment();
        }
        return instance;
    }

    public Environment() {
        loadRoutes();
    }

    public void loadRoutes(){
        ClassPathResource contextPath = new ClassPathResource("");
        try {
            this.targetPath = (contextPath.getFile().toString()+"/").replace("\\", "/");
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.rootPath= targetPath.replaceAll("target+.*","");
        String resourcesRelativePath ="";
        if (!targetPath.equals(rootPath)) resourcesRelativePath = "/src/main/resources/";
        this.resourcesPath = rootPath + resourcesRelativePath;
    }
}
