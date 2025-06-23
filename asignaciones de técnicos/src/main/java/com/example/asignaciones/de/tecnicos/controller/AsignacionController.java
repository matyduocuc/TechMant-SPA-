package com.example.asignaciones.de.tecnicos.controller;
import com.example.asignaciones.de.tecnicos.model.Asignacion;
import com.example.asignaciones.de.tecnicos.services.AsignacionService;
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
@RequestMapping("/api/asignaciones")
public class AsignacionController {

    @Autowired
    private AsignacionService service;

    @Operation(summary = "Asignar un técnico a una solicitud")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Asignación realizada con éxito", 
                     content = @Content(schema = @Schema(implementation = Asignacion.class))),
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta o no válida"),
        @ApiResponse(responseCode = "404", description = "Técnico o solicitud no encontrada")
    })
    @PostMapping
    public Asignacion asignar(@RequestBody Asignacion asignacion) {
        return service.asignar(asignacion);
    }

    @Operation(summary = "Obtener una lista de todas las asignaciones de técnicos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de asignaciones de técnicos encontrada", 
                     content = @Content(schema = @Schema(implementation = Asignacion.class)))
    })
    @GetMapping
    public List<Asignacion> listar() {
        return service.listarTodas();
    }

    @Operation(summary = "Obtener asignación de técnico por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Asignación encontrada", 
                     content = @Content(schema = @Schema(implementation = Asignacion.class))),
        @ApiResponse(responseCode = "404", description = "Asignación no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Asignacion> porId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Actualizar asignación de técnico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Asignación actualizada con éxito", 
                     content = @Content(schema = @Schema(implementation = Asignacion.class))),
        @ApiResponse(responseCode = "404", description = "Asignación no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Asignacion> actualizar(@PathVariable Long id, @RequestBody Asignacion datosActualizados) {
        return service.actualizar(id, datosActualizados)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar asignación de técnico por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Asignación eliminada con éxito"),
        @ApiResponse(responseCode = "404", description = "Asignación no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (service.eliminar(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
