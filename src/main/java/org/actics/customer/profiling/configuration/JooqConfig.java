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
                .url("jdbc:postgresql://localhost:5432/profiling_db") // In-Memory H2-Datenbank
                .username("postgres")
                .password("password")
                .driverClassName("org.postgresql.Driver")
                .build();
    }

    @Bean
    public org.jooq.Configuration jooqConfiguration(DataSource dataSource) {
        return new DefaultConfiguration()
                .set(SQLDialect.POSTGRES)
                .set(dataSource);
    }

    @Bean
    public DSLContext dslContext(org.jooq.Configuration jooqConfiguration) {
        return DSL.using(jooqConfiguration);
    }
}