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

    // Test de Registro de Usuario
    @Test
    void testRegistrarUsuario() throws Exception {
        UsuarioRequestDTO request = new UsuarioRequestDTO("Juan", "juan@mail.com", "password", "ADMIN");
        UsuarioResponseDTO response = new UsuarioResponseDTO(1L, "Juan", "juan@mail.com", "ADMIN");

        // Simulamos la respuesta del servicio
        when(usuarioService.registrar(any(UsuarioRequestDTO.class))).thenReturn(response);

        // Ejecutamos el test con la solicitud POST para registrar usuario
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
                .andExpect(status().isCreated())  // Cambié isOk() por isCreated()
                .andExpect(jsonPath("$.nombre").value("Juan"))  // Verificamos que el nombre sea correcto
                .andExpect(jsonPath("$.correo").value("juan@mail.com"))
                .andExpect(jsonPath("$.rol").value("ADMIN"));
    }

    // Test de Login de Usuario
    @Test
    void testLoginUsuario() throws Exception {
        // Datos de prueba
        when(usuarioService.login(eq("juan@mail.com"), eq("password"))).thenReturn("jwt_token_example");

        mockMvc.perform(post("/usuarios/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "correo": "juan@mail.com",
                          "contrasena": "password"
                        }
                        """))
                .andExpect(status().isOk())  // Verificamos el código 200
                .andExpect(jsonPath("$").value("jwt_token_example"));  // Verificamos el token JWT
    }

    // Test de Obtener Todos los Usuarios
    @Test
    void testObtenerTodosLosUsuarios() throws Exception {
        UsuarioResponseDTO usuario = new UsuarioResponseDTO(1L, "Juan", "juan@mail.com", "ADMIN");

        when(usuarioService.obtenerUsuarios()).thenReturn(List.of(usuario));

        mockMvc.perform(get("/usuarios"))
                .andExpect(status().isOk())  // Verificamos que el estado sea 200 OK
                .andExpect(jsonPath("$[0].nombre").value("Juan"));  // Verificamos que el nombre sea correcto
    }

    // Test de Obtener Usuario por ID
    @Test
    void testObtenerUsuarioPorId() throws Exception {
        UsuarioResponseDTO response = new UsuarioResponseDTO(1L, "Juan", "juan@mail.com", "ADMIN");

        when(usuarioService.obtenerPorId(1L)).thenReturn(Optional.of(response));

        mockMvc.perform(get("/usuarios/1"))
                .andExpect(status().isOk())  // Verificamos que el estado sea 200 OK
                .andExpect(jsonPath("$.nombre").value("Juan"))  // Verificamos que el nombre sea correcto
                .andExpect(jsonPath("$.rol").value("ADMIN"));
    }

    // Test de Actualización de Usuario
    @Test
    void testActualizarUsuario() throws Exception {
        UsuarioRequestDTO request = new UsuarioRequestDTO("Juan Updated", "juan@mail.com", "newpass", "CLIENTE");
        UsuarioResponseDTO response = new UsuarioResponseDTO(1L, "Juan Updated", "juan@mail.com", "CLIENTE");

        when(usuarioService.actualizarUsuario(eq(1L), any(UsuarioRequestDTO.class))).thenReturn(response);

        mockMvc.perform(put("/usuarios/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "nombre": "Juan Updated",
                          "correo": "juan@mail.com",
                          "contrasena": "newpass",
                          "rol": "CLIENTE"
                        }
                        """))
                .andExpect(status().isOk())  // Verificamos que el estado sea 200 OK
                .andExpect(jsonPath("$.nombre").value("Juan Updated"))  // Verificamos que el nombre sea actualizado
                .andExpect(jsonPath("$.rol").value("CLIENTE"));
    }

    // Test de Eliminar Usuario
    @Test
    void testEliminarUsuario() throws Exception {
        doNothing().when(usuarioService).eliminarUsuario(1L);

        mockMvc.perform(delete("/usuarios/1"))
                .andExpect(status().isNoContent());  // Verificamos que el estado sea 204 No Content
    }
}
