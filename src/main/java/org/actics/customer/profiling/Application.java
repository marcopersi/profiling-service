package org.actics.customer.profiling;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        app.setAdditionalProfiles("production"); // Beispiel: Aktives Profil setzen
        app.run(args);
    }
    @Bean
    public ApplicationRunner applicationRunner() {
        return args -> System.out.println("Application started successfully!");
    }

}
