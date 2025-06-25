package com.example.gestion.reportes.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReporteEstadisticoServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ReporteEstadisticoService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testObtenerTodasLasVentas() {
        Map<String, Object>[] mockResponse = new Map[]{
                Map.of("producto", "Laptop", "totalVenta", 120.0)
        };

        when(restTemplate.getForObject(anyString(), eq(Map[].class))).thenReturn(mockResponse);

        List<Map<String, Object>> ventas = service.obtenerTodasLasVentas();

        assertEquals(1, ventas.size());
        assertEquals("Laptop", ventas.get(0).get("producto"));
    }

    @Test
    void testCalcularTotalRecaudado() {
        Map<String, Object>[] mockResponse = new Map[]{
                Map.of("totalVenta", 100.0),
                Map.of("totalVenta", 200.0)
        };

        when(restTemplate.getForObject(anyString(), eq(Map[].class))).thenReturn(mockResponse);

        double total = service.calcularTotalRecaudado();
        assertEquals(300.0, total);
    }

    @Test
    void testTotalRecaudadoPorTecnico() {
        Map<String, Object>[] mockResponse = new Map[]{
                Map.of("tecnicoId", 1L, "totalVenta", 50.0),
                Map.of("tecnicoId", 1L, "totalVenta", 100.0),
                Map.of("tecnicoId", 2L, "totalVenta", 75.0)
        };

        when(restTemplate.getForObject(anyString(), eq(Map[].class))).thenReturn(mockResponse);

        Map<Long, Double> resultado = service.totalRecaudadoPorTecnico();

        assertEquals(2, resultado.size());
        assertEquals(150.0, resultado.get(1L));
        assertEquals(75.0, resultado.get(2L));
    }

    @Test
    void testTotalRecaudadoPorCliente() {
        Map<String, Object>[] mockResponse = new Map[]{
                Map.of("clienteId", 4L, "totalVenta", 80.0),
                Map.of("clienteId", 4L, "totalVenta", 20.0),
                Map.of("clienteId", 5L, "totalVenta", 100.0)
        };

        when(restTemplate.getForObject(anyString(), eq(Map[].class))).thenReturn(mockResponse);

        Map<Long, Double> resultado = service.totalRecaudadoPorCliente();

        assertEquals(2, resultado.size());
        assertEquals(100.0, resultado.get(4L));
        assertEquals(100.0, resultado.get(5L));
    }

    @Test
    void testActualizarVenta() {
        Map<String, Object> datos = Map.of("producto", "Teclado");

        doNothing().when(restTemplate).put(eq("http://localhost:8086/api/ventas/10"), eq(datos));

        Map<String, Object> resultado = service.actualizarVenta(10L, datos);

        assertEquals("Teclado", resultado.get("producto"));
        verify(restTemplate).put("http://localhost:8086/api/ventas/10", datos);
    }

    @Test
    void testEliminarVenta() {
        doNothing().when(restTemplate).delete("http://localhost:8086/api/ventas/15");

        assertDoesNotThrow(() -> service.eliminarVenta(15L));
        verify(restTemplate).delete("http://localhost:8086/api/ventas/15");
    }
}
