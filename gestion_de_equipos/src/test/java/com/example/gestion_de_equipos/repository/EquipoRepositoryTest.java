
package com.example.gestion_de_equipos.repository;

import com.example.gestion_de_equipos.model.Equipo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class EquipoRepositoryTest {

    @Mock
    private EquipoRepository equipoRepository;

    @InjectMocks
    private EquipoRepositoryTest equipoRepositoryTest;

    @Test
    public void testFindById() {
        Long equipoId = 1L;
        Equipo equipo = new Equipo(equipoId, "Equipo A", "Marca A", "Modelo A", "Tipo A", "Descripción A");
        when(equipoRepository.findById(equipoId)).thenReturn(java.util.Optional.of(equipo));

        var result = equipoRepository.findById(equipoId);

        assertTrue(result.isPresent());
        assertEquals("Equipo A", result.get().getNombre());
    }
}
