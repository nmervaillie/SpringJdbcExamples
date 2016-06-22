package org.github.examples.config;

import java.util.Arrays;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.StringUtils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import liquibase.integration.spring.SpringLiquibase;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "org.github.examples")
public class DatabaseConfiguration implements EnvironmentAware {

    private final Logger log = LoggerFactory.getLogger(DatabaseConfiguration.class);

    private Environment env;

    @Configuration
    @Profile("default")
    @PropertySource("classpath:application.properties")
    static class Defaults
    { }

    @Configuration
    @Profile("postgres")
    @PropertySource({"classpath:application.properties", "classpath:application-postgres.properties"})
    static class Overrides
    {
    }

    public void setEnvironment(Environment env) {
        this.env = env;
    }

    @Bean(destroyMethod = "close")
    public DataSource dataSource() {
        log.debug("Configuring Datasource");
        if (env.getProperty("spring.datasource.url") == null && env.getProperty("spring.datasource.databaseName") == null) {
            log.error("Your database connection pool configuration is incorrect! The application" +
                    " cannot start. Please check your Spring profile, current profiles are: {}",
                    Arrays.toString(env.getActiveProfiles()));

            throw new ApplicationContextException("Database connection pool is not configured correctly");
        }
        HikariConfig config = new HikariConfig();
        config.setDataSourceClassName(env.getProperty("spring.datasource.dataSourceClassName"));
        if(StringUtils.isEmpty(env.getProperty("spring.datasource.url"))) {
            config.addDataSourceProperty("databaseName", env.getProperty("spring.datasource.databaseName"));
            config.addDataSourceProperty("serverName", env.getProperty("spring.datasource.serverName"));
        } else {
            config.addDataSourceProperty("url", env.getProperty("spring.datasource.url"));
        }
        config.addDataSourceProperty("user", env.getProperty("spring.datasource.username"));
        config.addDataSourceProperty("password", env.getProperty("spring.datasource.password"));

        return new HikariDataSource(config);
    }

    @Bean
    public SpringLiquibase liquibase(DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog("classpath:config/liquibase/master.xml");
        liquibase.setContexts(env.getProperty("liquibase.context"));
        return liquibase;
    }

}
