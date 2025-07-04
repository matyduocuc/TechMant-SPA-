package com.example.Gestion_de_ticket.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Gestion_de_ticket.dto.TicketDTO;
import com.example.Gestion_de_ticket.model.Ticket;
import com.example.Gestion_de_ticket.services.TicketService;
import com.example.Gestion_de_ticket.services.TicketUsuarioService;

import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private TicketUsuarioService ticketUsuarioService;

    @Autowired
    private TicketService ticketService;

    @Operation(summary = "Crear un nuevo Ticket", description = "Crea un nuevo ticket con los datos del cliente y equipo.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Ticket creado con éxito", content = @Content(schema = @Schema(implementation = Ticket.class))),
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta"),
        @ApiResponse(responseCode = "500", description = "Error interno")
    })
    @PostMapping
    public ResponseEntity<Ticket> crearTicket(@Valid @RequestBody TicketDTO dto) {
        Ticket ticket = ticketService.crearTicket(dto);
        return ResponseEntity.status(201).body(ticket);
    }

    @Operation(summary = "Obtener todos los Tickets", description = "Devuelve la lista completa de tickets.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de Tickets obtenida con éxito"),
        @ApiResponse(responseCode = "204", description = "No hay tickets disponibles")
    })
    @GetMapping
    public List<Ticket> listarTodos() {
        return ticketService.listarTodos();
    }

    @Operation(summary = "Obtener un Ticket por ID", description = "Busca un ticket específico por su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ticket encontrado"),
        @ApiResponse(responseCode = "404", description = "Ticket no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Ticket>> obtenerPorId(@PathVariable Long id) {
        Optional<Ticket> ticket = ticketService.obtenerPorId(id);
        return ticket.isPresent() ? ResponseEntity.ok(ticket) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Actualizar un Ticket por ID", description = "Actualiza el diagnóstico y estado de un ticket existente.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ticket actualizado con éxito"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "404", description = "Ticket no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Optional<Ticket>> actualizarTicket(
            @PathVariable Long id,
            @RequestParam String diagnostico,
            @RequestParam String estado) {
        Optional<Ticket> actualizado = ticketService.actualizarTicket(id, diagnostico, estado);
        return actualizado.isPresent() ? ResponseEntity.ok(actualizado) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Eliminar un Ticket por ID", description = "Elimina un ticket según su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Ticket eliminado con éxito"),
        @ApiResponse(responseCode = "404", description = "Ticket no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        ticketService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Listar Tickets con información del Usuario",
               description = "Este endpoint devuelve todos los tickets con los datos del usuario asociados, obtenidos desde el microservicio techmant-usuarios.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tickets con usuario obtenidos correctamente"),
        @ApiResponse(responseCode = "500", description = "Error al obtener los datos de usuario")
    })
    @GetMapping("/detalle-usuarios")
    public List<Map<String, Object>> ticketsConUsuario() {
        return ticketUsuarioService.obtenerTicketsConUsuario();
    }
}
