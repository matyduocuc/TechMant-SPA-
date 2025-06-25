package com.example.gestion.reportes.controller;

import com.example.gestion.reportes.services.ReporteEstadisticoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ReporteEstadisticoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ReporteEstadisticoService servicio;

    @InjectMocks
    private ReporteEstadisticoController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testListarVentas() throws Exception {
        when(servicio.obtenerTodasLasVentas()).thenReturn(List.of(Map.of("producto", "SSD")));

        mockMvc.perform(get("/api/reportes-estadisticos/ventas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].producto").value("SSD"));
    }

    @Test
    void testTotalRecaudado() throws Exception {
        when(servicio.calcularTotalRecaudado()).thenReturn(99.99);

        mockMvc.perform(get("/api/reportes-estadisticos/ventas/total"))
                .andExpect(status().isOk())
                .andExpect(content().string("99.99"));
    }

    @Test
    void testTotalPorTecnico() throws Exception {
        when(servicio.totalRecaudadoPorTecnico()).thenReturn(Map.of(1L, 150.0));

        mockMvc.perform(get("/api/reportes-estadisticos/ventas/total-por-tecnico"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.1").value(150.0));
    }

    @Test
    void testTotalPorCliente() throws Exception {
        when(servicio.totalRecaudadoPorCliente()).thenReturn(Map.of(4L, 300.0));

        mockMvc.perform(get("/api/reportes-estadisticos/ventas/total-por-cliente"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.4").value(300.0));
    }

    @Test
    void testActualizarVenta() throws Exception {
        Map<String, Object> datos = Map.of("producto", "RAM");

        when(servicio.actualizarVenta(eq(1L), any())).thenReturn(datos);

        mockMvc.perform(put("/api/reportes-estadisticos/ventas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"producto\":\"RAM\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.producto").value("RAM"));
    }

    @Test
    void testEliminarVenta() throws Exception {
        doNothing().when(servicio).eliminarVenta(1L);

        mockMvc.perform(delete("/api/reportes-estadisticos/ventas/1"))
                .andExpect(status().isNoContent());
    }
}

