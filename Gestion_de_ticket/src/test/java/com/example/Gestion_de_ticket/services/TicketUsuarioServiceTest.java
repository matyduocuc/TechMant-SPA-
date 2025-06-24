package com.example.Gestion_de_ticket.services;

import com.example.Gestion_de_ticket.model.Ticket;
import com.example.Gestion_de_ticket.repository.TicketRepository;
import com.example.Gestion_de_ticket.dto.UsuarioResponseDTO;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TicketUsuarioServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private TicketUsuarioService ticketUsuarioService;

    @Test
    void testObtenerTicketsConUsuario() {
        // Arrange
        LocalDateTime now = LocalDateTime.of(2024, 6, 1, 12, 0);

        Ticket ticket = Ticket.builder()
                .id(1L)
                .idUsuario(10L)
                .idEquipo(2L)
                .idSolicitud(3L)
                .problemaReportado("No enciende")
                .estado("NUEVO")
                .fechaCreacion(now)
                .fechaActualizacion(now)
                .build();

        UsuarioResponseDTO usuario = new UsuarioResponseDTO();
        usuario.setId(10L);
        usuario.setNombre("Juan Pérez");
        usuario.setCorreo("juan@correo.cl");
        usuario.setRol("CLIENTE");

        when(ticketRepository.findAll()).thenReturn(List.of(ticket));
        when(restTemplate.getForObject("http://localhost:8081/usuarios/10", UsuarioResponseDTO.class))
                .thenReturn(usuario);

        // Act
        List<Map<String, Object>> resultado = ticketUsuarioService.obtenerTicketsConUsuario();

        // Assert
        assertThat(resultado).hasSize(1);
        Map<String, Object> ticketMap = resultado.get(0);

        assertThat(ticketMap.get("id")).isEqualTo(1L);
        assertThat(ticketMap.get("problemaReportado")).isEqualTo("No enciende");
        assertThat(ticketMap.get("estado")).isEqualTo("NUEVO");
        assertThat(ticketMap.get("idEquipo")).isEqualTo(2L);
        assertThat(ticketMap.get("idSolicitud")).isEqualTo(3L);
        assertThat(ticketMap.get("usuario")).isEqualTo(usuario);

        verify(ticketRepository).findAll();
        verify(restTemplate).getForObject("http://localhost:8081/usuarios/10", UsuarioResponseDTO.class);
    }
}