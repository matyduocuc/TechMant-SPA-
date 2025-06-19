
package com.example.gestion_de_equipos.services;

import com.example.gestion_de_equipos.model.Equipo;
import com.example.gestion_de_equipos.repository.EquipoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class EquipoServiceTest {

    @Mock
    private EquipoRepository equipoRepository;

    @InjectMocks
    private EquipoService equipoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGuardar() {
        Equipo equipo = new Equipo(null, "Equipo A", "Marca A", "Modelo A", "Tipo A", "Descripción A");
        when(equipoRepository.save(equipo)).thenReturn(equipo);

        Equipo resultado = equipoService.guardar(equipo);

        assertNotNull(resultado);
        assertEquals("Equipo A", resultado.getNombre());
        verify(equipoRepository, times(1)).save(equipo);
    }

    @Test
    public void testListar() {
        List<Equipo> equipos = List.of(
                new Equipo(1L, "Equipo A", "Marca A", "Modelo A", "Tipo A", "Descripción A"),
                new Equipo(2L, "Equipo B", "Marca B", "Modelo B", "Tipo B", "Descripción B")
        );
        when(equipoRepository.findAll()).thenReturn(equipos);

        List<Equipo> resultado = equipoService.listar();

        assertEquals(2, resultado.size());
        assertEquals("Equipo A", resultado.get(0).getNombre());
    }

    @Test
    public void testBuscarPorId() {
        Equipo equipo = new Equipo(1L, "Equipo A", "Marca A", "Modelo A", "Tipo A", "Descripción A");
        when(equipoRepository.findById(1L)).thenReturn(Optional.of(equipo));

        Optional<Equipo> resultado = equipoService.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Equipo A", resultado.get().getNombre());
    }

    @Test
    public void testActualizar() {
        Equipo equipoExistente = new Equipo(1L, "Equipo A", "Marca A", "Modelo A", "Tipo A", "Descripción A");
        Equipo equipoActualizado = new Equipo(1L, "Equipo A Actualizado", "Marca A", "Modelo A", "Tipo A", "Descripción A");

        when(equipoRepository.findById(1L)).thenReturn(Optional.of(equipoExistente));
        when(equipoRepository.save(equipoExistente)).thenReturn(equipoActualizado);

        Optional<Equipo> resultado = equipoService.actualizar(1L, equipoActualizado);

        assertTrue(resultado.isPresent());
        assertEquals("Equipo A Actualizado", resultado.get().getNombre());
        verify(equipoRepository, times(1)).save(equipoExistente);
    }
}
