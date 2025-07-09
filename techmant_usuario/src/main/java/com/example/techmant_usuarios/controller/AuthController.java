package com.example.techmant_usuarios.controller;

import com.example.techmant_usuarios.DTOs.JwtResponse;
import com.example.techmant_usuarios.DTOs.LoginRequest;
import com.example.techmant_usuarios.model.Usuario;
import com.example.techmant_usuarios.repository.UsuarioRepository;
import com.example.techmant_usuarios.repository.RolRepository;  // Asegúrate de importar el repositorio de roles
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
    private RolRepository rolRepository;  // Asegúrate de inyectar el repositorio de roles

    // Login de Usuario
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        // Buscar el usuario en la base de datos por correo
        Usuario usuario = usuarioRepository.findByCorreo(request.getCorreo()).orElse(null);

        // Verificar si el usuario existe y si la contraseña coincide
        if (usuario == null || !passwordEncoder.matches(request.getClave(), usuario.getContrasena())) {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }

        // Generar el token JWT
        String token = jwtUtil.generateToken(usuario.getCorreo(), usuario.getRol().getNombre());
        
        // Retornar el token generado
        return ResponseEntity.ok(new JwtResponse(token));
    }

    // Registro de Usuario
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody LoginRequest request) {
        // Verificar si el correo ya está registrado
        if (usuarioRepository.findByCorreo(request.getCorreo()).isPresent()) {
            return ResponseEntity.status(400).body("Correo ya registrado");
        }

        // Crear un nuevo usuario
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setCorreo(request.getCorreo());
        nuevoUsuario.setContrasena(passwordEncoder.encode(request.getClave()));  // Encriptar la contraseña

        // Asignar el rol recibido en la solicitud, si no existe asignar el rol ADMIN por defecto
        String rolNombre = request.getRol();  // Obtener el rol del body
        if (rolNombre == null || rolNombre.isEmpty()) {
            rolNombre = "CLIENTE";  // Asignar "CLIENTE" si no se recibe un rol
        }
        // Obtener el rol de la base de datos
        var rol = rolRepository.findByNombre(rolNombre)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        nuevoUsuario.setRol(rol);  // Asignar el rol al usuario

        // Guardar el nuevo usuario en la base de datos
        usuarioRepository.save(nuevoUsuario);

        // Generar el token JWT para el nuevo usuario
        String token = jwtUtil.generateToken(nuevoUsuario.getCorreo(), nuevoUsuario.getRol().getNombre());

        // Retornar el token generado en la respuesta
        return ResponseEntity.status(201).body(new JwtResponse(token));
    }
}
