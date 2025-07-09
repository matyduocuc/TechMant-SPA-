package com.example.techmant_usuarios.config;

import com.example.techmant_usuarios.filters.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    // Constructor para inyectar el filtro JWT
    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    // Definición del PasswordEncoder (usado para encriptar contraseñas)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Definición del AuthenticationManager (gestiona la autenticación)
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // Configuración de los filtros de seguridad
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()  // Desactivamos CSRF si estamos usando JWT
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // Usamos sesión sin estado (stateless)
            .and()
            .authorizeRequests()
                .requestMatchers(
                    "/api/v1/auth/**",  // Rutas públicas (login, registro, Swagger, etc.)
                    "/swagger-ui/**",
                    "/v3/api-docs/**",
                    "/swagger-ui.html",
                    "/swagger-resources/**",
                    "/webjars/**",
                    "/usuarios/registrar"  // Permitir el acceso a la ruta de registro de usuario
                ).permitAll()  // Estas rutas no requieren autenticación
                .anyRequest().authenticated()  // El resto de las rutas requieren autenticación
            .and()
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);  // Registrar el filtro JWT antes de la autenticación estándar

        return http.build();
    }
}
