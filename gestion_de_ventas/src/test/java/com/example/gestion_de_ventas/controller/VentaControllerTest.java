package com.example.gestion_de_ventas.controller;

import com.example.gestion_de_ventas.model.Venta;
import com.example.gestion_de_ventas.services.VentaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(VentaController.class)
public class VentaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VentaService ventaService;

    @Test
    void testPorId() throws Exception {
        Venta venta = new Venta(1L, 1L, 2L, 100.0, LocalDate.now());
        when(ventaService.obtenerPorId(1L)).thenReturn(Optional.of(venta));

        mockMvc.perform(get("/api/ventas/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }
}
