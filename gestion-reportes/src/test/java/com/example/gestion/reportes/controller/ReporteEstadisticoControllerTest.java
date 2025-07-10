package com.example.gestion.reportes.controller;

import com.example.gestion.reportes.dto.ReporteConUsuarioDTO;
import com.example.gestion.reportes.dto.UsuarioResponseDTO;
import com.example.gestion.reportes.model.Reporte;
import com.example.gestion.reportes.services.ReporteUsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReporteEstadisticoController.class)  // Asegura que solo se cargue el contexto de Spring MVC para este controlador
class ReporteEstadisticoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ReporteUsuarioService reporteUsuarioService;

    @InjectMocks
    private ReporteEstadisticoController controller;

    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
    }

    @Test
    void testDetalleUsuarios() throws Exception {
        // Preparación de los datos de prueba
        Reporte reporte = new Reporte(1L, 10L, 4L, 3L, "Revisión", "COMPLETADO", LocalDate.now(), "Reporte técnico");
        UsuarioResponseDTO cliente = new UsuarioResponseDTO(4L, "Cliente Prueba", "cliente@mail.com", "CLIENTE");
        UsuarioResponseDTO tecnico = new UsuarioResponseDTO(3L, "Tecnico Prueba", "tecnico@mail.com", "TECNICO");

        ReporteConUsuarioDTO dto = new ReporteConUsuarioDTO(reporte, cliente, tecnico);

        // Configuración del mock del servicio
        when(reporteUsuarioService.obtenerReportesConUsuarios()).thenReturn(List.of(dto));

        // Llamada al endpoint con la ruta corregida
        mockMvc.perform(get("/api/reportes-estadisticos/detalle-usuarios"))  // Asegúrate de que esta URL esté correcta
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].cliente.nombre").value("Cliente Prueba"))
                .andExpect(jsonPath("$[0].tecnico.nombre").value("Tecnico Prueba"));
    }
}
