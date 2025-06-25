package com.example.gestion.reportes.services;

import com.example.gestion.reportes.dto.ReporteConUsuarioDTO;
import com.example.gestion.reportes.dto.UsuarioResponseDTO;
import com.example.gestion.reportes.model.Reporte;
import com.example.gestion.reportes.repository.ReporteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReporteUsuarioServiceTest {

    @Mock
    private ReporteRepository reporteRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ReporteUsuarioService reporteUsuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testObtenerReportesConUsuarios() {
        Reporte reporte = new Reporte(1L, 10L, 4L, 2L, "Se arregló", "REPARADO", LocalDate.now(), "Título");
        UsuarioResponseDTO cliente = new UsuarioResponseDTO(4L, "Cliente Uno", "cliente@correo.com", "CLIENTE");
        UsuarioResponseDTO tecnico = new UsuarioResponseDTO(2L, "Técnico Dos", "tecnico@correo.com", "TECNICO");

        when(reporteRepository.findAll()).thenReturn(List.of(reporte));
        when(restTemplate.getForObject("http://localhost:8081/usuarios/4", UsuarioResponseDTO.class)).thenReturn(cliente);
        when(restTemplate.getForObject("http://localhost:8081/usuarios/2", UsuarioResponseDTO.class)).thenReturn(tecnico);

        List<ReporteConUsuarioDTO> resultado = reporteUsuarioService.obtenerReportesConUsuarios();

        assertEquals(1, resultado.size());
        assertEquals("Cliente Uno", resultado.get(0).getCliente().getNombre());
        assertEquals("Técnico Dos", resultado.get(0).getTecnico().getNombre());
    }
}
