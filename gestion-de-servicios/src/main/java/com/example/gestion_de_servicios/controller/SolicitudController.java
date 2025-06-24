package com.example.gestion_de_servicios.controller;

import com.example.gestion_de_servicios.dto.SolicitudConUsuarioDTO;
import com.example.gestion_de_servicios.model.Solicitud;
import com.example.gestion_de_servicios.services.SolicitudService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/solicitudes")
public class SolicitudController {

    @Autowired
    private SolicitudService servicio;

    @Operation(summary = "Crear una nueva solicitud de servicio")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Solicitud creada con éxito", content = @Content(schema = @Schema(implementation = Solicitud.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public Solicitud crear(@Valid @RequestBody Solicitud solicitud) {
        return servicio.guardar(solicitud);
    }

    @Operation(summary = "Obtener todas las solicitudes")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de solicitudes obtenida correctamente", content = @Content(schema = @Schema(implementation = Solicitud.class)))
    })
    @GetMapping
    public List<Solicitud> listar() {
        return servicio.listar();
    }

    @Operation(summary = "Obtener una solicitud por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Solicitud encontrada", content = @Content(schema = @Schema(implementation = Solicitud.class))),
        @ApiResponse(responseCode = "404", description = "Solicitud no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Solicitud> porId(@PathVariable Long id) {
        return servicio.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Listar solicitudes por ID del cliente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Solicitudes del cliente obtenidas correctamente", content = @Content(schema = @Schema(implementation = Solicitud.class)))
    })
    @GetMapping("/cliente/{clienteId}")
    public List<Solicitud> listarPorCliente(@PathVariable Long clienteId) {
        return servicio.listarPorCliente(clienteId);
    }

    @Operation(summary = "Actualizar una solicitud existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Solicitud actualizada correctamente", content = @Content(schema = @Schema(implementation = Solicitud.class))),
        @ApiResponse(responseCode = "404", description = "Solicitud no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Solicitud> actualizar(@PathVariable Long id, @Valid @RequestBody Solicitud datosActualizados) {
        return servicio.actualizar(id, datosActualizados)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar una solicitud por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Solicitud eliminada con éxito"),
        @ApiResponse(responseCode = "404", description = "Solicitud no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (servicio.eliminar(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Listar solicitudes con información del cliente",
               description = "Devuelve todas las solicitudes con los datos del cliente asociados desde el microservicio techmant-usuarios")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Solicitudes con clientes obtenidas correctamente", content = @Content(schema = @Schema(implementation = SolicitudConUsuarioDTO.class))),
        @ApiResponse(responseCode = "500", description = "Error al obtener los datos de usuario")
    })
    @GetMapping("/detalle-clientes")
    public List<SolicitudConUsuarioDTO> detalleClientes() {
        return servicio.listarConUsuario();
    }
}
