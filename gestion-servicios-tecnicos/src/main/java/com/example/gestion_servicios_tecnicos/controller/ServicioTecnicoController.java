package com.example.gestion_servicios_tecnicos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.gestion_servicios_tecnicos.model.ServicioTecnico;
import com.example.gestion_servicios_tecnicos.services.ServicioTecnicoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/servicios-tecnicos")
public class ServicioTecnicoController {

    @Autowired
    private ServicioTecnicoService servicio;

    @Operation(summary = "Crear un nuevo servicio técnico",
               description = "Este endpoint permite registrar un nuevo servicio técnico en el sistema.",
               responses = {
                   @ApiResponse(responseCode = "200", description = "Servicio técnico creado correctamente", 
                                content = @Content(schema = @Schema(implementation = ServicioTecnico.class))),
                   @ApiResponse(responseCode = "400", description = "Datos inválidos")
               })
    @PostMapping
    public ServicioTecnico crear(@Valid @RequestBody ServicioTecnico st) {
        return servicio.crear(st);
    }

    @Operation(summary = "Obtener todos los servicios técnicos",
               description = "Este endpoint permite obtener la lista de todos los servicios técnicos registrados.",
               responses = {
                   @ApiResponse(responseCode = "200", description = "Lista de servicios técnicos obtenida correctamente", 
                                content = @Content(schema = @Schema(implementation = ServicioTecnico.class)))
               })
    @GetMapping
    public List<ServicioTecnico> listar() {
        return servicio.listar();
    }

    @Operation(summary = "Obtener servicio técnico por ID",
               description = "Este endpoint permite obtener los detalles de un servicio técnico usando su ID.",
               responses = {
                   @ApiResponse(responseCode = "200", description = "Servicio técnico encontrado", 
                                content = @Content(schema = @Schema(implementation = ServicioTecnico.class))),
                   @ApiResponse(responseCode = "404", description = "Servicio técnico no encontrado")
               })
    @GetMapping("/{id}")
    public ResponseEntity<ServicioTecnico> porId(@PathVariable Long id) {
        return servicio.porId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Obtener servicios técnicos por técnico",
               description = "Este endpoint permite obtener los servicios técnicos asignados a un técnico específico.",
               responses = {
                   @ApiResponse(responseCode = "200", description = "Lista de servicios técnicos del técnico", 
                                content = @Content(schema = @Schema(implementation = ServicioTecnico.class)))
               })
    @GetMapping("/tecnico/{tecnicoId}")
    public List<ServicioTecnico> porTecnico(@PathVariable Long tecnicoId) {
        return servicio.listarPorTecnico(tecnicoId);
    }

    @Operation(summary = "Obtener servicios técnicos por solicitud",
               description = "Este endpoint permite obtener los servicios técnicos relacionados con una solicitud.",
               responses = {
                   @ApiResponse(responseCode = "200", description = "Lista de servicios técnicos de la solicitud", 
                                content = @Content(schema = @Schema(implementation = ServicioTecnico.class)))
               })
    @GetMapping("/solicitud/{solicitudId}")
    public List<ServicioTecnico> porSolicitud(@PathVariable Long solicitudId) {
        return servicio.listarPorSolicitud(solicitudId);
    }

    @Operation(summary = "Actualizar servicio técnico",
               description = "Este endpoint permite actualizar los detalles de un servicio técnico.",
               responses = {
                   @ApiResponse(responseCode = "200", description = "Servicio técnico actualizado correctamente", 
                                content = @Content(schema = @Schema(implementation = ServicioTecnico.class))),
                   @ApiResponse(responseCode = "400", description = "Datos inválidos"),
                   @ApiResponse(responseCode = "404", description = "Servicio técnico no encontrado")
               })
    @PutMapping("/{id}")
    public ResponseEntity<ServicioTecnico> actualizar(@PathVariable Long id, @Valid @RequestBody ServicioTecnico actualizado) {
        return servicio.actualizar(id, actualizado)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar servicio técnico",
               description = "Este endpoint permite eliminar un servicio técnico usando su ID.",
               responses = {
                   @ApiResponse(responseCode = "200", description = "Servicio técnico eliminado correctamente"),
                   @ApiResponse(responseCode = "404", description = "Servicio técnico no encontrado")
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
