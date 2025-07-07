package com.example.techmant_usuarios.controller;

import com.example.techmant_usuarios.DTOs.JwtResponse;
import com.example.techmant_usuarios.DTOs.LoginRequest;
import com.example.techmant_usuarios.model.Usuario;
import com.example.techmant_usuarios.repository.UsuarioRepository;
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

    // Método para el login de un usuario
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        // Buscar al usuario por correo usando findByCorreo
        Usuario usuario = usuarioRepository.findByCorreo(request.getCorreo()).orElse(null);

        // Si el usuario no existe o la contraseña no coincide, devolver un error
        if (usuario == null || !passwordEncoder.matches(request.getClave(), usuario.getContrasena())) {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }

        // Si las credenciales son correctas, generar el token JWT
        String token = jwtUtil.generateToken(usuario.getCorreo());

        // Retornar el token JWT en la respuesta
        return ResponseEntity.ok(new JwtResponse(token));
    }

    // Método para registrar un nuevo usuario
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody LoginRequest request) {

        // Verificar si el correo ya está registrado
        if (usuarioRepository.findByCorreo(request.getCorreo()).isPresent()) {
            return ResponseEntity.status(400).body("Correo ya registrado");
        }

        // Crear el nuevo usuario
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setCorreo(request.getCorreo());
        nuevoUsuario.setContrasena(passwordEncoder.encode(request.getClave()));  // Encriptar la contraseña
        // Asegúrate de asignar el rol adecuado
        // nuevoUsuario.setRol(rolRepository.findByNombre("CLIENTE").orElseThrow(...));

        // Guardar el nuevo usuario en la base de datos
        usuarioRepository.save(nuevoUsuario);

        // Generar un token para el usuario recién registrado
        String token = jwtUtil.generateToken(nuevoUsuario.getCorreo());

        // Retornar el token JWT en la respuesta
        return ResponseEntity.status(201).body(new JwtResponse(token));
    }
}
