package com.peter.util.db;

import com.google.common.base.Optional;

/**
 * @author Agustin Cabra
 * @version 8/21/2015.
 */
public class ConnectionInfo {

    private final String jdbcUrl;
    private final Optional<Credentials> credentials;


    public ConnectionInfo(final String jdbcUrl, final Optional<Credentials> credentials) {
        this.jdbcUrl = jdbcUrl;
        this.credentials = credentials;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public String getUserName() {
        return credentials.get().userName;
    }

    public String getPassword() {
        return credentials.get().password;
    }

    public String getDBName() {
        String[] split = jdbcUrl.split(":*/*/");
        if (split.length == 3) return split[2];
        else return "";
    }

    public DBManagerType getDBType() {
        String[] split = jdbcUrl.split(":");
        String strDBType = split[1].toLowerCase();
        DBManagerType type = null;
        if (strDBType.contains("mongo")) type = DBManagerType.MONGO;
        if (strDBType.contains("postgres")) type = DBManagerType.POSTGRES;
        if (strDBType.contains("oracle")) type = DBManagerType.ORACLE;
        return type;
    }

    public String getJDBCUrlWithDBName() {
        String dbName = getDBName();
        String url;
        if (dbName != null) url = jdbcUrl.replace(dbName, "");
        else url = this.getJdbcUrl();
        char lastChar = url.charAt(url.length() - 1);
        if (!(lastChar == '/')) url += "/";
        return url;
    }


    public String getServer() {
        return jdbcUrl.split(":*/*/")[1].split(":")[0];
    }

    public Integer getPort() {
        return Integer.parseInt(jdbcUrl.split(":*/*/")[1].split(":")[1]);
    }

    public Optional<Credentials> getCredentials() {
        return credentials;
    }
}
