package com.peter.util.connection;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
/**
 * Created by Peter on 2/28/2016.
 */
public class SSHTest {

    public SSH ssh;
    public String sshUser;
    public String sshPassword;
    public String sshPasswordA;
    public String sshHost;
    public int sshPort;
    public String remoteHost;
    public int localPort;
    public int remotePort;
    public String dbName;
    public String dbUser;
    public String dbPassword;
    public String sshUrl;
    public String sshUrlA;
    public String sshUrlWithTunnel;

    @Before
    public void before() {
        sshUser = "root";                           // SSH loging username
        sshPassword = "root";                       // SSH login password
        sshHost = "7.183.185.100";                  // hostname or ip or SSH server
        sshPort = 22;                               // remote SSH host port number
        remoteHost = "168.176.5.66";                // hostname or ip of your database server
        localPort = 3365;                           // local port number use to bind SSH tunnel
        remotePort = 1521;                          // remote port number of your database
        dbName = "pruebog";                         // database name
        dbUser = "sia_us_prog";                     // database loging username
        dbPassword = "bogota";                      // database login password
        sshUrl = "ssh://root@7.183.185.100:22";       // ssh url
        sshUrlWithTunnel = "ssh://root@7.183.185.100:22 3365->168.176.5.66:1521";       // ssh url type2

        sshPasswordA = "3asyso1";                       // SSH login password
        sshUrlA = "ssh://root@192.168.243.197:22";
    }

    @Test
    public void testConnectUrl() throws Exception {
        ssh = new SSH(sshUrl, sshPassword);
        Boolean result=ssh.session.isConnected();
        ssh.disconnect();
        assertTrue(result);
    }

    @Test
    public void testConnect() throws Exception {
        ssh = new SSH(sshUser, sshPassword,sshHost,sshPort);
        Boolean result=ssh.session.isConnected();
        ssh.disconnect();
        assertTrue(result);
    }

    @Test
    public void testBuildTunnel() throws Exception {
        ssh = new SSH(sshUrl, sshPassword);
        ssh.buildTunnel(localPort,remoteHost,remotePort);
        int result =ssh.session.getPortForwardingL().length;
        ssh.closeTunnel();
        ssh.disconnect();
        assertTrue( result>0);
    }

    @Test
    public void testBuildTunnelUrl() throws Exception {
        ssh = new SSH(sshUrlWithTunnel, sshPassword);
        int result =ssh.session.getPortForwardingL().length;
        ssh.closeTunnel();
        ssh.disconnect();
        assertTrue( result>0);
    }

    @Test
    public void testExecuteCommand() throws Exception {
        ssh = new SSH(sshUrlA, sshPasswordA);
        Boolean result=ssh.session.isConnected();
        String response=ssh.executeCommand("echo Hello World!");
        ssh.disconnect();
        assertTrue(result && response.equals("Hello World!\n"));
    }

}