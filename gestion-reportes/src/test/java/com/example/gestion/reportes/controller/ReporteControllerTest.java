
package com.example.gestion.reportes.controller;

import com.example.gestion.reportes.model.Reporte;
import com.example.gestion.reportes.services.ReporteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReporteController.class)
public class ReporteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReporteService reporteService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void testCrearReporte() throws Exception {
        Reporte reporte = new Reporte();
        reporte.setTitulo("Nuevo Reporte");

        Mockito.when(reporteService.crearReporte(any(Reporte.class))).thenReturn(reporte);

        mockMvc.perform(post("/reportes")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(reporte)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Nuevo Reporte"));
    }

    @Test
    void testObtenerReportes() throws Exception {
        Mockito.when(reporteService.obtenerReportes()).thenReturn(List.of(new Reporte("Reporte 1"), new Reporte("Reporte 2")));

        mockMvc.perform(get("/reportes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].titulo").value("Reporte 1"))
                .andExpect(jsonPath("$[1].titulo").value("Reporte 2"));
    }
}
