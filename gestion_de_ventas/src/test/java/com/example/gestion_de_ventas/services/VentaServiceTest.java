package com.example.gestion_de_ventas.services;

import com.example.gestion_de_ventas.model.Venta;
import com.example.gestion_de_ventas.repository.VentaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class VentaServiceTest {

    @Mock
    private VentaRepository ventaRepository;

    @InjectMocks
    private VentaService ventaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearVenta() {
        Venta venta = new Venta(null, 1L, 2L, 150.0, null);
        Venta guardada = new Venta(1L, 1L, 2L, 150.0, LocalDate.now());

        when(ventaRepository.save(any(Venta.class))).thenReturn(guardada);

        Venta resultado = ventaService.crearVenta(venta);

        assertThat(resultado.getId()).isEqualTo(1L);
        verify(ventaRepository).save(any(Venta.class));
    }

    @Test
    void testObtenerPorId() {
        Venta venta = new Venta(1L, 1L, 2L, 200.0, LocalDate.now());
        when(ventaRepository.findById(1L)).thenReturn(Optional.of(venta));

        Optional<Venta> resultado = ventaService.obtenerPorId(1L);

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getTotalVenta()).isEqualTo(200.0);
    }

    @Test
    void testEliminarVenta() {
        when(ventaRepository.existsById(1L)).thenReturn(true);
        boolean eliminado = ventaService.eliminarVenta(1L);

        assertThat(eliminado).isTrue();
        verify(ventaRepository).deleteById(1L);
    }
}