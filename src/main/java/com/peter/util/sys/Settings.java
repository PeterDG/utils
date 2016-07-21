package com.peter.util.sys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Service;

/**
 * Created by Pedro Gutierrez on 2/26/2016.
 */

@Configuration
@PropertySource("classpath:configuration.properties")
@Service("settings")
public class Settings {

    @Value("${database.postgres.jdbc.url}")
    public String jdbcPostgresURL;
    @Value("${database.postgres.jdbc.username}")
    public String jdbcPostgresUsername;
    @Value("${database.postgres.jdbc.password}")
    public String jdbcPostgresPassword;
    @Value("${database.oracle.jdbc.url}")
    public String jdbcOracleURL;
    @Value("${database.oracle.jdbc.username}")
    public String jdbcOracleUsername;
    @Value("${database.oracle.jdbc.password}")
    public String jdbcOraclePassword;
    @Value("${connection.ssh.url}")
    public String sshUrl;
    @Value("${connection.ssh.password}")
    public String sshPassword;


    public static Settings instance;

    public static Settings getInstance() {
        if (instance == null) {
            AnnotationConfigApplicationContext annotationContext = new AnnotationConfigApplicationContext(Settings.class);
            instance = (Settings)annotationContext.getBean("settings");
        }
        return instance;
    }

    public void setJdbcPostgresURL(String jdbcPostgresURL) {
        this.jdbcPostgresURL = jdbcPostgresURL;
    }

    public void setJdbcPostgresUsername(String jdbcPostgresUsername) {
        this.jdbcPostgresUsername = jdbcPostgresUsername;
    }

    public void setJdbcPostgresPassword(String jdbcPostgresPassword) {
        this.jdbcPostgresPassword = jdbcPostgresPassword;
    }

    public String getJdbcPostgresURL() {
        return jdbcPostgresURL;
    }

    public String getJdbcPostgresPassword() {
        return jdbcPostgresPassword;
    }

    public String getJdbcPostgresUsername() {
        return jdbcPostgresUsername;
    }

    //To resolve ${} in @Value
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}
