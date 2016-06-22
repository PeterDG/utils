package com.peter.util.connection;

import org.eclipse.jgit.api.*;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;
import java.io.IOException;

/**
 * Created by Peter on 6/22/2016.
 */
public class GIT {

    public String localPath, remotePath;
    public Repository localRepo;
    public Git git;
    public CredentialsProvider credentialsProvider;

    public GIT(String localPath) {
        try {
            localRepo = new FileRepository(localPath + "/.git");
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.localPath = localPath;
        git = new Git(localRepo);
    }

    public GIT(String localPath, String remotePath) {
        this(localPath);
        this.remotePath = remotePath;
    }

    public void setCredentialsProvider(CredentialsProvider credentialsProvider) {
        this.credentialsProvider = credentialsProvider;
    }

    public void setUsernamePasswordCredentialsProvider(String userName, String password) {
        this.credentialsProvider = new UsernamePasswordCredentialsProvider(userName, password);
    }

    public void setSSHConnection(String userName, String password) {
        this.credentialsProvider = new UsernamePasswordCredentialsProvider(userName, password);
    }

    public Boolean clone() {
        TransportCommand cmd = Git.cloneRepository().setURI(remotePath).setDirectory(new File(localPath));
        return run(cmd);
    }

    public Boolean push() {
        TransportCommand cmd = git.push();
        return run(cmd);
    }

    public Boolean fetch() {
        TransportCommand cmd = git.fetch();
        return run(cmd);
    }

    public Boolean pull() {
        TransportCommand cmd = git.pull();
        return run(cmd);
    }

    public Boolean commit(String message) {
        GitCommand cmd = git.commit().setMessage(message);
        return run(cmd);
    }

    public Boolean add(String fileRegExp) {
        GitCommand cmd =git.add().addFilepattern(fileRegExp);
        return run(cmd);
    }

    public Boolean run(TransportCommand command) {
        try {
            command.setCredentialsProvider(credentialsProvider).call();
        } catch (GitAPIException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Boolean run(GitCommand command) {
        try {
            command.call();
        } catch (GitAPIException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Boolean create() {
        try {
            localRepo.create();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
