package org.actics.customer.profiling.configuration;

import org.jooq.conf.Settings;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
public class JooqConfig {

    @Autowired
    private DataSource dataSource;

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String dbUsername;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    @Bean
    public org.jooq.Configuration jooqConfiguration() {
        System.out.println("DB URL: " + dbUrl);
        System.out.println("DB Username: " + dbUsername);
        // Do not print passwords in production code
        
        DefaultConfiguration configuration = new DefaultConfiguration();
        configuration.set(new DataSourceConnectionProvider(dataSource));
        configuration.setSQLDialect(org.jooq.SQLDialect.POSTGRES);
        configuration.set(new Settings().withRenderSchema(false));
        try {
            configuration.set(new org.jooq.impl.DefaultConnectionProvider(
                DriverManager.getConnection(dbUrl, dbUsername, dbPassword)
            ));
        } catch (SQLException e) {
            throw new RuntimeException("Error creating JOOQ connection", e);
        }
        return configuration;
    }
}