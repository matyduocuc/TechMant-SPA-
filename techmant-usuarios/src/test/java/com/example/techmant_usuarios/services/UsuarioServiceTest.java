package com.example.techmant_usuarios.services;

import com.example.techmant_usuarios.DTOs.UsuarioRequestDTO;
import com.example.techmant_usuarios.DTOs.UsuarioResponseDTO;
import com.example.techmant_usuarios.model.Rol;
import com.example.techmant_usuarios.model.Usuario;
import com.example.techmant_usuarios.repository.RolRepository;
import com.example.techmant_usuarios.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private RolRepository rolRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegistrar() {
        UsuarioRequestDTO dto = new UsuarioRequestDTO("Juan", "juan@example.com", "1234", "CLIENTE");
        Rol rol = new Rol(1L, "CLIENTE");
        Usuario usuario = new Usuario(null, "Juan", "juan@example.com", "1234", rol);

        when(rolRepository.findByNombre("CLIENTE")).thenReturn(Optional.of(rol));
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(i -> {
            Usuario u = i.getArgument(0);
            u.setId(1L);
            return u;
        });

        UsuarioResponseDTO response = usuarioService.registrar(dto);

        assertNotNull(response);
        assertEquals("Juan", response.getNombre());
        assertEquals("juan@example.com", response.getCorreo());
        assertEquals("CLIENTE", response.getRol());
    }

    @Test
    void testLoginSuccess() {
        String email = "juan@example.com";
        String password = "1234";
        Rol rol = new Rol(1L, "CLIENTE");
        Usuario usuario = new Usuario(1L, "Juan", email, password, rol);

        when(usuarioRepository.findByCorreo(email)).thenReturn(Optional.of(usuario));

        UsuarioResponseDTO result = usuarioService.login(email, password);

        assertNotNull(result);
        assertEquals("Juan", result.getNombre());
        assertEquals(email, result.getCorreo());
    }

    @Test
    void testObtenerUsuarios() {
        Rol rol = new Rol(1L, "CLIENTE");
        Usuario user = new Usuario(1L, "Juan", "juan@example.com", "1234", rol);

        when(usuarioRepository.findAll()).thenReturn(java.util.List.of(user));

        var usuarios = usuarioService.obtenerUsuarios();

        assertEquals(1, usuarios.size());
        assertEquals("Juan", usuarios.get(0).getNombre());
    }
}

