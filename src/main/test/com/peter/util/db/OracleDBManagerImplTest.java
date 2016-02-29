package com.peter.util.db;

import com.google.common.base.Optional;
import com.peter.util.connection.SSH;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import sys.Settings;

import java.sql.Connection;

import static org.junit.Assert.assertTrue;

/**
 * Created by Peter on 1/22/2016.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OracleDBManagerImplTest {
    public DBManager db;
    public Settings settings;
    public SSH ssh;
    @Before
    public void before() throws Exception {
        settings= Settings.getInstance();
        ssh = new SSH(settings.sshUrl, settings.sshPassword);
        Credentials credentials = new Credentials(settings.jdbcOracleUsername,settings.jdbcOraclePassword);
        ConnectionInfo connectionInfo=new ConnectionInfo(settings.jdbcOracleURL, Optional.of(credentials));
        db =DBManagerFactory.buildDBManager(connectionInfo);
    }

    @Test
    public void test02GetConnection() throws Exception {
        Connection connection= (Connection) db.getConnection();
        assertTrue(!connection.isClosed());
    }

    @After
    public void after() {
        ssh.closeTunnel();
        ssh.disconnect();
    }

}