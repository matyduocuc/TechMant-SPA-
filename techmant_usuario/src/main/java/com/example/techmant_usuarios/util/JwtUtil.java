package com.example.techmant_usuarios.util;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // Clave secreta de mínimo 32 caracteres para HMAC-SHA
    private final String SECRET_KEY = "clave-super-segura-para-jwt-2025-clinicavetsystem";

    // Token válido por 10 horas (en milisegundos)
    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 10;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    // Genera un token con el correo del usuario y el rol
    public String generateToken(String correo, String rol) {
        return Jwts.builder()
                .setSubject(correo)
                .claim("role", rol)  // Añadir el rol al token
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Extrae el correo del usuario desde el token
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Extrae el rol desde el token
    public String extractRole(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("role", String.class);  // Extraer el rol desde las reclamaciones
    }

    // Valida si el token es correcto y no ha expirado
    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false; // Token inválido o expirado
        }
    }
}
