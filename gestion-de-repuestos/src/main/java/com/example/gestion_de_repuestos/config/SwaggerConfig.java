package com.example.gestion_de_repuestos.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("Gestión de Repuestos API")
                        .description("Documentación del microservicio de repuestos")
                        .version("1.0"));
    }
}
