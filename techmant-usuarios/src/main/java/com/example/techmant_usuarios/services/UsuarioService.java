package com.example.techmant_usuarios.services;

import org.springframework.stereotype.Service;

import com.example.techmant_usuarios.DTOs.UsuarioRequestDTO;
import com.example.techmant_usuarios.DTOs.UsuarioResponseDTO;
import com.example.techmant_usuarios.model.Rol;
import com.example.techmant_usuarios.model.Usuario;
import com.example.techmant_usuarios.repository.RolRepository;
import com.example.techmant_usuarios.repository.UsuarioRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepo;
    private final RolRepository rolRepo;

    // Constructor con inyección de dependencias
    public UsuarioService(UsuarioRepository usuarioRepo, RolRepository rolRepo) {
        this.usuarioRepo = usuarioRepo;
        this.rolRepo = rolRepo;
    }

    // Método para registrar un nuevo usuario
    public UsuarioResponseDTO registrar(UsuarioRequestDTO dto) {
        try {
            // Verificar si el rol existe en la base de datos
            Rol rol = rolRepo.findByNombre(dto.getRol())
                    .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

            // Crear el nuevo usuario sin cifrar la contraseña
            Usuario usuario = new Usuario(null, dto.getNombre(), dto.getCorreo(), dto.getContrasena(), rol);
            usuarioRepo.save(usuario);

            // Retornar el DTO del usuario registrado
            return new UsuarioResponseDTO(usuario.getId(), usuario.getNombre(), usuario.getCorreo(), rol.getNombre());
        } catch (Exception e) {
            throw new RuntimeException("Error al registrar el usuario: " + e.getMessage(), e);
        }
    }

public UsuarioResponseDTO login(String correo, String contrasena) {
    try {
        // Verificar si el correo es válido
        if (correo == null || correo.isEmpty()) {
            throw new RuntimeException("Correo no proporcionado.");
        }

        System.out.println("Recibiendo solicitud de login con correo: " + correo);

        // Buscar el usuario por correo
        Usuario usuario = usuarioRepo.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Verificar si el usuario y la contraseña no son null
        if (usuario.getContrasena() == null || contrasena == null) {
            throw new RuntimeException("Contraseña o usuario nulos.");
        }

        // Mostrar las contraseñas para depuración
        System.out.println("Contraseña almacenada: " + usuario.getContrasena());
        System.out.println("Contraseña proporcionada: " + contrasena);

        // Comparar la contraseña directamente (sin cifrado)
        if (!contrasena.equals(usuario.getContrasena())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        // Retornar los datos del usuario autenticado
        return new UsuarioResponseDTO(usuario.getId(), usuario.getNombre(), usuario.getCorreo(), usuario.getRol().getNombre());

    } catch (Exception e) {
        // Mostrar el stack trace completo del error
        e.printStackTrace();
        throw new RuntimeException("Error en el login: " + e.getMessage(), e);
    }
}


    // Método para obtener todos los usuarios
    public List<UsuarioResponseDTO> obtenerUsuarios() {
        List<Usuario> usuarios = usuarioRepo.findAll();
        return usuarios.stream()
                .map(usuario -> new UsuarioResponseDTO(usuario.getId(), usuario.getNombre(), usuario.getCorreo(), usuario.getRol().getNombre()))
                .collect(Collectors.toList());
    }

    // Método para actualizar un usuario
    public UsuarioResponseDTO actualizarUsuario(Long id, UsuarioRequestDTO dto) {
        try {
            // Buscar el usuario por ID
            Usuario usuario = usuarioRepo.findById(id)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            // Verificar si el rol existe en la base de datos
            Rol rol = rolRepo.findByNombre(dto.getRol())
                    .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

            // Actualizar los datos del usuario
            usuario.setNombre(dto.getNombre());
            usuario.setCorreo(dto.getCorreo());
            usuario.setContrasena(dto.getContrasena());
            usuario.setRol(rol);
            usuarioRepo.save(usuario);

            // Retornar los datos del usuario actualizado
            return new UsuarioResponseDTO(usuario.getId(), usuario.getNombre(), usuario.getCorreo(), rol.getNombre());
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar el usuario: " + e.getMessage(), e);
        }
    }

    // Método para eliminar un usuario
    public void eliminarUsuario(Long id) {
        try {
            // Buscar el usuario por ID
            Usuario usuario = usuarioRepo.findById(id)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            // Eliminar el usuario de la base de datos
            usuarioRepo.delete(usuario);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar el usuario: " + e.getMessage(), e);
        }
    }
}