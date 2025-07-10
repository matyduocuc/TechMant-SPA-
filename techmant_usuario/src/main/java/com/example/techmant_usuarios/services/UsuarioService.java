package com.example.techmant_usuarios.services;

import com.example.techmant_usuarios.DTOs.UsuarioRequestDTO;
import com.example.techmant_usuarios.DTOs.UsuarioResponseDTO;
import com.example.techmant_usuarios.model.Rol;
import com.example.techmant_usuarios.model.Usuario;
import com.example.techmant_usuarios.repository.RolRepository;
import com.example.techmant_usuarios.repository.UsuarioRepository;
import com.example.techmant_usuarios.util.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepo;
    private final RolRepository rolRepo;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UsuarioService(UsuarioRepository usuarioRepo, RolRepository rolRepo,
                          BCryptPasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.usuarioRepo = usuarioRepo;
        this.rolRepo = rolRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // ==========================
    //  Registro de Usuario
    // ==========================
    public UsuarioResponseDTO registrar(UsuarioRequestDTO dto) {
        // Verificar que el correo no esté registrado
        if (usuarioRepo.findByCorreo(dto.getCorreo()).isPresent()) {
            throw new RuntimeException("El correo ya está registrado.");
        }

        // Obtener el rol
        Rol rol = rolRepo.findByNombre(dto.getRol())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        // Encriptar la contraseña
        String contrasenaEncriptada = passwordEncoder.encode(dto.getContrasena());

        // Crear el nuevo usuario
        Usuario usuario = new Usuario(null, dto.getNombre(), dto.getCorreo(), contrasenaEncriptada, rol);
        usuarioRepo.save(usuario);

        // Crear el DTO para la respuesta
        return new UsuarioResponseDTO(usuario.getId(), usuario.getNombre(), usuario.getCorreo(), rol.getNombre());
    }

    // ==========================
    //  Login de Usuario
    // ==========================
    public String login(String correo, String contrasena) {
        // Verificar que el correo exista
        Usuario usuario = usuarioRepo.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Verificar la contraseña
        if (!passwordEncoder.matches(contrasena, usuario.getContrasena())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        // Generar el token JWT con el correo y el rol del usuario
        return jwtUtil.generateToken(usuario.getCorreo(), usuario.getRol().getNombre());
    }

    // ==========================
    //  Obtener todos los usuarios
    // ==========================
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

    // ==========================
    //  Obtener un usuario por ID
    // ==========================
    public Optional<UsuarioResponseDTO> obtenerPorId(Long id) {
        return usuarioRepo.findById(id)
                .map(usuario -> new UsuarioResponseDTO(
                        usuario.getId(),
                        usuario.getNombre(),
                        usuario.getCorreo(),
                        usuario.getRol().getNombre()
                ));
    }

    // ==========================
    //  Actualizar usuario
    // ==========================
    public UsuarioResponseDTO actualizarUsuario(Long id, UsuarioRequestDTO dto) {
        Usuario usuario = usuarioRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Obtener el rol
        Rol rol = rolRepo.findByNombre(dto.getRol())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        // Actualizar usuario
        usuario.setNombre(dto.getNombre());
        usuario.setCorreo(dto.getCorreo());

        if (dto.getContrasena() != null && !dto.getContrasena().isEmpty()) {
            usuario.setContrasena(passwordEncoder.encode(dto.getContrasena()));  // Encriptar la nueva contraseña
        }

        usuario.setRol(rol);
        usuarioRepo.save(usuario);

        return new UsuarioResponseDTO(usuario.getId(), usuario.getNombre(), usuario.getCorreo(), rol.getNombre());
    }

    // ==========================
    //  Eliminar usuario
    // ==========================
    public void eliminarUsuario(Long id) {
        Usuario usuario = usuarioRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuarioRepo.delete(usuario);
    }
}
