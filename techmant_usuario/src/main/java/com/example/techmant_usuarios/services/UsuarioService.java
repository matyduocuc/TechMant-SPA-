package com.example.techmant_usuarios.services;

import org.springframework.stereotype.Service;

import com.example.techmant_usuarios.DTOs.UsuarioRequestDTO;
import com.example.techmant_usuarios.DTOs.UsuarioResponseDTO;
import com.example.techmant_usuarios.model.Rol;
import com.example.techmant_usuarios.model.Usuario;
import com.example.techmant_usuarios.repository.RolRepository;
import com.example.techmant_usuarios.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepo;
    private final RolRepository rolRepo;

    public UsuarioService(UsuarioRepository usuarioRepo, RolRepository rolRepo) {
        this.usuarioRepo = usuarioRepo;
        this.rolRepo = rolRepo;
    }

    // ========================
    //      REGISTRO
    // ========================
    public UsuarioResponseDTO registrar(UsuarioRequestDTO dto) {
        try {
            Rol rol = rolRepo.findByNombre(dto.getRol())
                    .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

            Usuario usuario = new Usuario(null, dto.getNombre(), dto.getCorreo(), dto.getContrasena(), rol);
            usuarioRepo.save(usuario);

            return new UsuarioResponseDTO(usuario.getId(), usuario.getNombre(), usuario.getCorreo(), rol.getNombre());
        } catch (Exception e) {
            throw new RuntimeException("Error al registrar el usuario: " + e.getMessage(), e);
        }
    }

    // ========================
    //         LOGIN
    // ========================
    public UsuarioResponseDTO login(String correo, String contrasena) {
        try {
            if (correo == null || correo.isEmpty()) {
                throw new RuntimeException("Correo no proporcionado.");
            }

            Usuario usuario = usuarioRepo.findByCorreo(correo)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            if (usuario.getContrasena() == null || contrasena == null) {
                throw new RuntimeException("Contraseña o usuario nulos.");
            }

            if (!contrasena.equals(usuario.getContrasena())) {
                throw new RuntimeException("Contraseña incorrecta");
            }

            return new UsuarioResponseDTO(usuario.getId(), usuario.getNombre(), usuario.getCorreo(), usuario.getRol().getNombre());

        } catch (Exception e) {
            e.printStackTrace();
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

            Rol rol = rolRepo.findByNombre(dto.getRol())
                    .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

            usuario.setNombre(dto.getNombre());
            usuario.setCorreo(dto.getCorreo());
            usuario.setContrasena(dto.getContrasena());
            usuario.setRol(rol);
            usuarioRepo.save(usuario);

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