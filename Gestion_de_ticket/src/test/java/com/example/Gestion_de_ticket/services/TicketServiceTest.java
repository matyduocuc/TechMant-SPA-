package com.example.Gestion_de_ticket.services;

import com.example.Gestion_de_ticket.dto.TicketDTO;
import com.example.Gestion_de_ticket.model.Ticket;
import com.example.Gestion_de_ticket.repository.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private TicketService ticketService;

    @BeforeEach
    void setUp() {
        // Inicializa los mocks antes de cada prueba
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearTicket() {
        TicketDTO dto = new TicketDTO();
        dto.setIdUsuario(5L);
        dto.setIdEquipo(10L);
        dto.setIdSolicitud(15L);
        dto.setProblemaReportado("Pantalla no enciende");

        Ticket ticket = new Ticket(1L, 5L, 10L, 15L, "Pantalla no enciende", "EN_PROCESO", null, null, null);
        
        // Simulamos el comportamiento del método save del repositorio
        when(ticketRepository.save(Mockito.any(Ticket.class))).thenReturn(ticket);

        // Llamamos al servicio para crear el ticket
        Ticket createdTicket = ticketService.crearTicket(dto);

        // Verificamos que el ID del ticket creado no sea null
        assertTrue(createdTicket.getId() != null);
    }
}
