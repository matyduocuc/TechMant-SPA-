package com.example.techmant_usuarios.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.techmant_usuarios.DTOs.UsuarioRequestDTO;
import com.example.techmant_usuarios.DTOs.UsuarioResponseDTO;
import com.example.techmant_usuarios.model.Rol;
import com.example.techmant_usuarios.model.Usuario;
import com.example.techmant_usuarios.repository.RolRepository;
import com.example.techmant_usuarios.repository.UsuarioRepository;
import com.example.techmant_usuarios.util.JwtUtil;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepo;    // Repositorio para interactuar con la base de datos de usuarios
    private final RolRepository rolRepo;             // Repositorio para interactuar con la base de datos de roles
    private final BCryptPasswordEncoder passwordEncoder; // Para encriptar las contraseñas
    private final JwtUtil jwtUtil;                   // Utilidad para manejar la generación de tokens JWT

    // Constructor con inyección de dependencias
    public UsuarioService(UsuarioRepository usuarioRepo, RolRepository rolRepo, 
                          BCryptPasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.usuarioRepo = usuarioRepo;
        this.rolRepo = rolRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // Método para registrar un usuario
    public UsuarioResponseDTO registrar(UsuarioRequestDTO dto) {
        try {
            // Verificar si el correo ya está registrado
            if (usuarioRepo.findByCorreo(dto.getCorreo()).isPresent()) {
                throw new RuntimeException("El correo ya está registrado.");
            }

            // Obtener el rol ADMIN
            Rol rol = rolRepo.findByNombre("ADMIN")
                    .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

            // Encriptar la contraseña del usuario
            String hash = passwordEncoder.encode(dto.getContrasena());

            // Crear un nuevo usuario
            Usuario usuario = new Usuario(null, dto.getNombre(), dto.getCorreo(), hash, rol);
            usuarioRepo.save(usuario); // Guardar el usuario en la base de datos

            // Retornar la respuesta DTO
            return new UsuarioResponseDTO(usuario.getId(), usuario.getNombre(), usuario.getCorreo(), rol.getNombre());
        } catch (Exception e) {
            throw new RuntimeException("Error al registrar el usuario: " + e.getMessage(), e);
        }
    }

    // Método para realizar login
    public String login(String correo, String contrasena) {
        try {
            // Buscar al usuario por correo
            Usuario usuario = usuarioRepo.findByCorreo(correo)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            // Verificar la contraseña
            if (!passwordEncoder.matches(contrasena, usuario.getContrasena())) {
                throw new RuntimeException("Contraseña incorrecta");
            }

            // Generar el token JWT
            return jwtUtil.generateToken(usuario.getCorreo(), usuario.getRol().getNombre()); // Incluir el rol en el token
        } catch (Exception e) {
            throw new RuntimeException("Error en el login: " + e.getMessage(), e);
        }
    }

    // Método para obtener todos los usuarios
    public List<UsuarioResponseDTO> obtenerUsuarios() {
        return usuarioRepo.findAll().stream()
                .map(usuario -> new UsuarioResponseDTO(
                        usuario.getId(),
                        usuario.getNombre(),
                        usuario.getCorreo(),
                        usuario.getRol().getNombre()
                ))
                .collect(Collectors.toList());
    }

    // Método para obtener un usuario por ID
    public Optional<UsuarioResponseDTO> obtenerPorId(Long id) {
        return usuarioRepo.findById(id)
                .map(usuario -> new UsuarioResponseDTO(
                        usuario.getId(),
                        usuario.getNombre(),
                        usuario.getCorreo(),
                        usuario.getRol().getNombre()
                ));
    }

    // Método para actualizar un usuario
    public UsuarioResponseDTO actualizarUsuario(Long id, UsuarioRequestDTO dto) {
        try {
            // Buscar el usuario a actualizar
            Usuario usuario = usuarioRepo.findById(id)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            // Obtener el rol
            Rol rol = rolRepo.findByNombre(dto.getRol())
                    .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

            // Actualizar los datos del usuario
            usuario.setNombre(dto.getNombre());
            usuario.setCorreo(dto.getCorreo());

            // Si se proporciona una nueva contraseña, la encriptamos
            if (dto.getContrasena() != null && !dto.getContrasena().isEmpty()) {
                String hash = passwordEncoder.encode(dto.getContrasena());
                usuario.setContrasena(hash);
            }

            usuario.setRol(rol);
            usuarioRepo.save(usuario);

            // Retornar la respuesta DTO
            return new UsuarioResponseDTO(usuario.getId(), usuario.getNombre(), usuario.getCorreo(), rol.getNombre());
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar el usuario: " + e.getMessage(), e);
        }
    }

    // Método para eliminar un usuario
    public void eliminarUsuario(Long id) {
        try {
            Usuario usuario = usuarioRepo.findById(id)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            usuarioRepo.delete(usuario); // Eliminar el usuario de la base de datos
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar el usuario: " + e.getMessage(), e);
        }
    }
}
