package com.peter.util.so;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * Created by Peter on 7/9/2016.
 */
public class LogPrinterThread extends Thread {
    private final logTypes log;
    public BufferedReader reader;
    public StringBuffer output;

    public enum logTypes {OUT, ERR}

    public LogPrinterThread(Process p, logTypes log, StringBuffer output) {
        this.log = log;
        this.output=output;
        switch (log) {
            case OUT:
                this.reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
                break;
            case ERR:
                this.reader = new BufferedReader(new InputStreamReader(p.getErrorStream()));
                break;
        }
    }

    public void run() {
        String line = "";
        try {
            while ((line = reader.readLine()) != null) {
                switch (log) {
                    case OUT:
                        System.out.println(line);
                        output.append(line+"\n");
                        break;
                    case ERR:
                        System.err.println(line);
                        output.append(line+"\n");
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
