package com.example.gestion_de_repuestos.controller;

import com.example.gestion_de_repuestos.dto.SolicitudDTO;
import com.example.gestion_de_repuestos.dto.SolicitudRepuestoConDetalleDTO;
import com.example.gestion_de_repuestos.model.Repuesto;
import com.example.gestion_de_repuestos.services.RepuestoService;
import com.example.gestion_de_repuestos.services.SolicitudRepuestoDetalleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RepuestoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RepuestoService repuestoService;

    @Mock
    private SolicitudRepuestoDetalleService detalleService;

    @InjectMocks
    private RepuestoController controller;

    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        org.mockito.MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
    }

    @Test
    void testListar() throws Exception {
        when(repuestoService.listar()).thenReturn(List.of(new Repuesto()));
        mockMvc.perform(get("/api/repuestos"))
                .andExpect(status().isOk());
    }

    @Test
    void testAgregar() throws Exception {
        Repuesto repuesto = new Repuesto(null, "RAM", "Memoria RAM", 10, 50.0);
        when(repuestoService.crear(any())).thenReturn(new Repuesto(1L, "RAM", "Memoria RAM", 10, 50.0));

        mockMvc.perform(post("/api/repuestos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(repuesto)))
                .andExpect(status().isCreated()) // Cambiado a 201 Created
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testObtenerPorId() throws Exception {
        Repuesto repuesto = new Repuesto(1L, "SSD", "Disco sólido", 5, 70.0);
        when(repuestoService.buscarPorId(1L)).thenReturn(Optional.of(repuesto));

        mockMvc.perform(get("/api/repuestos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("SSD"));
    }

    @Test
    void testActualizar() throws Exception {
        Repuesto nuevo = new Repuesto(null, "HDD", "Actualizado", 15, 60.0);
        Repuesto actualizado = new Repuesto(1L, "HDD", "Actualizado", 15, 60.0);

        when(repuestoService.actualizar(eq(1L), any())).thenReturn(Optional.of(actualizado));

        mockMvc.perform(put("/api/repuestos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(nuevo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("HDD"));
    }

    @Test
    void testEliminar() throws Exception {
        when(repuestoService.eliminar(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/repuestos/1"))
                .andExpect(status().isNoContent());
    }
}
