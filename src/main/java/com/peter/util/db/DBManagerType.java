package com.peter.util.db;

/**
 * @author acabra
 * @version 2015-06-18
 */
public enum DBManagerType {
    MONGO("","\"src/main/resources/db/scripts/mongo/"),
    ORACLE("oracle.jdbc.OracleDriver","src/main/resources/db/scripts/oracle/"),
    POSTGRES("org.postgresql.Driver","src/main/resources/db/scripts/postgres/"),
    MYSQL("com.mysql.jdbc.Driver","src/main/resources/db/scripts/mysql/");

    public final String driverClassName;
    public String scriptsPath;

    DBManagerType(final String driverClassName,final String scriptsPath) {
        this.driverClassName = driverClassName;
        this.scriptsPath=scriptsPath;

    }
}
