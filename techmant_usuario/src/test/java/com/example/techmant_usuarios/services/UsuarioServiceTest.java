package com.example.techmant_usuarios.services;

import com.example.techmant_usuarios.model.Rol;
import com.example.techmant_usuarios.model.Usuario;
import com.example.techmant_usuarios.repository.RolRepository;
import com.example.techmant_usuarios.repository.UsuarioRepository;
import com.example.techmant_usuarios.DTOs.UsuarioRequestDTO;
import com.example.techmant_usuarios.DTOs.UsuarioResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private RolRepository rolRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegistrarUsuario() {
        UsuarioRequestDTO usuarioRequestDTO = new UsuarioRequestDTO("Juan", "juan@mail.com", "password", "ADMIN");
        Rol rol = new Rol(1L, "ADMIN");
        Usuario usuario = new Usuario(1L, "Juan", "juan@mail.com", "password", rol);

        when(rolRepository.findByNombre("ADMIN")).thenReturn(Optional.of(rol));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        UsuarioResponseDTO result = usuarioService.registrar(usuarioRequestDTO);

        assertEquals("Juan", result.getNombre());
        assertEquals("juan@mail.com", result.getCorreo());
        assertEquals("ADMIN", result.getRol());
    }

    @Test
    public void testLogin() {
        String correo = "juan@mail.com";
        String contrasena = "password";

        Rol rol = new Rol(1L, "ADMIN");
        Usuario usuario = new Usuario(1L, "Juan", correo, contrasena, rol);

        when(usuarioRepository.findByCorreo(correo)).thenReturn(Optional.of(usuario));

        UsuarioResponseDTO result = usuarioService.login(correo, contrasena);

        assertEquals("Juan", result.getNombre());
        assertEquals("juan@mail.com", result.getCorreo());
        assertEquals("ADMIN", result.getRol());
    }

    @Test
    public void testObtenerUsuarios() {
        Rol rol = new Rol(1L, "ADMIN");
        Usuario usuario = new Usuario(1L, "Juan", "juan@mail.com", "password", rol);

        when(usuarioRepository.findAll()).thenReturn(List.of(usuario));

        var usuarios = usuarioService.obtenerUsuarios();

        assertFalse(usuarios.isEmpty());
        assertEquals(1, usuarios.size());
        assertEquals("Juan", usuarios.get(0).getNombre());
    }

    @Test
    public void testActualizarUsuario() {
        Long id = 1L;
        UsuarioRequestDTO usuarioRequestDTO = new UsuarioRequestDTO("Juan Updated", "juan@mail.com", "newpassword", "CLIENTE");
        Rol rol = new Rol(1L, "CLIENTE");
        Usuario usuario = new Usuario(id, "Juan", "juan@mail.com", "password", rol);

        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));
        when(rolRepository.findByNombre("CLIENTE")).thenReturn(Optional.of(rol));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        UsuarioResponseDTO result = usuarioService.actualizarUsuario(id, usuarioRequestDTO);

        assertEquals("Juan Updated", result.getNombre());
        assertEquals("CLIENTE", result.getRol());
    }

    @Test
    public void testEliminarUsuario() {
        Long id = 1L;
        Usuario usuario = new Usuario(id, "Juan", "juan@mail.com", "password", new Rol(1L, "ADMIN"));

        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));

        usuarioService.eliminarUsuario(id);

        verify(usuarioRepository, times(1)).delete(usuario);
    }
}
