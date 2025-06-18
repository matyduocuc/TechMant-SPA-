
package com.example.gestion.reportes.services;

import com.example.gestion.reportes.model.Reporte;
import com.example.gestion.reportes.repository.ReporteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ReporteServiceTest {

    @Mock
    private ReporteRepository reporteRepository;

    @InjectMocks
    private ReporteService reporteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearReporte() {
        Reporte reporte = new Reporte();
        reporte.setTitulo("Nuevo Reporte");

        when(reporteRepository.save(any(Reporte.class))).thenReturn(reporte);

        Reporte resultado = reporteService.crearReporte(reporte);

        assertNotNull(resultado);
        assertEquals("Nuevo Reporte", resultado.getTitulo());
        verify(reporteRepository, times(1)).save(reporte);
    }

    @Test
    void testObtenerReportes() {
        when(reporteRepository.findAll()).thenReturn(List.of(new Reporte("Reporte 1"), new Reporte("Reporte 2")));

        var reportes = reporteService.obtenerReportes();

        assertNotNull(reportes);
        assertEquals(2, reportes.size());
    }
}
