package com.example.techmant_usuarios.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Desactiva protección CSRF para facilitar pruebas en Postman
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() // 🔓 Permite todas las rutas sin autenticación
            );

        return http.build();
    }
}