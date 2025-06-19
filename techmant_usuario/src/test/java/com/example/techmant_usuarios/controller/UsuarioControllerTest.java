
package com.example.techmant_usuarios.controller;

import com.example.techmant_usuarios.DTOs.UsuarioRequestDTO;
import com.example.techmant_usuarios.DTOs.UsuarioResponseDTO;
import com.example.techmant_usuarios.services.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

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
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(usuarioController).build();
    }

    @Test
    public void testRegistrarUsuario() throws Exception {
        UsuarioRequestDTO requestDTO = new UsuarioRequestDTO("Juan", "juan@mail.com", "password", "ADMIN");
        UsuarioResponseDTO responseDTO = new UsuarioResponseDTO(1L, "Juan", "juan@mail.com", "ADMIN");

        when(usuarioService.registrar(requestDTO)).thenReturn(responseDTO);

        mockMvc.perform(post("/usuarios/registrar")
                .contentType("application/json")
                .content("{"nombre":"Juan","correo":"juan@mail.com","contrasena":"password","rol":"ADMIN"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan"))
                .andExpect(jsonPath("$.correo").value("juan@mail.com"))
                .andExpect(jsonPath("$.rol").value("ADMIN"));

        verify(usuarioService, times(1)).registrar(requestDTO);
    }
}
