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

    private final String SECRET_KEY = "clave-super-segura-para-jwt-2025-clinicavetsystem";
    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 10;  // 10 horas

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String generateToken(String correo, String rol) {
        return Jwts.builder()
                .setSubject(correo)  // Establece el correo como el "subject" del token
                .claim("role", rol)  // Añade el rol como un claim en el token
                .setIssuedAt(new Date())  // Establece la fecha de emisión
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))  // Establece la fecha de expiración
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)  // Firma el token
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String extractRole(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("role", String.class);  // Extrae el rol del token
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}
