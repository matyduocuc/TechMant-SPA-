package com.example.gestion.reportes.repository;

import com.example.gestion.reportes.model.Reporte;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ReporteRepositoryTest {

    @Autowired
    private ReporteRepository reporteRepository;

    @BeforeEach
    void setUp() {
        // Limpiar la base de datos antes de cada prueba (opcional)
        reporteRepository.deleteAll();
    }

    @Test
    void testGuardarReporte() {
        // Crear un nuevo reporte
        Reporte reporte = new Reporte();
        reporte.setTitulo("Reporte Guardado");
        reporte.setDescripcion("Descripción del reporte");
        reporte.setEstadoFinal("REPARADO");
        reporte.setFechaReporte(LocalDate.now());

        // Guardar el reporte en la base de datos
        Reporte reporteGuardado = reporteRepository.save(reporte);

        // Verificar que el reporte fue guardado correctamente
        assertNotNull(reporteGuardado.getId());
        assertEquals("Reporte Guardado", reporteGuardado.getTitulo());
    }

    @Test
    void testEncontrarReportePorTitulo() {
        // Crear y guardar un reporte
        Reporte reporte = new Reporte();
        reporte.setTitulo("Reporte de Prueba");
        reporte.setDescripcion("Descripción del reporte");
        reporte.setEstadoFinal("REPARADO");
        reporte.setFechaReporte(LocalDate.now());
        reporteRepository.save(reporte);

        // Buscar reportes por el título
        List<Reporte> reportes = reporteRepository.findByTituloIgnoreCase("Reporte de Prueba");

        // Verificar que se encontró el reporte y que el título es el esperado
        assertNotNull(reportes);
        assertFalse(reportes.isEmpty());
        assertEquals("Reporte de Prueba", reportes.get(0).getTitulo());
    }

    @Test
    void testEncontrarReportesPorClienteId() {
        // Crear y guardar un reporte con clienteId
        Reporte reporte = new Reporte();
        reporte.setTitulo("Reporte Cliente");
        reporte.setClienteId(1L);
        reporte.setDescripcion("Descripción del reporte del cliente");
        reporte.setEstadoFinal("NO REPARADO");
        reporte.setFechaReporte(LocalDate.now());
        reporteRepository.save(reporte);

        // Buscar reportes por clienteId
        List<Reporte> reportes = reporteRepository.findByClienteId(1L);

        // Verificar que se encontró el reporte
        assertNotNull(reportes);
        assertFalse(reportes.isEmpty());
        assertEquals("Reporte Cliente", reportes.get(0).getTitulo());
    }

    @Test
    void testEncontrarReportesPorTecnicoId() {
        // Crear y guardar un reporte con tecnicoId
        Reporte reporte = new Reporte();
        reporte.setTitulo("Reporte Técnico");
        reporte.setTecnicoId(2L);
        reporte.setDescripcion("Descripción del reporte del técnico");
        reporte.setEstadoFinal("REPARADO");
        reporte.setFechaReporte(LocalDate.now());
        reporteRepository.save(reporte);

        // Buscar reportes por tecnicoId
        List<Reporte> reportes = reporteRepository.findByTecnicoId(2L);

        // Verificar que se encontró el reporte
        assertNotNull(reportes);
        assertFalse(reportes.isEmpty());
        assertEquals("Reporte Técnico", reportes.get(0).getTitulo());
    }

    @Test
    void testEncontrarReportesPorFecha() {
        // Crear y guardar un reporte con una fecha específica
        Reporte reporte = new Reporte();
        reporte.setTitulo("Reporte de Fecha");
        reporte.setDescripcion("Descripción del reporte con fecha específica");
        reporte.setEstadoFinal("REPARADO");
        reporte.setFechaReporte(LocalDate.of(2025, 6, 18));  // Fecha específica
        reporteRepository.save(reporte);

        // Buscar reportes por fecha
        List<Reporte> reportes = reporteRepository.findByFechaReporte(LocalDate.of(2025, 6, 18));

        // Verificar que se encontró el reporte
        assertNotNull(reportes);
        assertFalse(reportes.isEmpty());
        assertEquals("Reporte de Fecha", reportes.get(0).getTitulo());
    }

    @Test
    void testEncontrarReportesPorEstado() {
        // Crear y guardar un reporte con un estado específico
        Reporte reporte = new Reporte();
        reporte.setTitulo("Reporte Estado");
        reporte.setDescripcion("Descripción del reporte con estado");
        reporte.setEstadoFinal("NO REPARADO");
        reporte.setFechaReporte(LocalDate.now());
        reporteRepository.save(reporte);

        // Buscar reportes por estado (ignorando mayúsculas/minúsculas)
        List<Reporte> reportes = reporteRepository.findByEstadoFinalIgnoreCase("no reparado");

        // Verificar que se encontró el reporte con el estado correcto
        assertNotNull(reportes);
        assertFalse(reportes.isEmpty());
        assertEquals("NO REPARADO", reportes.get(0).getEstadoFinal());
    }

    public ReporteRepository getReporteRepository() {
        return reporteRepository;
    }

    public void setReporteRepository(ReporteRepository reporteRepository) {
        this.reporteRepository = reporteRepository;
    }
}


