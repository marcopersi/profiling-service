package org.actics.customer.profiling.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SpringDocConfiguration {

    @Bean(name = "org.openapitools.configuration.SpringDocConfiguration.apiInfo")
    OpenAPI apiInfo() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("FIDLEG Compliant Customer Profile API")
                                .description("A REST API for managing customer profiles compliant with FINMA's FIDLEG requirements, including economic circumstances, risk tolerance, and more.")
                                .version("1.0.0")
                )
        ;
    }
}