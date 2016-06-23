package com.peter.util.connection;

import com.peter.util.data.Dir;
import org.junit.After;
import org.junit.Before;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.*;
import org.eclipse.jgit.api.errors.*;
import org.eclipse.jgit.api.CreateBranchCommand.SetupUpstreamMode;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by Peter on 6/22/2016.
 */
public class GITTest {

    private String localPath, remotePath,remotePathSSH,remotePathUserNamePass;
    private GIT git;
//    private static String username = "pgutierrez";
    private static String username = "PeterDG";
    private static String password = "";
    private static String privateKey= "E:\\Development\\Projects\\utils\\src\\main\\test\\resources\\git\\id_rsa";
    private static String passphrase= "pgutierrez";

    @Before
    public void init() throws IOException, InterruptedException {
        localPath = "E:\\Development\\Projects\\examples\\test";
        remotePathUserNamePass = "https://gitlab.com/PeterDG/test.git";
        remotePath = "git@gitlab.com:PeterDG/test.git";
//        remotePathUserNamePass = "http://192.168.243.3/pgutierrez/test.git";
//        remotePath = "git@192.168.243.3:pgutierrez/test.git";
        setSSHAuthentication();
        Dir.deleteDirectory(localPath);
    }


    @Test
    public void testCreate() throws IOException {
        Boolean resp = git.create();
        assertTrue(resp);
    }

    @Test
    public void testClone() throws IOException, GitAPIException {
        Boolean resp = git.clone();
        assertTrue(resp);
    }

    @Test
    public void testAddAndPush() throws IOException {
        String fileName = "myfile" + (int) (Math.random() * 10000);
        Boolean clone = git.clone();
        com.peter.util.data.File myfile = new com.peter.util.data.File(localPath + "/" + fileName);
        myfile.writeLine("Esto es una prueba",false);
        Boolean add = git.addAndPush(fileName,"Added file:"+fileName);
        assertTrue(add);
    }


    @Test
    public void testEditAndPush() throws IOException {
        String fileName = "myfile" + (int) (Math.random() * 10000);
        Boolean clone = git.clone();
        com.peter.util.data.File myfile = new com.peter.util.data.File(localPath + "/" + fileName);
        myfile.writeLine("Esto es una prueba\n Segunda Linea",true);
        Boolean add = git.addAndPush(fileName,"Added file:"+fileName);
        myfile.writeLine("Agregando nuevas lineas\n Cuarta Linea",false);
        Boolean edit = git.addAndPush(fileName,"Edited file:"+fileName);
        assertTrue(add);
        assertTrue(edit);
    }

    @Test
    public void testRemoveAndPush() throws IOException {
        String fileName = "myfile" + (int) (Math.random() * 10000);
        Boolean clone = git.clone();
        com.peter.util.data.File myfile = new com.peter.util.data.File(localPath + "/" + fileName);
        myfile.writeLine("Esto es una prueba",false);
        Boolean add = git.addAndPush(fileName,"Added file:"+fileName);
        Boolean remove = git.removeAndPush(fileName,"Removed file:"+fileName);
        assertTrue(add);
        assertTrue(remove);
    }

    @Test
    public void testAddFilePushSSH_gitUri() throws IOException {
        String fileName = "myfile" + (int) (Math.random() * 10000);
        Boolean clone = git.clone();
        File myfile = new File(localPath + "/" + fileName);
        myfile.createNewFile();
        Boolean add = git.add(fileName);
        Boolean commit = git.commit("Added " + fileName);
        Boolean push = git.push();
        assertTrue(clone);
        assertTrue(add);
        assertTrue(commit);
        assertTrue(push);
    }

    @Test
    public void testRemoveFilePushSSH_gitUri() throws IOException {
        String fileName = "myfile" + (int) (Math.random() * 10000);
        Boolean clone = git.clone();
        File myfile = new File(localPath + "/" + fileName);
        myfile.createNewFile();
        Boolean add = git.add(fileName);
        Boolean commit = git.commit("Added " + fileName);
        Boolean push = git.push();
        Boolean remove=git.remove(fileName);
        Boolean commitR = git.commit("Removed " + fileName);
        Boolean pushR = git.push();
        assertTrue(clone);
        assertTrue(add);
        assertTrue(commit);
        assertTrue(push);
        assertTrue(remove);
        assertTrue(commitR);
        assertTrue(pushR);
    }

    @Test
    public void testAddFilePushUserNamePasswordCredentials() throws IOException {
        setUserNamePassAuthentication();
        String fileName = "myfile" + (int) (Math.random() * 10000);
        Boolean clone = git.clone();
        File myfile = new File(localPath + "/" + fileName);
        myfile.createNewFile();
        Boolean add = git.add(fileName);
        Boolean commit = git.commit("Added " + fileName);
        Boolean push = git.push();
        assertTrue(clone);
        assertTrue(add);
        assertTrue(commit);
        assertTrue(push);
    }

    public void setUserNamePassAuthentication(){
        git = new GIT(localPath, remotePathUserNamePass);
        git.setUsernamePasswordCredentialsProvider(username, password);
    }

    public void setSSHAuthentication(){
        git = new GIT(localPath, remotePath);
        git.setPrivateKey(privateKey);
        git.setPassphrase(passphrase);
    }

}