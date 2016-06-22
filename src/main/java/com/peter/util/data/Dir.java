package com.peter.util.data;
import java.io.File;
/**
 * Created by Peter on 6/22/2016.
 */
public class Dir {

    public static boolean deleteDirectory(File path) {
        if( path.exists() ) {
            File[] files = path.listFiles();
            for(int i=0; i<files.length; i++) {
                if(files[i].isDirectory()) {
                    deleteDirectory(files[i]);
                }
                else {
                    files[i].delete();
                }
            }
        }
        return( path.delete() );
    }

    public static boolean deleteDirectory(String path) {
        File file = new File(path);
        return deleteDirectory(file);
    }
}
