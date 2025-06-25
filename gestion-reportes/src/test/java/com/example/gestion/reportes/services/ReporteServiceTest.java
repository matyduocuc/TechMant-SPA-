package com.example.gestion.reportes.services;

import com.example.gestion.reportes.model.Reporte;
import com.example.gestion.reportes.repository.ReporteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
    void testListarTodos() {
        Reporte r1 = new Reporte(1L, 1L, 1L, 1L, "Desc", "ESTADO", LocalDate.now(), "Titulo 1");
        Reporte r2 = new Reporte(2L, 2L, 2L, 2L, "Desc 2", "OTRO", LocalDate.now(), "Titulo 2");

        when(reporteRepository.findAll()).thenReturn(List.of(r1, r2));

        List<Reporte> result = reporteService.listarTodos();

        assertEquals(2, result.size());
    }

    @Test
    void testListarPorCliente() {
        when(reporteRepository.findByClienteId(1L)).thenReturn(List.of(new Reporte()));
        List<Reporte> result = reporteService.listarPorCliente(1L);
        assertFalse(result.isEmpty());
    }

    @Test
    void testListarPorTecnico() {
        when(reporteRepository.findByTecnicoId(1L)).thenReturn(List.of(new Reporte()));
        List<Reporte> result = reporteService.listarPorTecnico(1L);
        assertFalse(result.isEmpty());
    }

    @Test
    void testListarPorFecha() {
        LocalDate fecha = LocalDate.now();
        when(reporteRepository.findByFechaReporte(fecha)).thenReturn(List.of(new Reporte()));
        List<Reporte> result = reporteService.listarPorFecha(fecha);
        assertFalse(result.isEmpty());
    }

    @Test
    void testListarPorEstado() {
        when(reporteRepository.findByEstadoFinalIgnoreCase("estado")).thenReturn(List.of(new Reporte()));
        List<Reporte> result = reporteService.listarPorEstado("estado");
        assertFalse(result.isEmpty());
    }

    @Test
    void testListarPorTitulo() {
        when(reporteRepository.findByEstadoFinalIgnoreCase("titulo")).thenReturn(List.of(new Reporte()));
        List<Reporte> result = reporteService.listarPorTitulo("titulo");
        assertFalse(result.isEmpty());
    }

    @Test
    void testActualizarReporte() {
        Reporte original = new Reporte(1L, 1L, 1L, 1L, "desc", "estado", LocalDate.now(), "titulo");
        Reporte actualizado = new Reporte(null, 2L, 2L, 2L, "desc 2", "nuevo", LocalDate.now(), "nuevo titulo");

        Reporte resultadoEsperado = new Reporte(1L, 2L, 2L, 2L, "desc 2", "nuevo", LocalDate.now(), "nuevo titulo");

        when(reporteRepository.findById(1L)).thenReturn(Optional.of(original));
        when(reporteRepository.save(any(Reporte.class))).thenReturn(resultadoEsperado);

        Reporte resultado = reporteService.actualizarReporte(1L, actualizado);
        assertEquals("desc 2", resultado.getDescripcion());
        assertEquals("nuevo titulo", resultado.getTitulo());
    }


    @Test
    void testEliminarReporte() {
        Reporte reporte = new Reporte();
        when(reporteRepository.findById(1L)).thenReturn(Optional.of(reporte));

        assertDoesNotThrow(() -> reporteService.eliminarReporte(1L));
        verify(reporteRepository).delete(reporte);
    }

    @Test
    void testEliminarReporteNoExistente() {
        when(reporteRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> reporteService.eliminarReporte(99L));
    }
} 

