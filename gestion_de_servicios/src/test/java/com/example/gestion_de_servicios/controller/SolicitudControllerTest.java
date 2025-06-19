package com.example.gestion_de_servicios.controller;

import com.example.gestion_de_servicios.model.Solicitud;
import com.example.gestion_de_servicios.services.SolicitudService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SolicitudController.class)
class SolicitudControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SolicitudService servicio;

    @Test
    void listarDebeRetornar200() throws Exception {
        Solicitud solicitud = new Solicitud(1L, "desc", "tipo", "PENDIENTE", LocalDate.now(), 1L);
        when(servicio.listar()).thenReturn(Collections.singletonList(solicitud));

        mockMvc.perform(get("/api/solicitudes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }
}
