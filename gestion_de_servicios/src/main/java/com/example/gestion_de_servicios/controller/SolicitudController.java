package com.example.gestion_de_servicios.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.gestion_de_servicios.dto.SolicitudConUsuarioDTO;
import com.example.gestion_de_servicios.model.Solicitud;
import com.example.gestion_de_servicios.services.SolicitudService;
import com.example.gestion_de_servicios.services.SolicitudUsuarioService;

import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/api/solicitudes")
public class SolicitudController {

    @Autowired
    private SolicitudService servicio;

    @Autowired
    private SolicitudUsuarioService solicitudUsuarioService;

    @Operation(summary = "Crear una nueva solicitud de servicio")
    @ApiResponse(responseCode = "201", description = "Solicitud creada con éxito", content = @Content(schema = @Schema(implementation = Solicitud.class)))
    @PostMapping
    public Solicitud crear(@Valid @RequestBody Solicitud solicitud) {
        return servicio.guardar(solicitud);
    }

    @Operation(summary = "Obtener todas las solicitudes")
    @ApiResponse(responseCode = "200", description = "Lista de solicitudes obtenidas", content = @Content(schema = @Schema(implementation = Solicitud.class)))
    @GetMapping
    public List<Solicitud> listar() {
        return servicio.listar();
    }

    @Operation(summary = "Obtener solicitud por ID")
    @ApiResponse(responseCode = "200", description = "Solicitud encontrada", content = @Content(schema = @Schema(implementation = Solicitud.class)))
    @ApiResponse(responseCode = "404", description = "Solicitud no encontrada")
    @GetMapping("/{id}")
    public ResponseEntity<Solicitud> porId(@PathVariable Long id) {
        return servicio.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Obtener solicitudes por cliente")
    @ApiResponse(responseCode = "200", description = "Lista de solicitudes por cliente", content = @Content(schema = @Schema(implementation = Solicitud.class)))
    @GetMapping("/cliente/{clienteId}")
    public List<Solicitud> listarPorCliente(@PathVariable Long clienteId) {
        return servicio.listarPorCliente(clienteId);
    }

    @Operation(summary = "Actualizar solicitud existente")
    @ApiResponse(responseCode = "200", description = "Solicitud actualizada con éxito", content = @Content(schema = @Schema(implementation = Solicitud.class)))
    @ApiResponse(responseCode = "404", description = "Solicitud no encontrada")
    @PutMapping("/{id}")
    public ResponseEntity<Solicitud> actualizar(@PathVariable Long id, @Valid @RequestBody Solicitud datosActualizados) {
        return servicio.actualizar(id, datosActualizados)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar solicitud por ID")
    @ApiResponse(responseCode = "204", description = "Solicitud eliminada con éxito")
    @ApiResponse(responseCode = "404", description = "Solicitud no encontrada")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (servicio.eliminar(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
        summary = "Obtener todas las solicitudes con los datos del cliente",
        description = "Devuelve cada solicitud incluyendo la información del cliente (nombre, correo, rol) desde el microservicio techmant-usuarios."
    )
    @ApiResponse(responseCode = "200", description = "Solicitudes con información de usuario obtenidas correctamente", content = @Content(schema = @Schema(implementation = SolicitudConUsuarioDTO.class)))
    @GetMapping("/detalle-usuario")
    public List<SolicitudConUsuarioDTO> solicitudesConUsuario() {
        return solicitudUsuarioService.listarSolicitudesConUsuario();
    }
}