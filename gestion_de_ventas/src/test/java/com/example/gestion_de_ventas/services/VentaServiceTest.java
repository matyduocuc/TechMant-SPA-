package com.example.gestion_de_ventas.services;

import com.example.gestion_de_ventas.model.Venta;
import com.example.gestion_de_ventas.repository.VentaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class VentaServiceTest {

    @Mock
    private VentaRepository repository;

    @InjectMocks
    private VentaService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGuardarConFechaPorDefecto() {
        Venta venta = new Venta(null, "Producto", 2, 50.0, null, 1L);
        when(repository.save(any())).thenAnswer(i -> i.getArgument(0));

        Venta result = service.guardar(venta);

        assertNotNull(result.getFechaVenta());
        verify(repository).save(result);
    }

    @Test
    void testListar() {
        when(repository.findAll()).thenReturn(List.of(new Venta()));
        assertEquals(1, service.listar().size());
    }

    @Test
    void testBuscarPorId() {
        Venta venta = new Venta(1L, "Producto", 1, 100.0, LocalDate.now(), 1L);
        when(repository.findById(1L)).thenReturn(Optional.of(venta));

        Optional<Venta> result = service.buscarPorId(1L);
        assertTrue(result.isPresent());
    }

    @Test
    void testActualizar() {
        Venta actual = new Venta(1L, "P1", 1, 100.0, LocalDate.now(), 1L);
        Venta nueva = new Venta(null, "P2", 2, 200.0, LocalDate.now(), 2L);
        when(repository.findById(1L)).thenReturn(Optional.of(actual));
        when(repository.save(any())).thenReturn(actual);

        Optional<Venta> result = service.actualizar(1L, nueva);
        assertTrue(result.isPresent());
        assertEquals("P2", result.get().getProducto());
    }

    @Test
    void testEliminar() {
        when(repository.existsById(1L)).thenReturn(true);
        assertTrue(service.eliminar(1L));
        verify(repository).deleteById(1L);
    }
}
