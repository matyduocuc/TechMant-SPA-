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

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = authHeader.substring(7);  // Eliminamos "Bearer "
        final String correo = jwtUtil.extractUsername(jwt);  // Extraemos el correo del token

        if (correo != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            Usuario usuario = usuarioRepository.findByCorreo(correo).orElse(null);

            if (usuario != null && jwtUtil.isTokenValid(jwt)) {
                // Verificar que el rol sea ADMIN
                String rol = jwtUtil.extractRole(jwt);  // Asegúrate de que el rol se extraiga correctamente
                if ("ADMIN".equals(rol)) {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(usuario, null, Collections.emptyList());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                } else {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "No tienes permisos suficientes");
                    return;
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
