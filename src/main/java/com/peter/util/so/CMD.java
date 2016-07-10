package com.peter.util.so;

/**
 * Created by Peter on 6/23/2015.
 */

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.List;

public class CMD {

    public static String execute(String cmdLine) {
        StringBuffer output = new StringBuffer();
        Process p;
        try {
            p = Runtime.getRuntime().exec(cmdLine);
            LogPrinterThread outLog = new LogPrinterThread(p, LogPrinterThread.logTypes.OUT,output);
            LogPrinterThread errLog = new LogPrinterThread(p, LogPrinterThread.logTypes.ERR,output);
            errLog.start();
            outLog.start();
            p.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return output.toString();
    }

    public static String execute(List<String> cmdLines, String adminLnkPath) {
        String result;
        File file = new File(adminLnkPath);
        String rootPath = file.getParent() + "/";
        String resource = file.getName();
        com.peter.util.data.File bat = new com.peter.util.data.File(rootPath, "cmds.bat");
        bat.writeLines(cmdLines, true);
        result = execute("cmd /c start /w /b " + rootPath + resource + " \"" + bat.getPath() + "\"");
        return result;
    }

    //      Create Tmp file inside of jar file
    public File createTmpFile(String resourcePath) {
        File tmp = null;
        String[] split = resourcePath.split("\\.");
        String extension = split.length > 1 ? split[1] : "";
        try {
            tmp = File.createTempFile("tmp", "." + extension);
            try (final ReadableByteChannel channel = Channels.newChannel(this.getClass().getResourceAsStream(resourcePath));
                 final FileChannel fileChannel = new RandomAccessFile(tmp, "rw").getChannel()) {
                final ByteBuffer bb = ByteBuffer.allocate(1024);
                while (channel.read(bb) > 0) {
                    bb.flip();
                    fileChannel.write(bb);
                    bb.clear();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tmp;
    }

}