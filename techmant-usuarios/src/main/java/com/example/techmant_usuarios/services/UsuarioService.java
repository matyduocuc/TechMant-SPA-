package com.example.techmant_usuarios.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.techmant_usuarios.DTOs.UsuarioRequestDTO;
import com.example.techmant_usuarios.DTOs.UsuarioResponseDTO;
import com.example.techmant_usuarios.model.Rol;
import com.example.techmant_usuarios.model.Usuario;
import com.example.techmant_usuarios.repository.RolRepository;
import com.example.techmant_usuarios.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepo;
    private final RolRepository rolRepo;

    public UsuarioService(UsuarioRepository usuarioRepo, RolRepository rolRepo) {
        this.usuarioRepo = usuarioRepo;
        this.rolRepo = rolRepo;
    }

    public UsuarioResponseDTO registrar(UsuarioRequestDTO dto) {
        Rol rol = rolRepo.findByNombre(dto.getRol())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        Usuario usuario = new Usuario(null, dto.getNombre(), dto.getCorreo(), dto.getContrasena(), rol);
        usuarioRepo.save(usuario);

        return new UsuarioResponseDTO(usuario.getId(), usuario.getNombre(), usuario.getCorreo(), rol.getNombre());
    }

    public UsuarioResponseDTO login(String correo, String contrasena) {
        Usuario usuario = usuarioRepo.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!usuario.getContrasena().equals(contrasena)) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        return new UsuarioResponseDTO(usuario.getId(), usuario.getNombre(), usuario.getCorreo(), usuario.getRol().getNombre());
    }

    public List<Usuario> listarTodos() {
        return usuarioRepo.findAll();
    }
}
