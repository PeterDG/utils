package com.peter.util.connection;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.util.Properties;

/**
 * Created by Peter on 2/28/2016.
 */
public class SSH {
    public String sshUser;
    public String sshPassword;
    public String sshHost;
    public int sshPort;
    public String remoteHost;
    public int remotePort;
    public int localPort;
    public Session session;

    public SSH(String sshUser, String sshPassword, String sshHost, int sshPort) {
        this.sshUser = sshUser;
        this.sshPassword = sshPassword;
        this.sshHost = sshHost;
        this.sshPort = sshPort;
        connect();
    }

    public SSH(String sshUrl, String sshPassword) {
        String [] split =sshUrl.split(":|@|/| |->");
        this.sshUser = split[3];
        this.sshPassword = sshPassword;
        this.sshHost = split[4];
        this.sshPort = Integer.valueOf(split[5]);
        connect();
        if(sshUrl.contains("->")){
            this.localPort=Integer.valueOf(split[6]);
            this.remoteHost=split[7];
            this.remotePort=Integer.valueOf(split[8]);
            buildTunnel(localPort,remoteHost,remotePort);
        }
    }

    public void connect() {
        try {
            final JSch jsch = new JSch();
            session = jsch.getSession(sshUser, sshHost, sshPort);
            session.setPassword(sshPassword);
            final Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void disconnect(){
        session.disconnect();
    }

    public void buildTunnel(int localPort,String remoteHost, int remotePort){
        this.localPort=localPort;
        this.remoteHost=remoteHost;
        this.remotePort=remotePort;
        try {
            session.setPortForwardingL(localPort, remoteHost, remotePort);
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }

    public void closeTunnel(){
        try {
            session.delPortForwardingL(localPort);
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }

}
