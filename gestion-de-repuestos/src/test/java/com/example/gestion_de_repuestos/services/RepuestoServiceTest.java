package com.example.gestion_de_repuestos.services;

import com.example.gestion_de_repuestos.model.Repuesto;
import com.example.gestion_de_repuestos.repository.RepuestoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class RepuestoServiceTest {

    @Mock
    private RepuestoRepository repo;

    @InjectMocks
    private RepuestoService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListar() {
        when(repo.findAll()).thenReturn(Collections.emptyList());
        List<Repuesto> resultado = service.listar();
        assertThat(resultado).isEmpty();
    }

    @Test
    void testCrear() {
        Repuesto repuesto = new Repuesto(null, "RAM", "Memoria RAM", 10, 50.0);
        when(repo.save(any())).thenReturn(repuesto);

        Repuesto creado = service.crear(repuesto);
        assertNotNull(creado);
        assertEquals("RAM", creado.getNombre());
    }

    @Test
    void testBuscarPorIdExistente() {
        Repuesto repuesto = new Repuesto(1L, "SSD", "Disco sólido", 5, 70.0);
        when(repo.findById(1L)).thenReturn(Optional.of(repuesto));

        Optional<Repuesto> encontrado = service.buscarPorId(1L);
        assertTrue(encontrado.isPresent());
        assertEquals("SSD", encontrado.get().getNombre());
    }

    @Test
    void testActualizar() {
        Repuesto existente = new Repuesto(1L, "HDD", "Disco duro", 8, 40.0);
        Repuesto nuevo = new Repuesto(null, "HDD 1TB", "Disco duro actualizado", 15, 45.0);
        when(repo.findById(1L)).thenReturn(Optional.of(existente));
        when(repo.save(any())).thenReturn(existente);

        Optional<Repuesto> actualizado = service.actualizar(1L, nuevo);

        assertTrue(actualizado.isPresent());
        assertEquals("HDD 1TB", actualizado.get().getNombre());
        assertEquals(15, actualizado.get().getStock());
    }

    @Test
    void testEliminarExistente() {
        when(repo.existsById(1L)).thenReturn(true);
        boolean eliminado = service.eliminar(1L);
        assertTrue(eliminado);
        verify(repo, times(1)).deleteById(1L);
    }

    @Test
    void testEliminarNoExistente() {
        when(repo.existsById(2L)).thenReturn(false);
        boolean eliminado = service.eliminar(2L);
        assertFalse(eliminado);
    }
}
