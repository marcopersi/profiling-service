package org.actics.customer.profiling.configuration;

import org.jooq.conf.Settings;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class JooqConfig {

    @Autowired
    private DataSource dataSource;

    @Bean
    public org.jooq.Configuration jooqConfiguration() {
        DefaultConfiguration configuration = new DefaultConfiguration();
        configuration.set(new DataSourceConnectionProvider(dataSource));
        configuration.setSQLDialect(org.jooq.SQLDialect.POSTGRES);
        configuration.set(new Settings().withRenderSchema(false)); // Beispiel
        return configuration;
    }
}