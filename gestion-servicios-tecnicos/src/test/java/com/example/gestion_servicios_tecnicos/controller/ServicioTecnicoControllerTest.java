package com.example.gestion_servicios_tecnicos.controller;

import com.example.gestion_servicios_tecnicos.model.ServicioTecnico;
import com.example.gestion_servicios_tecnicos.services.ServicioTecnicoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ServicioTecnicoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ServicioTecnicoService service;

    @InjectMocks
    private ServicioTecnicoController controller;

    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
    }

    @Test
    void testCrear() throws Exception {
        ServicioTecnico st = new ServicioTecnico(1L, 10L, 20L, 30L, "Desc", "EN PROGRESO", LocalDate.now());
        when(service.crear(any(ServicioTecnico.class))).thenReturn(st);

        mockMvc.perform(post("/api/servicios-tecnicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(st)))
                .andExpect(status().isCreated()) // Esperamos el código 201 Created
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testListar() throws Exception {
        when(service.listar()).thenReturn(List.of(new ServicioTecnico()));

        mockMvc.perform(get("/api/servicios-tecnicos"))
                .andExpect(status().isOk());
    }

    @Test
    void testPorId() throws Exception {
        ServicioTecnico st = new ServicioTecnico(1L, 10L, 20L, 30L, "Desc", "FINALIZADO", LocalDate.now());
        when(service.porId(1L)).thenReturn(Optional.of(st));

        mockMvc.perform(get("/api/servicios-tecnicos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testActualizar() throws Exception {
        ServicioTecnico st = new ServicioTecnico(1L, 10L, 20L, 30L, "Actualizado", "FINALIZADO", LocalDate.now());
        when(service.actualizar(eq(1L), any())).thenReturn(Optional.of(st));

        mockMvc.perform(put("/api/servicios-tecnicos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(st)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.descripcion").value("Actualizado"));
    }

    @Test
    void testEliminar() throws Exception {
        when(service.eliminar(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/servicios-tecnicos/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testListarPorTecnico() throws Exception {
        when(service.listarPorTecnico(30L)).thenReturn(List.of(new ServicioTecnico()));

        mockMvc.perform(get("/api/servicios-tecnicos/tecnico/30"))
                .andExpect(status().isOk());
    }

    @Test
    void testListarPorSolicitud() throws Exception {
        when(service.listarPorSolicitud(10L)).thenReturn(List.of(new ServicioTecnico()));

        mockMvc.perform(get("/api/servicios-tecnicos/solicitud/10"))
                .andExpect(status().isOk());
    }
}
