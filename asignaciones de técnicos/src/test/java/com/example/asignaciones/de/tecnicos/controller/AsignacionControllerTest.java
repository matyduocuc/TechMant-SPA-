package com.example.asignaciones.de.tecnicos.controller;

import com.example.asignaciones.de.tecnicos.model.Asignacion;
import com.example.asignaciones.de.tecnicos.services.AsignacionService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AsignacionController.class)
public class AsignacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AsignacionService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testAsignar() throws Exception {
        Asignacion a = new Asignacion(null, 1L, 2L, LocalDate.now());
        Asignacion guardada = new Asignacion(1L, 1L, 2L, LocalDate.now());

        when(service.asignar(Mockito.any())).thenReturn(guardada);

        mockMvc.perform(post("/api/asignaciones")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(a)))
                .andExpect(status().isCreated())  // Cambié a isCreated()
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testListar() throws Exception {
        when(service.listarTodas()).thenReturn(Arrays.asList(new Asignacion(1L, 1L, 2L, LocalDate.now())));

        mockMvc.perform(get("/api/asignaciones"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }
}
