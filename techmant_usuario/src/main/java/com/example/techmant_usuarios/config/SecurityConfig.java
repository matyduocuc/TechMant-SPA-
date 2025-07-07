package com.example.techmant_usuarios.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()  // Desactiva CSRF para facilitar pruebas en Postman
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/v1/auth/**", "/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html")
                .permitAll()  // Acceso público a estas rutas
                .requestMatchers("/admin/**").hasRole("ADMIN")  // Solo ADMIN puede acceder a estas rutas
                .requestMatchers("/tecnico/**").hasRole("TECNICO")  // Solo TECNICO puede acceder
                .requestMatchers("/cliente/**").hasRole("CLIENTE")  // Solo CLIENTE puede acceder
                .anyRequest().authenticated()  // Cualquier otra solicitud necesita estar autenticada
            )
            .formLogin().permitAll()  // Permite que todos puedan acceder al formulario de login
            .logout(logout -> logout
                .logoutUrl("/logout")  // Establece la URL de logout
                .permitAll()  // Permite que todos puedan hacer logout
            );

        return http.build();
    }
}
