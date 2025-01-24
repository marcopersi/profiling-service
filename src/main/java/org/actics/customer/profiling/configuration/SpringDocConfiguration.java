package org.actics.customer.profiling.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SpringDocConfiguration {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("FIDLEG Compliant Customer Profile API")
                                .description("A REST API for managing customer profiles compliant with FINMA's FIDLEG requirements, including economic circumstances, risk tolerance, and more.")
                                .version("1.0.0")
                                .termsOfService("https://www.example.com/terms")
                                .contact(
                                        new Contact()
                                                .name("Support Team")
                                                .email("support@example.com")
                                                .url("https://www.example.com/support")
                                )
                                .license(
                                        new License()
                                                .name("Apache 2.0")
                                                .url("https://www.apache.org/licenses/LICENSE-2.0")
                                )
                );
    }
}
