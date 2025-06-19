package com.example.techmant_usuarios.services;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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

    private final UsuarioRepository usuarioRepo = null;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UsuarioResponseDTO login(String correo, String contrasena) {
    try {
        // Buscar el usuario por correo
        Usuario usuario = usuarioRepo.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Verificar si la contraseña proporcionada es correcta
        if (!passwordEncoder.matches(contrasena, usuario.getContrasena())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        // Retornar los datos del usuario autenticado
        return new UsuarioResponseDTO(usuario.getId(), usuario.getNombre(), usuario.getCorreo(), usuario.getRol().getNombre());
    } catch (Exception e) {
        throw new RuntimeException("Error en el login: " + e.getMessage(), e);
    }
    }

    public UsuarioResponseDTO registrar(UsuarioRequestDTO usuarioRequestDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'registrar'");
    }

    public List<UsuarioResponseDTO> obtenerUsuarios() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'obtenerUsuarios'");
    }

    public UsuarioResponseDTO actualizarUsuario(Long id, UsuarioRequestDTO usuarioRequestDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actualizarUsuario'");
    }

    public void eliminarUsuario(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eliminarUsuario'");
    }

}