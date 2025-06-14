package com.example.Gestion_de_ticket.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Gestion_de_ticket.dto.TicketDTO;
import com.example.Gestion_de_ticket.model.Ticket;
import com.example.Gestion_de_ticket.services.TicketService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping
    public Ticket crearTicket(@Valid @RequestBody TicketDTO dto) {
        return ticketService.crearTicket(dto);
    }

    @GetMapping
    public List<Ticket> listarTodos() {
        return ticketService.listarTodos();
    }

    @GetMapping("/{id}")
    public Optional<Ticket> obtenerPorId(@PathVariable Long id) {
        return ticketService.obtenerPorId(id);
    }

    @PutMapping("/{id}")
    public Optional<Ticket> actualizarTicket(
            @PathVariable Long id,
            @RequestParam String diagnostico,
            @RequestParam String estado) {
        return ticketService.actualizarTicket(id, diagnostico, estado);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        ticketService.eliminar(id);
    }
}
