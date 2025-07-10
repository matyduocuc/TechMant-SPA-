package com.example.techmant_usuarios.filters;

import com.example.techmant_usuarios.util.JwtUtil;
import com.example.techmant_usuarios.model.Usuario;
import com.example.techmant_usuarios.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            final String jwt = authHeader.substring(7);
            final String correo = jwtUtil.extractUsername(jwt);

            if (correo != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                Usuario usuario = usuarioRepository.findByCorreo(correo).orElse(null);
                if (usuario != null && jwtUtil.isTokenValid(jwt)) {
                    String role = jwtUtil.extractRole(jwt);  // Extrae el rol del token

                    if (role != null) {
                        // Asigna el rol al contexto de seguridad
                        UsernamePasswordAuthenticationToken authToken = 
                            new UsernamePasswordAuthenticationToken(usuario, null, null);
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
