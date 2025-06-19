
package com.example.gestion_de_equipos.controller;

import com.example.gestion_de_equipos.model.Equipo;
import com.example.gestion_de_equipos.services.EquipoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class EquipoControllerTest {

    @Mock
    private EquipoService equipoService;

    @InjectMocks
    private EquipoController equipoController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(equipoController).build();
    }

    @Test
    public void testCrearEquipo() throws Exception {
        Equipo equipo = new Equipo(null, "Equipo A", "Marca A", "Modelo A", "Tipo A", "Descripción A");
        when(equipoService.guardar(equipo)).thenReturn(equipo);

        mockMvc.perform(post("/api/equipos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{"nombre":"Equipo A","marca":"Marca A","modelo":"Modelo A","tipo":"Tipo A","descripcion":"Descripción A"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Equipo A"))
                .andExpect(jsonPath("$.marca").value("Marca A"));

        verify(equipoService, times(1)).guardar(equipo);
    }

    @Test
    public void testListarEquipos() throws Exception {
        mockMvc.perform(get("/api/equipos"))
                .andExpect(status().isOk());

        verify(equipoService, times(1)).listar();
    }

    @Test
    public void testObtenerEquipoPorId() throws Exception {
        Long equipoId = 1L;
        Equipo equipo = new Equipo(equipoId, "Equipo A", "Marca A", "Modelo A", "Tipo A", "Descripción A");
        when(equipoService.buscarPorId(equipoId)).thenReturn(java.util.Optional.of(equipo));

        mockMvc.perform(get("/api/equipos/{id}", equipoId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Equipo A"))
                .andExpect(jsonPath("$.marca").value("Marca A"));

        verify(equipoService, times(1)).buscarPorId(equipoId);
    }
}
