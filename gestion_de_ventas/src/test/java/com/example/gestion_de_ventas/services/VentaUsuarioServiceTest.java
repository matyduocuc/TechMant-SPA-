package com.example.gestion_de_ventas.services;

import com.example.gestion_de_ventas.dto.UsuarioResponseDTO;
import com.example.gestion_de_ventas.dto.VentaConUsuarioDTO;
import com.example.gestion_de_ventas.model.Venta;
import com.example.gestion_de_ventas.repository.VentaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class VentaUsuarioServiceTest {

    @Mock
    private VentaRepository repository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private VentaUsuarioService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testObtenerVentasConUsuarios() {
        Venta venta = new Venta(1L, "Producto", 1, 100.0, LocalDate.now(), 5L);
        UsuarioResponseDTO usuario = new UsuarioResponseDTO(5L, "Ana", "ana@mail.com", "CLIENTE");

        when(repository.findAll()).thenReturn(List.of(venta));
        when(restTemplate.getForObject("http://localhost:8081/usuarios/5", UsuarioResponseDTO.class))
                .thenReturn(usuario);

        List<VentaConUsuarioDTO> resultado = service.obtenerVentasConUsuarios();

        assertEquals(1, resultado.size());
        assertEquals("Ana", resultado.get(0).getUsuario().getNombre());
    }
}
