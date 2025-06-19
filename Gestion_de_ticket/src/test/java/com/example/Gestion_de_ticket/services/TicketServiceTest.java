package com.example.Gestion_de_ticket.services;

import com.example.Gestion_de_ticket.dto.TicketDTO;
import com.example.Gestion_de_ticket.model.Ticket;
import com.example.Gestion_de_ticket.repository.TicketRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private TicketService ticketService;

    @Test
    void testCrearTicket() {
        // Arrange
        TicketDTO dto = new TicketDTO();
        dto.setIdUsuario(1L);
        dto.setIdEquipo(2L);
        dto.setIdSolicitud(3L);
        dto.setProblemaReportado("Pantalla rota");

        // Simula comportamiento del repository
        when(ticketRepository.save(any(Ticket.class))).thenAnswer(invocation -> {
            Ticket t = invocation.getArgument(0);
            t.setId(1L); // simulamos ID generado
            return t;
        });

        // Act
        Ticket result = ticketService.crearTicket(dto);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getProblemaReportado()).isEqualTo("Pantalla rota");
        verify(ticketRepository).save(any(Ticket.class));
    }
}
