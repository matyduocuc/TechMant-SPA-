package com.example.gestion_servicios_tecnicos.services;

import com.example.gestion_servicios_tecnicos.model.ServicioTecnico;
import com.example.gestion_servicios_tecnicos.repository.ServicioTecnicoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ServicioTecnicoServiceTest {

    @Mock
    private ServicioTecnicoRepository repo;

    @InjectMocks
    private ServicioTecnicoService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearServicioTecnico() {
        ServicioTecnico st = new ServicioTecnico(null, 1L, 2L, 3L, "Revisión", "EN PROGRESO", null);
        when(repo.save(any())).thenReturn(st);

        ServicioTecnico creado = service.crear(st);
        assertNotNull(creado);
        assertEquals("EN PROGRESO", creado.getEstado());
    }

    @Test
    void testPorId() {
        ServicioTecnico st = new ServicioTecnico(1L, 1L, 2L, 3L, "Revisión", "EN PROGRESO", LocalDate.now());
        when(repo.findById(1L)).thenReturn(Optional.of(st));

        Optional<ServicioTecnico> encontrado = service.porId(1L);
        assertTrue(encontrado.isPresent());
        assertEquals(1L, encontrado.get().getId());
    }

    @Test
    void testActualizar() {
        ServicioTecnico original = new ServicioTecnico(1L, 1L, 2L, 3L, "Antigua", "EN PROGRESO", LocalDate.now());
        ServicioTecnico nuevo = new ServicioTecnico(null, 5L, 6L, 7L, "Nueva descripción", "FINALIZADO", LocalDate.now());

        when(repo.findById(1L)).thenReturn(Optional.of(original));
        when(repo.save(any())).thenReturn(original);

        Optional<ServicioTecnico> actualizado = service.actualizar(1L, nuevo);

        assertTrue(actualizado.isPresent());
        assertEquals("FINALIZADO", actualizado.get().getEstado());
        assertEquals("Nueva descripción", actualizado.get().getDescripcion());
        assertEquals(6L, actualizado.get().getEquipoId());
    }

    @Test
    void testEliminarExistente() {
        when(repo.existsById(1L)).thenReturn(true);
        boolean eliminado = service.eliminar(1L);
        assertTrue(eliminado);
        verify(repo).deleteById(1L);
    }

    @Test
    void testEliminarNoExistente() {
        when(repo.existsById(2L)).thenReturn(false);
        boolean eliminado = service.eliminar(2L);
        assertFalse(eliminado);
    }

    @Test
    void testListarPorTecnico() {
        when(repo.findByTecnicoId(3L)).thenReturn(List.of(new ServicioTecnico()));
        List<ServicioTecnico> resultado = service.listarPorTecnico(3L);
        assertFalse(resultado.isEmpty());
    }

    @Test
    void testListarPorSolicitud() {
        when(repo.findBySolicitudId(5L)).thenReturn(List.of(new ServicioTecnico()));
        List<ServicioTecnico> resultado = service.listarPorSolicitud(5L);
        assertFalse(resultado.isEmpty());
    }
}

