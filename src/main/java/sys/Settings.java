package sys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Service;

/**
 * Created by Pedro Gutierrez on 2/26/2016.
 */
@Configuration
@PropertySource("classpath:configuration.properties")
@Service("settings")
public class Settings {

    @Value("${database.jdbc.url}")
    public String jdbcURL;
    @Value("${database.jdbc.username}")
    public String jdbcUsername;
    @Value("${database.jdbc.password}")
    public String jdbcPassword;

    public static Settings instance;

    public static Settings getInstance() {
        if (instance == null) {
            AnnotationConfigApplicationContext annotationContext = new AnnotationConfigApplicationContext(Settings.class);
            instance = (Settings)annotationContext.getBean("settings");
        }
        return instance;
    }

    public void setJdbcURL(String jdbcURL) {
        this.jdbcURL = jdbcURL;
    }

    public void setJdbcUsername(String jdbcUsername) {
        this.jdbcUsername = jdbcUsername;
    }

    public void setJdbcPassword(String jdbcPassword) {
        this.jdbcPassword = jdbcPassword;
    }

    public String getJdbcURL() {
        return jdbcURL;
    }

    public String getJdbcPassword() {
        return jdbcPassword;
    }

    public String getJdbcUsername() {
        return jdbcUsername;
    }

    //To resolve ${} in @Value
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}
