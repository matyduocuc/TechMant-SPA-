package com.example.techmant_usuarios.controller;

import com.example.techmant_usuarios.DTOs.JwtResponse;
import com.example.techmant_usuarios.DTOs.LoginRequest;
import com.example.techmant_usuarios.model.Usuario;
import com.example.techmant_usuarios.repository.UsuarioRepository;
import com.example.techmant_usuarios.repository.RolRepository;
import com.example.techmant_usuarios.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RolRepository rolRepository;

    // Login de Usuario
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Usuario usuario = usuarioRepository.findByCorreo(request.getCorreo()).orElse(null);

        if (usuario == null || !passwordEncoder.matches(request.getClave(), usuario.getContrasena())) {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }

        // Generar el token JWT con el correo y el rol del usuario
        String token = jwtUtil.generateToken(usuario.getCorreo(), usuario.getRol().getNombre());
        return ResponseEntity.ok(new JwtResponse(token));
    }

    // Registro de Usuario
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody LoginRequest request) {
        if (usuarioRepository.findByCorreo(request.getCorreo()).isPresent()) {
            return ResponseEntity.status(400).body("Correo ya registrado");
        }

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setCorreo(request.getCorreo());
        nuevoUsuario.setContrasena(passwordEncoder.encode(request.getClave()));  // Encriptar la contraseña

        var rol = rolRepository.findByNombre("ADMIN")
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        nuevoUsuario.setRol(rol);
        usuarioRepository.save(nuevoUsuario);

        // Generar el token JWT
        String token = jwtUtil.generateToken(nuevoUsuario.getCorreo(), nuevoUsuario.getRol().getNombre());
        return ResponseEntity.status(201).body(new JwtResponse(token));
    }
}
