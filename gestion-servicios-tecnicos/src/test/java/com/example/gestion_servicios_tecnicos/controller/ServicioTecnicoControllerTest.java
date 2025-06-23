package com.example.gestion_servicios_tecnicos.controller;


import com.example.gestion_servicios_tecnicos.services.ServicioTecnicoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ServicioTecnicoController.class)
public class ServicioTecnicoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServicioTecnicoService service;

    @Test
    void testListarServicios() throws Exception {
        when(service.listar()).thenReturn(List.of());

        mockMvc.perform(get("/api/servicios-tecnicos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
