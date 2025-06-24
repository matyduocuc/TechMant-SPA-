package com.example.gestion_de_servicios.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("servicios-api")
                .pathsToMatch("/api/**") // Puedes ajustar esto según tus rutas
                .build();
    }
}
