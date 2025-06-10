package com.example.Gestion_de_ticket.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Gestion_de_ticket.dto.TicketDTO;
import com.example.Gestion_de_ticket.model.Ticket;
import com.example.Gestion_de_ticket.repository.TicketRepository;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    public Ticket crearTicket(TicketDTO dto) {
        Ticket ticket = Ticket.builder()
                .idUsuario(dto.getIdUsuario())
                .idEquipo(dto.getIdEquipo())
                .idSolicitud(dto.getIdSolicitud())
                .problemaReportado(dto.getProblemaReportado())
                .estado("NUEVO")
                .fechaCreacion(LocalDateTime.now())
                .fechaActualizacion(LocalDateTime.now())
                .build();
        return ticketRepository.save(ticket);
    }

    public List<Ticket> listarTodos() {
        return ticketRepository.findAll();
    }

    public Optional<Ticket> obtenerPorId(Long id) {
        return ticketRepository.findById(id);
    }

    public Optional<Ticket> actualizarTicket(Long id, String diagnostico, String estado) {
        return ticketRepository.findById(id).map(ticket -> {
            ticket.setDiagnosticoTecnico(diagnostico);
            ticket.setEstado(estado);
            ticket.setFechaActualizacion(LocalDateTime.now());
            return ticketRepository.save(ticket);
        });
    }

    public void eliminar(Long id) {
        ticketRepository.deleteById(id);
    }
}