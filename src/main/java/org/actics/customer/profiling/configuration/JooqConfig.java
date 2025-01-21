package org.actics.customer.profiling.configuration;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.jdbc.DataSourceBuilder;

import javax.sql.DataSource;

@Configuration
public class JooqConfig {

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:h2:mem:testdb") // In-Memory H2-Datenbank
                .username("sa")
                .password("")
                .driverClassName("org.h2.Driver")
                .build();
    }

    @Bean
    public org.jooq.Configuration jooqConfiguration(DataSource dataSource) {
        return new DefaultConfiguration()
                .set(SQLDialect.H2)
                .set(dataSource);
    }

    @Bean
    public DSLContext dslContext(org.jooq.Configuration jooqConfiguration) {
        return DSL.using(jooqConfiguration);
    }
}