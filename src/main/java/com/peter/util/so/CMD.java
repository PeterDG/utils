package com.peter.util.so;

/**
 * Created by Peter on 6/23/2015.
 */

import java.io.*;
import java.util.List;

public class CMD {

    public static String execute(String cmdLine) {
        StringBuffer output = new StringBuffer();
        Process p;
        try {
            p = Runtime.getRuntime().exec(cmdLine);
            p.waitFor();
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = "";
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return output.toString();
    }

    public static String execute(List<String> cmdLines, String adminLnkPath) {
        String result;
        File file = new File(adminLnkPath);
        String rootPath = file.getParent()+"/";
        String resource = file.getName();
        com.peter.util.data.File bat = new com.peter.util.data.File(rootPath, "cmds.bat");
        bat.writeLines(cmdLines, true);
        result = execute("cmd /c start "+rootPath + resource+" \""+bat.getPath()+"\"");
        return result;
    }

}