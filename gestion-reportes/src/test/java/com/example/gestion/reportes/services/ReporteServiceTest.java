package com.example.gestion.reportes.services;

import com.example.gestion.reportes.model.Reporte;
import com.example.gestion.reportes.repository.ReporteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

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
        // Crear el reporte simulado
        Reporte reporte = new Reporte();
        reporte.setTitulo("Nuevo Reporte");

        // Simular la respuesta del método save
        when(reporteRepository.save(any(Reporte.class))).thenReturn(reporte);

        // Llamar al método del servicio
        Reporte resultado = reporteService.crearReporte(reporte);

        // Verificar que el resultado es correcto
        assertNotNull(resultado);
        assertEquals("Nuevo Reporte", resultado.getTitulo());

        // Verificar que el método save fue llamado una vez
        verify(reporteRepository, times(1)).save(reporte);
    }

    @Test
    void testObtenerReportes() {
        // Simular el comportamiento del método findAll
        when(reporteRepository.findAll()).thenReturn(List.of(
        ));

        // Llamar al método del servicio
        List<Reporte> reportes = reporteService.obtenerReportes();

        // Verificar que la lista no está vacía y tiene el tamaño esperado
        assertNotNull(reportes);
        assertEquals(2, reportes.size());  // Verificar que se devuelven 2 reportes
    }
}
