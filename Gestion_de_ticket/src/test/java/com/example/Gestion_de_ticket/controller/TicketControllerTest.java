package com.example.Gestion_de_ticket.controller;

import com.example.Gestion_de_ticket.model.Ticket;
import com.example.Gestion_de_ticket.services.TicketService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TicketController.class)
public class TicketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TicketService ticketService;

    @Test
    void testListarTodos() throws Exception {
        Mockito.when(ticketService.listarTodos()).thenReturn(List.of(new Ticket()));
        mockMvc.perform(get("/api/tickets"))
                .andExpect(status().isOk());
    }
}
