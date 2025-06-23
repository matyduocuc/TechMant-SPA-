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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @Operation(summary = "Crear un nuevo Ticket")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Ticket creado con éxito"),
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta")
    })
    @PostMapping
    public Ticket crearTicket(@Valid @RequestBody TicketDTO dto) {
        return ticketService.crearTicket(dto);
    }

    @Operation(summary = "Obtener todos los Tickets")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de Tickets obtenida con éxito")
    })
    @GetMapping
    public List<Ticket> listarTodos() {
        return ticketService.listarTodos();
    }

    @Operation(summary = "Obtener un Ticket por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ticket encontrado"),
        @ApiResponse(responseCode = "404", description = "Ticket no encontrado")
    })
    @GetMapping("/{id}")
    public Optional<Ticket> obtenerPorId(@PathVariable Long id) {
        return ticketService.obtenerPorId(id);
    }

    @Operation(summary = "Actualizar un Ticket por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ticket actualizado con éxito"),
        @ApiResponse(responseCode = "404", description = "Ticket no encontrado")
    })
    @PutMapping("/{id}")
    public Optional<Ticket> actualizarTicket(
            @PathVariable Long id,
            @RequestParam String diagnostico,
            @RequestParam String estado) {
        return ticketService.actualizarTicket(id, diagnostico, estado);
    }

    @Operation(summary = "Eliminar un Ticket por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Ticket eliminado con éxito"),
        @ApiResponse(responseCode = "404", description = "Ticket no encontrado")
    })
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        ticketService.eliminar(id);
    }
}