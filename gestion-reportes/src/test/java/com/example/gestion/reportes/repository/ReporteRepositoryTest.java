
package com.example.gestion.reportes.repository;

import com.example.gestion.reportes.model.Reporte;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ReporteRepositoryTest {

    @Autowired
    private ReporteRepository reporteRepository;

    @Test
    void testGuardarReporte() {
        Reporte reporte = new Reporte();
        reporte.setTitulo("Reporte Guardado");

        Reporte reporteGuardado = reporteRepository.save(reporte);

        assertNotNull(reporteGuardado.getId());
        assertEquals("Reporte Guardado", reporteGuardado.getTitulo());
    }

    @Test
    void testEncontrarReportePorTitulo() {
        Reporte reporte = new Reporte();
        reporte.setTitulo("Reporte de Prueba");

        reporteRepository.save(reporte);

        Reporte encontrado = reporteRepository.findByTitulo("Reporte de Prueba");

        assertNotNull(encontrado);
        assertEquals("Reporte de Prueba", encontrado.getTitulo());
    }
}
