package com.example.gestion_de_ventas.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi ventasApi() {
        return GroupedOpenApi.builder()
                .group("ventas")
                .pathsToMatch("/api/**")
                .build();
    }
}