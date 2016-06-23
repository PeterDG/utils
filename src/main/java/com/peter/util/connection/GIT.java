package com.peter.util.connection;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;
import org.eclipse.jgit.api.*;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.errors.UnsupportedCredentialItem;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.*;
import org.eclipse.jgit.util.FS;

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
    public String privateKey;
    public String passphrase = "";


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

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public void setPassphrase(String passphrase) {
        this.passphrase = passphrase;
    }

    public void setUsernamePasswordCredentialsProvider(String userName, String password) {
        this.credentialsProvider = new UsernamePasswordCredentialsProvider(userName, password);
    }


    public static TransportCommand setSSHConnection(TransportCommand command, String privateKey, String passphrase) {
        SshSessionFactory sshSessionFactory = new JschConfigSessionFactory() {
            @Override
            protected void configure(OpenSshConfig.Host host, Session session) {
                UserInfo userInfo = new UserInfo() {
                    @Override
                    public String getPassphrase() {
                        return passphrase;
                    }

                    @Override
                    public String getPassword() {
                        return "";
                    }

                    @Override
                    public boolean promptPassword(String message) {
                        return false;
                    }

                    @Override
                    public boolean promptPassphrase(String message) {
                        return true;
                    }

                    @Override
                    public boolean promptYesNo(String message) {
                        // accept host authenticity
                        return true;
                    }

                    @Override
                    public void showMessage(String message) {
                    }
                };
                session.setUserInfo(userInfo);
            }

            @Override
            protected JSch createDefaultJSch(FS fs) throws JSchException {
                JSch defaultJSch = super.createDefaultJSch(fs);
                defaultJSch.addIdentity(privateKey);
                return defaultJSch;
            }
        };
        command.setTransportConfigCallback(new TransportConfigCallback() {
            @Override
            public void configure(Transport transport) {
                SshTransport sshTransport = (SshTransport) transport;
                sshTransport.setSshSessionFactory(sshSessionFactory);
            }
        });
        return command;
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
        GitCommand cmd = git.add().addFilepattern(fileRegExp);
        return run(cmd);
    }

    public Boolean addAndPush(String fileRegExp, String message) {
        Boolean fetch = fetch();
        Boolean pull = pull();
        Boolean opr= add(fileRegExp);
        Boolean commit=commit(message);
        Boolean push=push();
        return fetch&&pull&&opr&&commit&&push;
    }

    public Boolean removeAndPush(String fileRegExp, String message) {
        Boolean fetch = fetch();
        Boolean pull = pull();
        Boolean opr= remove(fileRegExp);
        Boolean commit=commit(message);
        Boolean push=push();
        return fetch&&pull&&opr&&commit&&push;
    }

    public Boolean remove(String fileRegExp) {
        GitCommand cmd = git.rm().addFilepattern(fileRegExp);
        return run(cmd);
    }

    public Boolean run(TransportCommand command) {
        if (sshAuthentication())
            command = setSSHConnection(command, privateKey, passphrase);
        else
            command = (TransportCommand) command.setCredentialsProvider(credentialsProvider);
        return run((GitCommand) command);
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

    public boolean sshAuthentication() {
        if (remotePath.matches("git@.*") || remotePath.matches("ssh://.*"))
            return true;
        return false;
    }

    public void close() {
        git.close();
    }
}
