package com.example.asignaciones.de.tecnicos.services;

import com.example.asignaciones.de.tecnicos.model.Asignacion;
import com.example.asignaciones.de.tecnicos.repository.AsignacionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class AsignacionServiceTest {

    @Mock
    private AsignacionRepository repository;

    @InjectMocks
    private AsignacionService service;

    public AsignacionServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAsignar() {
        Asignacion asignacion = new Asignacion(null, 1L, 2L, LocalDate.now());
        when(repository.save(asignacion)).thenReturn(new Asignacion(1L, 1L, 2L, asignacion.getFechaAsignacion()));

        Asignacion resultado = service.asignar(asignacion);

        assertThat(resultado.getId()).isEqualTo(1L);
        verify(repository).save(asignacion);
    }

    @Test
    void testListarTodas() {
        List<Asignacion> asignaciones = Arrays.asList(
            new Asignacion(1L, 1L, 2L, LocalDate.now()),
            new Asignacion(2L, 2L, 3L, LocalDate.now())
        );

        when(repository.findAll()).thenReturn(asignaciones);

        List<Asignacion> resultado = service.listarTodas();

        assertThat(resultado).hasSize(2);
    }

    @Test
    void testListarPorTecnico() {
        when(repository.findByTecnicoId(2L)).thenReturn(List.of(
            new Asignacion(1L, 1L, 2L, LocalDate.now())
        ));

        List<Asignacion> resultado = service.listarPorTecnico(2L);

        assertThat(resultado).hasSize(1);
    }
}
