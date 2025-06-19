package com.example.gestion_servicios_tecnicos.services;

import com.example.gestion_servicios_tecnicos.model.ServicioTecnico;
import com.example.gestion_servicios_tecnicos.repository.ServicioTecnicoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ServicioTecnicoServiceTest {

    @Mock
    private ServicioTecnicoRepository repo;

    @InjectMocks
    private ServicioTecnicoService service;

    public ServicioTecnicoServiceTest() {
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
}
