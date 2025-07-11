package com.example.techmant_usuarios.controller;

import com.example.techmant_usuarios.DTOs.UsuarioRequestDTO;
import com.example.techmant_usuarios.DTOs.UsuarioResponseDTO;
import com.example.techmant_usuarios.services.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UsuarioControllerTest {

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(usuarioController).build();
    }

    // Test de registro de usuario
    @Test
    void testRegistrarUsuario() throws Exception {
        UsuarioRequestDTO request = new UsuarioRequestDTO("Juan", "juan@mail.com", "password", "ADMIN");
        UsuarioResponseDTO response = new UsuarioResponseDTO(1L, "Juan", "juan@mail.com", "ADMIN");

        when(usuarioService.registrar(any(UsuarioRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/usuarios/registrar")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "nombre": "Juan",
                          "correo": "juan@mail.com",
                          "contrasena": "password",
                          "rol": "ADMIN"
                        }
                        """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Juan"))
                .andExpect(jsonPath("$.correo").value("juan@mail.com"))
                .andExpect(jsonPath("$.rol").value("ADMIN"));
    }

    // Test de login de usuario
    @Test
    void testLoginUsuario() throws Exception {
        when(usuarioService.login(eq("juan@mail.com"), eq("password"))).thenReturn("jwt_token_example");

        mockMvc.perform(post("/usuarios/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "correo": "juan@mail.com",
                          "contrasena": "password"
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("jwt_token_example"));
    }

    // Test de obtener todos los usuarios
    @Test
    void testObtenerTodosLosUsuarios() throws Exception {
        UsuarioResponseDTO usuario = new UsuarioResponseDTO(1L, "Juan", "juan@mail.com", "ADMIN");

        when(usuarioService.obtenerUsuarios()).thenReturn(List.of(usuario));

        mockMvc.perform(get("/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Juan"));
    }

    // Test de obtener usuario por ID
    @Test
    void testObtenerUsuarioPorId() throws Exception {
        UsuarioResponseDTO response = new UsuarioResponseDTO(1L, "Juan", "juan@mail.com", "ADMIN");

        when(usuarioService.obtenerPorId(1L)).thenReturn(Optional.of(response));

        mockMvc.perform(get("/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan"))
                .andExpect(jsonPath("$.correo").value("juan@mail.com"))
                .andExpect(jsonPath("$.rol").value("ADMIN"));
    }

    // Test de actualizar usuario
    @Test
    void testActualizarUsuario() throws Exception {
        UsuarioRequestDTO dto = new UsuarioRequestDTO("Juan Updated", "juan@mail.com", "newpassword", "CLIENTE");
        UsuarioResponseDTO response = new UsuarioResponseDTO(1L, "Juan Updated", "juan@mail.com", "CLIENTE");

        when(usuarioService.actualizarUsuario(eq(1L), any(UsuarioRequestDTO.class))).thenReturn(response);

        mockMvc.perform(put("/usuarios/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "nombre": "Juan Updated",
                          "correo": "juan@mail.com",
                          "contrasena": "newpassword",
                          "rol": "CLIENTE"
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan Updated"))
                .andExpect(jsonPath("$.rol").value("CLIENTE"));
    }

    // Test de eliminar usuario
    @Test
    void testEliminarUsuario() throws Exception {
        doNothing().when(usuarioService).eliminarUsuario(1L);

        mockMvc.perform(delete("/usuarios/1"))
                .andExpect(status().isNoContent());
    }
}
