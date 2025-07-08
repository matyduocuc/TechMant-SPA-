package com.example.Gestion_de_ticket.controller;

import com.example.Gestion_de_ticket.model.Ticket;
import com.example.Gestion_de_ticket.services.TicketService;
import com.example.Gestion_de_ticket.services.TicketUsuarioService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(TicketController.class)
public class TicketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TicketService ticketService;

    @MockBean
    private TicketUsuarioService ticketUsuarioService; // Aseguramos que también esté simulado el servicio de usuario

    @Test
    void testListarTodos() throws Exception {
        // Simulamos que el servicio devuelve una lista con un solo ticket
        Ticket ticket = new Ticket(1L, 5L, 10L, 15L, "Pantalla no enciende", "EN_PROCESO", null, null, null);
        Mockito.when(ticketService.listarTodos()).thenReturn(List.of(ticket));

        // Realizamos una solicitud GET al endpoint "/api/tickets"
        mockMvc.perform(get("/api/tickets"))
                .andExpect(status().isOk())  // Verificamos que la respuesta sea 200 OK
                .andExpect(jsonPath("$[0].problemaReportado").value("Pantalla no enciende"));  // Verificamos el valor del campo "problemaReportado"
    }

    @Test
    void testObtenerTicketPorId() throws Exception {
        Long ticketId = 1L;
        Ticket ticket = new Ticket(ticketId, 5L, 10L, 15L, "Pantalla no enciende", "EN_PROCESO", null, null, null);

        // Simulamos que el servicio devuelve el ticket con el ID solicitado
        Mockito.when(ticketService.obtenerPorId(ticketId)).thenReturn(java.util.Optional.of(ticket));

        // Realizamos una solicitud GET al endpoint "/api/tickets/{id}"
        mockMvc.perform(get("/api/tickets/{id}", ticketId))
                .andExpect(status().isOk())  // Verificamos que la respuesta sea 200 OK
                .andExpect(jsonPath("$.problemaReportado").value("Pantalla no enciende"));  // Verificamos el valor del campo "problemaReportado"
    }

    @Test
    void testObtenerTicketPorIdNoEncontrado() throws Exception {
        Long ticketId = 999L; // Usamos un ID que no existe
        // Simulamos que el servicio no devuelve ningún ticket para el ID proporcionado
        Mockito.when(ticketService.obtenerPorId(ticketId)).thenReturn(java.util.Optional.empty());

        // Realizamos una solicitud GET al endpoint "/api/tickets/{id}"
        mockMvc.perform(get("/api/tickets/{id}", ticketId))
                .andExpect(status().isNotFound());  // Verificamos que la respuesta sea 404 (no encontrado)
    }
}
