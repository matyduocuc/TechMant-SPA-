package com.example.gestion_servicios_tecnicos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.gestion_servicios_tecnicos.model.ServicioTecnico;
import com.example.gestion_servicios_tecnicos.services.ServicioTecnicoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/servicios-tecnicos")
public class ServicioTecnicoController {

    @Autowired
    private ServicioTecnicoService servicio;

    @Operation(summary = "Crear un nuevo servicio técnico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Servicio técnico creado correctamente",
                     content = @Content(schema = @Schema(implementation = ServicioTecnico.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<ServicioTecnico> crear(@Valid @RequestBody ServicioTecnico st) {
        ServicioTecnico creado = servicio.crear(st);
        return ResponseEntity.status(201).body(creado);
    }

    @Operation(summary = "Obtener todos los servicios técnicos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de servicios técnicos obtenida correctamente",
                     content = @Content(schema = @Schema(implementation = ServicioTecnico.class))),
        @ApiResponse(responseCode = "204", description = "No hay servicios técnicos registrados"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<List<ServicioTecnico>> listar() {
        List<ServicioTecnico> lista = servicio.listar();
        return lista.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(lista);
    }

    @Operation(summary = "Obtener servicio técnico por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Servicio técnico encontrado",
                     content = @Content(schema = @Schema(implementation = ServicioTecnico.class))),
        @ApiResponse(responseCode = "404", description = "Servicio técnico no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ServicioTecnico> porId(@PathVariable Long id) {
        return servicio.porId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Obtener servicios técnicos por técnico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de servicios técnicos del técnico",
                     content = @Content(schema = @Schema(implementation = ServicioTecnico.class))),
        @ApiResponse(responseCode = "204", description = "El técnico no tiene servicios registrados"),
        @ApiResponse(responseCode = "404", description = "Técnico no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/tecnico/{tecnicoId}")
    public ResponseEntity<List<ServicioTecnico>> porTecnico(@PathVariable Long tecnicoId) {
        List<ServicioTecnico> lista = servicio.listarPorTecnico(tecnicoId);
        return lista.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(lista);
    }

    @Operation(summary = "Obtener servicios técnicos por solicitud")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de servicios técnicos de la solicitud",
                     content = @Content(schema = @Schema(implementation = ServicioTecnico.class))),
        @ApiResponse(responseCode = "204", description = "No hay servicios para esta solicitud"),
        @ApiResponse(responseCode = "404", description = "Solicitud no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/solicitud/{solicitudId}")
    public ResponseEntity<List<ServicioTecnico>> porSolicitud(@PathVariable Long solicitudId) {
        List<ServicioTecnico> lista = servicio.listarPorSolicitud(solicitudId);
        return lista.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(lista);
    }

    @Operation(summary = "Actualizar servicio técnico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Servicio técnico actualizado correctamente",
                     content = @Content(schema = @Schema(implementation = ServicioTecnico.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "404", description = "Servicio técnico no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ServicioTecnico> actualizar(@PathVariable Long id, @Valid @RequestBody ServicioTecnico actualizado) {
        return servicio.actualizar(id, actualizado)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar servicio técnico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Servicio técnico eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Servicio técnico no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (servicio.eliminar(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
