package com.example.gestion_de_ventas.controller;

import com.example.gestion_de_ventas.dto.UsuarioResponseDTO;
import com.example.gestion_de_ventas.dto.VentaConUsuarioDTO;
import com.example.gestion_de_ventas.model.Venta;
import com.example.gestion_de_ventas.services.VentaService;
import com.example.gestion_de_ventas.services.VentaUsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class VentaControllerTest {

    private MockMvc mockMvc;

    @Mock
    private VentaService ventaService;

    @Mock
    private VentaUsuarioService ventaUsuarioService;

    @InjectMocks
    private VentaController ventaController;

    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(ventaController).build();
        mapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @Test
    void testCrearVenta() throws Exception {
        Venta nueva = new Venta(null, "SSD", 2, 45.0, null, 5L);
        Venta guardada = new Venta(1L, "SSD", 2, 45.0, LocalDate.now(), 5L);

        when(ventaService.guardar(any(Venta.class))).thenReturn(guardada);

        mockMvc.perform(post("/api/ventas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(nueva)))
                .andExpect(status().isCreated())  // Cambiado de .isOk() a .isCreated() para esperar el código 201
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testListarVentas() throws Exception {
        Venta venta = new Venta(1L, "Monitor", 1, 120.0, LocalDate.now(), 3L);
        when(ventaService.listar()).thenReturn(List.of(venta));

        mockMvc.perform(get("/api/ventas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].producto").value("Monitor"));
    }

    @Test
    void testBuscarPorId() throws Exception {
        Venta venta = new Venta(1L, "Mouse", 1, 25.0, LocalDate.now(), 4L);
        when(ventaService.buscarPorId(1L)).thenReturn(Optional.of(venta));

        mockMvc.perform(get("/api/ventas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.producto").value("Mouse"));
    }

    @Test
    void testActualizarVenta() throws Exception {
        Venta actualizada = new Venta(1L, "Teclado", 2, 50.0, LocalDate.now(), 4L);
        when(ventaService.actualizar(eq(1L), any(Venta.class))).thenReturn(Optional.of(actualizada));

        mockMvc.perform(put("/api/ventas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(actualizada)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.producto").value("Teclado"));
    }

    @Test
    void testEliminarVenta() throws Exception {
        when(ventaService.eliminar(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/ventas/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDetalleConUsuario() throws Exception {
        Venta venta = new Venta(1L, "Tablet", 1, 180.0, LocalDate.now(), 6L);
        UsuarioResponseDTO usuario = new UsuarioResponseDTO(6L, "Mario", "mario@correo.com", "CLIENTE");
        VentaConUsuarioDTO dto = new VentaConUsuarioDTO(venta, usuario);

        when(ventaUsuarioService.obtenerVentasConUsuarios()).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/ventas/detalle-clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].usuario.nombre").value("Mario"))
                .andExpect(jsonPath("$[0].venta.producto").value("Tablet"));
    }
}
