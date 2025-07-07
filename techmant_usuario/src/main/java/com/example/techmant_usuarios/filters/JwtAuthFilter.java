package com.example.techmant_usuarios.filters;

import com.example.techmant_usuarios.model.Usuario;
import com.example.techmant_usuarios.repository.UsuarioRepository;
import com.example.techmant_usuarios.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;  // Utilidad para manejar el token JWT

    @Autowired
    private UsuarioRepository usuarioRepository;  // Repositorio de usuarios

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        // Si no hay encabezado de autorización o no empieza con "Bearer ", pasamos al siguiente filtro
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extraemos el token JWT del encabezado
        final String jwt = authHeader.substring(7);  // Eliminamos el prefijo "Bearer "
        final String correo = jwtUtil.extractUsername(jwt);  // Extraemos el correo del token

        // Verificamos si el correo no es nulo y si el usuario no está autenticado
        if (correo != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Buscamos al usuario en la base de datos
            Usuario usuario = usuarioRepository.findByCorreo(correo).orElse(null);

            // Si el usuario existe y el token es válido
            if (usuario != null && jwtUtil.isTokenValid(jwt)) {
                // Crear el token de autenticación con los detalles del usuario
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(usuario, null, Collections.emptyList());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));  // Establecer los detalles de la solicitud

                // Establecemos la autenticación en el contexto de seguridad de Spring
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Pasamos la solicitud al siguiente filtro en la cadena
        filterChain.doFilter(request, response);
    }
}
