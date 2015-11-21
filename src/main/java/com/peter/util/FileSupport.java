package com.peter.util;


import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Peter on 5/13/2015.
 */
public class FileSupport {

    public File file;
    public String path;

    public FileSupport(String path, String nameFile) {
        this.file = new File(path, nameFile);
        this.path = path;
    }

    public FileSupport(String path) {
        ArrayList<String> data = getFileName(path);
        this.file = new File(data.get(0), data.get(1));
        this.path = path;
    }

    public void clean(){
        try {
            file.delete();
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeLine(String content, boolean override) {
        String eol = System.getProperty("line.separator");

        try {
            // if file doesnt exists, then create it
            if (!file.exists() || override) {
                file.delete();
                file.createNewFile();
            }
            // APPEND MODE SET HERE
            BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
            bw.append(content);
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeLines(List<String> content, boolean override) {
        try {
            if (override) {
                if(file.exists()) file.delete();
                file.createNewFile();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String line : content) {
            writeLine(line, false);
        }
    }

    public List<String> getListFilesOfDirectory() {
        List<String> fileNames = new ArrayList<String>();
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                fileNames.add(listOfFiles[i].getName());
            } else if (listOfFiles[i].isDirectory()) {
                System.out.println("Directory " + listOfFiles[i].getName());
            }
        }

        return fileNames;
    }

    public boolean moveFile(String target){
        new File(target).mkdirs();
        File targetFile=new File(target +"/"+ file.getName());
        return file.renameTo(targetFile);
    }

    public void moveDir(String target){
        List<String> fileNames=getListFilesOfDirectory();
        for (int i = 0; i < fileNames.size(); i++) {
           FileSupport file= new FileSupport(path,fileNames.get(i));
            file.moveFile(target);
        }
    }

    public void moveAllFilesByExtension(String target, String extension){
        List<String> fileNames = getListFilesOfDirectory().stream()
                .filter(p -> p.contains("." + extension)).collect(Collectors.toList());
        for (int i = 0; i < fileNames.size(); i++) {
            FileSupport file= new FileSupport(path,fileNames.get(i));
            file.moveFile(target);
        }
    }

    public List<String> getFileNamesBasedOnRegex(String regex){
        List<String> fileNames = getListFilesOfDirectory().stream()
                .filter(p -> p.matches(regex)).collect(Collectors.toList());
        return fileNames;
    }

    public void removeAllFilesByExtension(String target, String extension){
        List<String> fileNames = getListFilesOfDirectory().stream()
                .filter(p -> p.contains("." + extension)).collect(Collectors.toList());
        for (int i = 0; i < fileNames.size(); i++) {
            FileSupport file= new FileSupport(path,fileNames.get(i));
            file.getFile().delete();
        }
    }

    public List<Object> getListLinesOfFile() {
        List<Object> linesList = null;
        try {
            Stream<String> lines = Files.lines(Paths.get(path));
            linesList = lines.filter(x -> !x.isEmpty()).collect(Collectors.toList());
            lines.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return linesList;
    }

    public void replace(String inFilePath, String outFilePath, String text, String replaceWith) {
        try {
            File file = new File(inFilePath);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = "", oldtext = "";
            while ((line = reader.readLine()) != null) {
                oldtext += line + "\n";
            }
            reader.close();

            String newtext = oldtext.replaceAll(text, replaceWith);

            FileWriter writer = new FileWriter(outFilePath);
            writer.write(newtext);
            writer.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }

    ///////////////////Getters and Seters//////////////////
    public void setFile(File file) {
        this.file = file;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public File getFile() {
        return file;
    }

    public String getPath() {
        return path;
    }

    public String toString() {
        String fileStr;
        fileStr = file.toString();
        return fileStr;
    }

    public ArrayList getFileName(String path){
        ArrayList<String> list=new ArrayList<String>();
        List<String> nodes = Arrays.asList(path.split("[\\\\,/]"));
        String fileName= nodes.get(nodes.size() - 1);
        String filePath=nodes.subList(0,nodes.size()-1).toString().replaceAll(", ","\\\\").replaceAll("[\\[,\\],]","");
        list.add(filePath);
        list.add(fileName);
        return list;
    }

    public void replaceTextFromFile(String regex,String replacement){
        Path path = Paths.get(file.getAbsolutePath());
        Charset charset = StandardCharsets.UTF_8;
        try {
            String content = new String(Files.readAllBytes(path), charset);
            content = content.replaceAll(regex, replacement);
            Files.write(path, content.getBytes(charset));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void removeLastCharacter() {
        Path path = Paths.get(file.getAbsolutePath());
        Charset charset = StandardCharsets.UTF_8;
        try {
            String content = new String(Files.readAllBytes(path), charset);
            content = content.substring(0,content.length()-1);
            Files.write(path, content.getBytes(charset));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
