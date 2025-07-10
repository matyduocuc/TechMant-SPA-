package com.example.techmant_usuarios.services;

import com.example.techmant_usuarios.DTOs.UsuarioRequestDTO;
import com.example.techmant_usuarios.DTOs.UsuarioResponseDTO;
import com.example.techmant_usuarios.model.Rol;
import com.example.techmant_usuarios.model.Usuario;
import com.example.techmant_usuarios.util.JwtUtil;
import com.example.techmant_usuarios.repository.RolRepository;
import com.example.techmant_usuarios.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepo;

    @Mock
    private RolRepository rolRepo;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;  // Mock de JwtUtil

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);  // Inicializa los mocks
    }

    // Test de login de usuario
    @Test
    public void testLogin() {
        String correo = "juan@mail.com";
        String contrasena = "password";

        // Simulamos el rol del usuario
        Rol rol = new Rol(1L, "ADMIN");

        // Simulamos el usuario con correo y contraseña encriptada
        Usuario usuario = new Usuario(1L, "Juan", correo, "encodedPassword", rol);

        // Simulamos que el usuario existe en la base de datos
        when(usuarioRepo.findByCorreo(correo)).thenReturn(Optional.of(usuario));

        // Simulamos que la contraseña coincide
        when(passwordEncoder.matches(contrasena, "encodedPassword")).thenReturn(true);

        // Simulamos la generación del token JWT
        when(jwtUtil.generateToken(correo, rol.getNombre())).thenReturn("jwt_token_example");

        // Ejecutamos el servicio de login
        String token = usuarioService.login(correo, contrasena);

        // Verificamos que el token devuelto sea el esperado
        assertEquals("jwt_token_example", token);

        // Verificamos que jwtUtil.generateToken haya sido llamado con los parámetros correctos
        verify(jwtUtil, times(1)).generateToken(correo, rol.getNombre());
    }

    // Test de registro de usuario
    @Test
    public void testRegistrarUsuario() {
        UsuarioRequestDTO usuarioRequestDTO = new UsuarioRequestDTO("Juan", "juan@mail.com", "password", "ADMIN");
        Rol rol = new Rol(1L, "ADMIN");
        Usuario usuario = new Usuario(1L, "Juan", "juan@mail.com", "encodedPassword", rol);

        // Simulamos la encriptación de la contraseña
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        // Simulamos que el rol exista
        when(rolRepo.findByNombre("ADMIN")).thenReturn(Optional.of(rol));

        // Simulamos la creación del usuario en el servicio
        when(usuarioRepo.save(any(Usuario.class))).thenReturn(usuario);

        // Simulamos la generación del token JWT
        when(jwtUtil.generateToken(usuario.getCorreo(), usuario.getRol().getNombre())).thenReturn("jwt_token_example");

        UsuarioResponseDTO result = usuarioService.registrar(usuarioRequestDTO);

        // Verificamos que el nombre, correo y rol sean correctos
        assertEquals("Juan", result.getNombre());
        assertEquals("juan@mail.com", result.getCorreo());
        assertEquals("ADMIN", result.getRol());
    }

    // Test de obtener usuario por ID
    @Test
    public void testObtenerUsuarioPorId() {
        Long id = 1L;
        Rol rol = new Rol(1L, "ADMIN");
        Usuario usuario = new Usuario(id, "Juan", "juan@mail.com", "password", rol);
        UsuarioResponseDTO usuarioResponseDTO = new UsuarioResponseDTO(id, "Juan", "juan@mail.com", "ADMIN");

        // Simulamos que el usuario existe
        when(usuarioRepo.findById(id)).thenReturn(Optional.of(usuario));

        // Ejecutamos el método de obtener por ID
        Optional<UsuarioResponseDTO> result = usuarioService.obtenerPorId(id);

        // Verificamos que la respuesta no esté vacía y que el usuario devuelto sea correcto
        assertTrue(result.isPresent());
        assertEquals("Juan", result.get().getNombre());
        assertEquals("ADMIN", result.get().getRol());
    }

    // Test de actualizar usuario
    @Test
    public void testActualizarUsuario() {
        Long id = 1L;
        UsuarioRequestDTO dto = new UsuarioRequestDTO("Juan Updated", "juan@mail.com", "newpassword", "CLIENTE");
        Rol rol = new Rol(2L, "CLIENTE");
        Usuario usuario = new Usuario(id, "Juan", "juan@mail.com", "encodedPassword", rol);

        // Simulamos la existencia del usuario y el rol
        when(usuarioRepo.findById(id)).thenReturn(Optional.of(usuario));
        when(rolRepo.findByNombre("CLIENTE")).thenReturn(Optional.of(rol));

        // Simulamos la encriptación de la nueva contraseña
        when(passwordEncoder.encode("newpassword")).thenReturn("encodedNewPassword");

        // Simulamos la actualización del usuario
        when(usuarioRepo.save(any(Usuario.class))).thenReturn(usuario);

        UsuarioResponseDTO result = usuarioService.actualizarUsuario(id, dto);

        // Verificamos que los datos se han actualizado correctamente
        assertEquals("Juan Updated", result.getNombre());
        assertEquals("CLIENTE", result.getRol());
    }

    // Test de eliminar usuario
    @Test
    public void testEliminarUsuario() {
        Long id = 1L;
        Usuario usuario = new Usuario(id, "Juan", "juan@mail.com", "encodedPassword", new Rol(1L, "ADMIN"));

        // Simulamos que el usuario existe
        when(usuarioRepo.findById(id)).thenReturn(Optional.of(usuario));

        // Ejecutamos el método de eliminar usuario
        usuarioService.eliminarUsuario(id);

        // Verificamos que el repositorio de usuarios haya llamado el método delete una vez
        verify(usuarioRepo, times(1)).delete(usuario);
    }
}
