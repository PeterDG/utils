package com.peter.util.so;

import com.peter.util.time.Time;
import org.junit.Before;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Peter on 5/12/2016.
 */
public class SOUtils {
    public static int waitTime=500;
    private static String OS = System.getProperty("os.name").toLowerCase();

    public static void setSODate(String date, String adminLnkPath){
        List<String> cmdLines = Arrays.asList("date "+date, "exit");
         CMD.execute(cmdLines,adminLnkPath);
         sleep(waitTime);
    }
    public static void setSOHour(String hour, String adminLnkPath){
        List<String> cmdLines = Arrays.asList("time "+hour, "exit");
        CMD.execute(cmdLines,adminLnkPath);
        sleep(waitTime);
    }

    public static void syncTime(String adminLnkPath){
        List<String> cmdLines = Arrays.asList("w32tm /resync", "exit");
        CMD.execute(cmdLines,adminLnkPath);
    }

    public static void setSOTime(String date,String hour, String adminLnkPath){
        List<String> cmdLines = Arrays.asList("date "+date,"time "+hour, "exit");
        CMD.execute(cmdLines,adminLnkPath);
        sleep(waitTime);
    }

    public static void setSOTime(String time, String adminLnkPath){
        String[] splitTime = time.split(" ");
        String[] splitDate = splitTime[0].split("-");
        String day=splitDate[2];
        String month=splitDate[1];
        String year=splitDate[0].substring(2,4);
        setSOTime(month+"-"+day+"-"+year,splitTime[1],adminLnkPath);
    }

    public static void sleep(int milisecond){
        try {
            Thread.sleep(milisecond);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static boolean isWindows() {
        return (OS.indexOf("win") >= 0);
    }

    public static boolean isMac() {
        return (OS.indexOf("mac") >= 0);
    }

    public static boolean isUnix() {
        return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );
    }

    public static boolean isSolaris() {
        return (OS.indexOf("sunos") >= 0);
    }
    public static String getOS(){
         return OS;
    }


}
