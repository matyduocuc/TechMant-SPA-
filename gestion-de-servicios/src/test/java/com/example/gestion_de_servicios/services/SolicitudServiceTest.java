package com.example.gestion_de_servicios.services;

import com.example.gestion_de_servicios.model.Solicitud;
import com.example.gestion_de_servicios.repository.SolicitudRepository;
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

class SolicitudServiceTest {

    @Mock
    private SolicitudRepository repo;

    @InjectMocks
    private SolicitudService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGuardarConDefaults() {
        Solicitud solicitud = new Solicitud();
        solicitud.setDescripcion("Revisión de equipo");
        solicitud.setTipoEquipo("Laptop");

        when(repo.save(any())).thenAnswer(i -> i.getArguments()[0]);

        Solicitud resultado = service.guardar(solicitud);

        assertEquals("PENDIENTE", resultado.getEstado());
        assertNotNull(resultado.getFechaCreacion());
    }

    @Test
    void testListar() {
        when(repo.findAll()).thenReturn(List.of(new Solicitud()));

        List<Solicitud> solicitudes = service.listar();

        assertFalse(solicitudes.isEmpty());
        verify(repo).findAll();
    }

    @Test
    void testActualizar() {
        Solicitud existente = new Solicitud(1L, "desc", "equipo", "PENDIENTE", LocalDate.now(), 2L);
        Solicitud actualizada = new Solicitud(null, "nueva desc", "nuevo equipo", "RESUELTO", LocalDate.now(), 3L);

        when(repo.findById(1L)).thenReturn(Optional.of(existente));
        when(repo.save(any())).thenReturn(existente);

        Optional<Solicitud> resultado = service.actualizar(1L, actualizada);

        assertTrue(resultado.isPresent());
        assertEquals("nueva desc", resultado.get().getDescripcion());
    }

    @Test
    void testEliminarExistente() {
        when(repo.existsById(1L)).thenReturn(true);

        boolean eliminado = service.eliminar(1L);

        assertTrue(eliminado);
        verify(repo).deleteById(1L);
    }
}
