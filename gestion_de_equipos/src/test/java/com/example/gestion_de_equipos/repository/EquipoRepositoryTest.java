package com.example.gestion_de_equipos.repository;

import com.example.gestion_de_equipos.model.Equipo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class EquipoRepositoryTest {

    @Mock
    private EquipoRepository equipoRepository;  // Simulamos el repositorio

    @InjectMocks
    private EquipoRepositoryTest equipoRepositoryTest;  // Se inyecta en el test

    @BeforeEach
    public void setUp() {
        // Inicializa los mocks antes de cada test
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindById() {
        // Creamos un equipo simulado
        Long equipoId = 1L;
        Equipo equipo = new Equipo(equipoId, "Equipo A", "Marca A", "Modelo A", "Tipo A", "Descripción A");

        // Simulamos el comportamiento de findById
        when(equipoRepository.findById(equipoId)).thenReturn(Optional.of(equipo));

        // Llamamos al método del repositorio
        Optional<Equipo> result = equipoRepository.findById(equipoId);

        // Verificamos que el equipo no sea null y que el nombre del equipo sea el esperado
        assertTrue(result.isPresent());
        assertEquals("Equipo A", result.get().getNombre());
        verify(equipoRepository, times(1)).findById(equipoId);  // Verificamos que findById se haya llamado
    }
}

