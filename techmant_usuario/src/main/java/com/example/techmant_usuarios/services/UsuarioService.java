package com.example.techmant_usuarios.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.techmant_usuarios.DTOs.UsuarioRequestDTO;
import com.example.techmant_usuarios.DTOs.UsuarioResponseDTO;
import com.example.techmant_usuarios.model.Rol;
import com.example.techmant_usuarios.model.Usuario;
import com.example.techmant_usuarios.repository.RolRepository;
import com.example.techmant_usuarios.repository.UsuarioRepository;
import com.example.techmant_usuarios.util.JwtUtil;  // Asegúrate de que esté correctamente importado

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepo;
    private final RolRepository rolRepo;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;  // Inyectar JwtUtil

    // Inyección de dependencias a través del constructor
    public UsuarioService(UsuarioRepository usuarioRepo, RolRepository rolRepo, 
                          BCryptPasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.usuarioRepo = usuarioRepo;
        this.rolRepo = rolRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;  // Asignar JwtUtil
    }

    // ========================
    //      REGISTRO
    // ========================
    public UsuarioResponseDTO registrar(UsuarioRequestDTO dto) {
        try {
            // Validar que el correo no esté ya registrado
            if (usuarioRepo.findByCorreo(dto.getCorreo()).isPresent()) {
                throw new RuntimeException("El correo ya está registrado.");
            }

            // Obtener el rol
            Rol rol = rolRepo.findByNombre(dto.getRol())
                    .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

            // Encriptar la contraseña
            String hash = passwordEncoder.encode(dto.getContrasena());

            // Crear el usuario
            Usuario usuario = new Usuario(null, dto.getNombre(), dto.getCorreo(), hash, rol);
            usuarioRepo.save(usuario);

            // Retornar la respuesta DTO
            return new UsuarioResponseDTO(usuario.getId(), usuario.getNombre(), usuario.getCorreo(), rol.getNombre());
        } catch (Exception e) {
            throw new RuntimeException("Error al registrar el usuario: " + e.getMessage(), e);
        }
    }

    // ========================
    //         LOGIN
    // ========================
    public String login(String correo, String contrasena) {
        try {
            if (correo == null || correo.isEmpty()) {
                throw new RuntimeException("Correo no proporcionado.");
            }

            // Buscar el usuario por correo
            Usuario usuario = usuarioRepo.findByCorreo(correo)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            if (usuario.getContrasena() == null || contrasena == null) {
                throw new RuntimeException("Contraseña o usuario nulos.");
            }

            // Verificar la contraseña
            if (!passwordEncoder.matches(contrasena, usuario.getContrasena())) {
                throw new RuntimeException("Contraseña incorrecta");
            }

            // Generar el token JWT
            return jwtUtil.generateToken(usuario.getCorreo());  // Usamos el JwtUtil inyectado
        } catch (Exception e) {
            throw new RuntimeException("Error en el login: " + e.getMessage(), e);
        }
    }

    // ========================
    //    LISTAR TODOS
    // ========================
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

    // ========================
    //     BUSCAR POR ID
    // ========================
    public Optional<UsuarioResponseDTO> obtenerPorId(Long id) {
        return usuarioRepo.findById(id)
                .map(usuario -> new UsuarioResponseDTO(
                        usuario.getId(),
                        usuario.getNombre(),
                        usuario.getCorreo(),
                        usuario.getRol().getNombre()
                ));
    }

    // ========================
    //     ACTUALIZAR
    // ========================
    public UsuarioResponseDTO actualizarUsuario(Long id, UsuarioRequestDTO dto) {
        try {
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

    // ========================
    //      ELIMINAR
    // ========================
    public void eliminarUsuario(Long id) {
        try {
            Usuario usuario = usuarioRepo.findById(id)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            usuarioRepo.delete(usuario);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar el usuario: " + e.getMessage(), e);
        }
    }
}
