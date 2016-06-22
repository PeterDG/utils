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

    private String localPath, remotePath;
    private GIT git;
    private static String username = "PeterDG";
    private static String password = "";

    @Before
    public void init() throws IOException {
        localPath = "E:\\Development\\Projects\\examples\\test";
        remotePath = "https://gitlab.com/PeterDG/test.git";
        git = new GIT(localPath, remotePath);
        git.setUsernamePasswordCredentialsProvider(username, password);
        com.peter.util.data.File file = new com.peter.util.data.File(localPath);
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
    public void testAddFilePushUserNamePasswordCredentials() throws IOException {
        String fileName = "myfile" + (int) (Math.random() * 1000);
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


}