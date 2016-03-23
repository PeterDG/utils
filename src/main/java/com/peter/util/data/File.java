package com.peter.util.data;


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
public class File {

    public java.io.File file;
    public Path path;
    public Path directory;
    public String defaultEncodingName="Cp1252"; //ANSI Encoding

    public File(String strPath, String nameFile) {
        this.path = Paths.get(strPath+"/"+nameFile);
        this.directory = path.getParent();
        this.file = new java.io.File(getPath());
    }

    public File(String strPath) {
        this.path = Paths.get(strPath);
        this.directory = path.getParent();
        this.file = new java.io.File(getPath().replace("\\","/"));
    }

    public void clean() {
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
                if (file.exists()) file.delete();
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
        java.io.File folder = file;
        java.io.File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                fileNames.add(listOfFiles[i].getName());
            } else if (listOfFiles[i].isDirectory()) {
             //   System.out.println("Directory " + listOfFiles[i].getName());
            }
        }

        return fileNames;
    }

    public boolean moveFile(String target) {
        new java.io.File(target).mkdirs();
        java.io.File targetFile = new java.io.File(target + "/" + file.getName());
        return file.renameTo(targetFile);
    }

    public void moveDir(String target) {
        List<String> fileNames = getListFilesOfDirectory();
        for (int i = 0; i < fileNames.size(); i++) {
            File file = new File(getDirectory(), fileNames.get(i));
            file.moveFile(target);
        }
    }

    public void moveAllFilesByExtension(String target, String extension) {
        List<String> fileNames = getListFilesOfDirectory().stream()
                .filter(p -> p.contains("." + extension)).collect(Collectors.toList());
        for (int i = 0; i < fileNames.size(); i++) {
            File file = new File(getDirectory(), fileNames.get(i));
            file.moveFile(target);
        }
    }

    public List<String> getFileNamesBasedOnRegex(String regex) {
        List<String> fileNames = getListFilesOfDirectory().stream()
                .filter(p -> p.matches(regex)).collect(Collectors.toList());
        return fileNames;
    }

    public void removeAllFilesByExtension(String target, String extension) {
        List<String> fileNames = getListFilesOfDirectory().stream()
                .filter(p -> p.contains("." + extension)).collect(Collectors.toList());
        for (int i = 0; i < fileNames.size(); i++) {
            File file = new File(getDirectory(), fileNames.get(i));
            file.getFile().delete();
        }
    }

    public List<String> getListLinesOfFile() {
        List<String> linesList = null;
        try {
            Stream<String> lines = Files.lines(path,Charset.forName(defaultEncodingName));
            linesList = lines.filter(x -> !x.isEmpty()).collect(Collectors.toList());
            lines.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return linesList;
    }

    public void replaceTextLists(String outFilePath, ArrayList<String[]> pairsToReplace) {
        this.replace(getPath(), outFilePath, pairsToReplace.get(0)[0], pairsToReplace.get(0)[1]);
        for (int i = 1; i < pairsToReplace.size(); i++) {
            this.replace(outFilePath, outFilePath, pairsToReplace.get(i)[0], pairsToReplace.get(i)[1]);
        }
    }

    private void replace(String inFilePath, String outFilePath, String text, String replaceWith) {
        try {
            java.io.File file = new java.io.File(inFilePath);
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
    public void setFile(java.io.File file) {
        this.file = file;
    }

    public void setDirectory(String path) {
        this.directory = Paths.get(path);
    }

    public java.io.File getFile() {
        return file;
    }

    public String getDirectory() {
        return directory.toString();
    }

    public String getPath() {
        return path.toString();
    }

    public String toString() {
        byte[] encoded = new byte[0];
        String result = null;
        try {
            encoded = Files.readAllBytes(path);
             result=new String(encoded, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public ArrayList getFileName(String path) {
        ArrayList<String> list = new ArrayList<String>();
        List<String> nodes = Arrays.asList(path.split("[\\\\,/]"));
        String fileName = nodes.get(nodes.size() - 1);
        String filePath = nodes.subList(0, nodes.size() - 1).toString().replaceAll(", ", "\\\\").replaceAll("[\\[,\\],]", "");
        list.add(filePath);
        list.add(fileName);
        return list;
    }

    public void replaceTextFromFile(String regex, String replacement) {
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
            content = content.substring(0, content.length() - 1);
            Files.write(path, content.getBytes(charset));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File copy(String target) {

        try {
            Files.copy(path,Paths.get(target));
        } catch (IOException e) {
            e.printStackTrace();
        }
        File file = new File(target);
        return file;
    }

    public void delete() {
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean exist(){
        return file.exists();
    }

}
