package com.example.gestion_de_repuestos.controller;

import com.example.gestion_de_repuestos.dto.SolicitudRepuestoConDetalleDTO;
import com.example.gestion_de_repuestos.model.Repuesto;
import com.example.gestion_de_repuestos.services.RepuestoService;
import com.example.gestion_de_repuestos.services.SolicitudRepuestoDetalleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/repuestos")
public class RepuestoController {

    @Autowired
    private RepuestoService servicio;

    @Autowired
    private SolicitudRepuestoDetalleService detalleService;

    @Operation(summary = "Agregar un nuevo repuesto", description = "Este endpoint permite agregar un nuevo repuesto al sistema.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Repuesto creado correctamente", content = @Content(schema = @Schema(implementation = Repuesto.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "500", description = "Error interno")
    })
    @PostMapping
    public ResponseEntity<Repuesto> agregar(@RequestBody Repuesto repuesto) {
        Repuesto creado = servicio.crear(repuesto);
        return ResponseEntity.status(201).body(creado);
    }

    @Operation(summary = "Obtener lista de repuestos", description = "Lista todos los repuestos registrados.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
        @ApiResponse(responseCode = "204", description = "No hay repuestos disponibles"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public List<Repuesto> listar() {
        return servicio.listar();
    }

    @Operation(summary = "Obtener un repuesto por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Repuesto encontrado", content = @Content(schema = @Schema(implementation = Repuesto.class))),
        @ApiResponse(responseCode = "404", description = "Repuesto no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Repuesto> obtenerPorId(@PathVariable Long id) {
        return servicio.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Actualizar un repuesto")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Repuesto actualizado correctamente", content = @Content(schema = @Schema(implementation = Repuesto.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "404", description = "Repuesto no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Repuesto> actualizar(@PathVariable Long id, @RequestBody Repuesto nuevo) {
        return servicio.actualizar(id, nuevo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar un repuesto")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Repuesto eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Repuesto no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (servicio.eliminar(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Listar solicitudes de repuesto con detalle de solicitud")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Listado combinado obtenido correctamente", content = @Content(schema = @Schema(implementation = SolicitudRepuestoConDetalleDTO.class))),
        @ApiResponse(responseCode = "500", description = "Error al obtener los datos combinados")
    })
    @GetMapping("/solicitudes-detalle")
    public List<SolicitudRepuestoConDetalleDTO> listarConSolicitudes() {
        return detalleService.listarConSolicitud();
    }
}
