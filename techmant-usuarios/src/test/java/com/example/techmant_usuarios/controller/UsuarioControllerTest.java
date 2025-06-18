
package com.example.techmant_usuarios.controller;

import com.example.techmant_usuarios.DTOs.UsuarioRequestDTO;
import com.example.techmant_usuarios.DTOs.UsuarioResponseDTO;
import com.example.techmant_usuarios.services.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void testRegistrarUsuario() throws Exception {
        UsuarioRequestDTO request = new UsuarioRequestDTO("Juan", "juan@example.com", "1234", "CLIENTE");
        UsuarioResponseDTO response = new UsuarioResponseDTO(1L, "Juan", "juan@example.com", "CLIENTE");

        Mockito.when(usuarioService.registrar(any(UsuarioRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/usuarios/registrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan"));
    }

    @Test
    void testLogin() throws Exception {
        UsuarioRequestDTO request = new UsuarioRequestDTO(null, "juan@example.com", "1234", null);
        UsuarioResponseDTO response = new UsuarioResponseDTO(1L, "Juan", "juan@example.com", "CLIENTE");

        Mockito.when(usuarioService.login("juan@example.com", "1234")).thenReturn(response);

        mockMvc.perform(post("/usuarios/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.correo").value("juan@example.com"));
    }

    @Test
    void testObtenerUsuarios() throws Exception {
        UsuarioResponseDTO user = new UsuarioResponseDTO(1L, "Juan", "juan@example.com", "CLIENTE");

        Mockito.when(usuarioService.obtenerUsuarios()).thenReturn(List.of(user));

        mockMvc.perform(get("/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Juan"));
    }
}
