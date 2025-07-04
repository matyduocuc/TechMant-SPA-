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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private RolRepository rolRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

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
        Usuario usuario = new Usuario(1L, "Juan", "juan@mail.com", "encodedPassword", rol);

        when(rolRepository.findByNombre("ADMIN")).thenReturn(Optional.of(rol));
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
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
        Usuario usuario = new Usuario(1L, "Juan", correo, "encodedPassword", rol);

        when(usuarioRepository.findByCorreo(correo)).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches(contrasena, "encodedPassword")).thenReturn(true);

        UsuarioResponseDTO result = usuarioService.login(correo, contrasena);

        assertEquals("Juan", result.getNombre());
        assertEquals("juan@mail.com", result.getCorreo());
        assertEquals("ADMIN", result.getRol());
    }

    @Test
    public void testLoginContrasenaIncorrecta() {
        String correo = "juan@mail.com";
        String contrasena = "password";

        Rol rol = new Rol(1L, "ADMIN");
        Usuario usuario = new Usuario(1L, "Juan", correo, "otraClave", rol);

        when(usuarioRepository.findByCorreo(correo)).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches(contrasena, "otraClave")).thenReturn(false);

        Exception ex = assertThrows(RuntimeException.class, () -> {
            usuarioService.login(correo, contrasena);
        });

        assertTrue(ex.getMessage().contains("Contraseña incorrecta"));
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
        UsuarioRequestDTO dto = new UsuarioRequestDTO("Juan Updated", "juan@mail.com", "newpassword", "CLIENTE");
        Rol rol = new Rol(2L, "CLIENTE");
        Usuario usuario = new Usuario(id, "Juan", "juan@mail.com", "password", rol);

        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));
        when(rolRepository.findByNombre("CLIENTE")).thenReturn(Optional.of(rol));
        when(passwordEncoder.encode("newpassword")).thenReturn("encodedNewPassword");
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        UsuarioResponseDTO result = usuarioService.actualizarUsuario(id, dto);

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

    @Test
    public void testObtenerPorId() {
        Long id = 1L;
        Rol rol = new Rol(1L, "ADMIN");
        Usuario usuario = new Usuario(id, "Juan", "juan@mail.com", "password", rol);

        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));

        Optional<UsuarioResponseDTO> result = usuarioService.obtenerPorId(id);

        assertTrue(result.isPresent());
        assertEquals("Juan", result.get().getNombre());
        assertEquals("ADMIN", result.get().getRol());
    }
}