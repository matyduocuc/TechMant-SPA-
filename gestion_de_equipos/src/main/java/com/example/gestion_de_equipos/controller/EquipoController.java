package com.example.gestion_de_equipos.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.gestion_de_equipos.model.Equipo;
import com.example.gestion_de_equipos.services.EquipoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/equipos")
public class EquipoController {

    @Autowired
    private EquipoService servicio;

    @Operation(summary = "Obtener una lista de todos los equipos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de equipos encontrada", content = @Content(schema = @Schema(implementation = Equipo.class)))
    })
    @GetMapping
    public List<Equipo> listar() {
        return servicio.listar();
    }

    @Operation(summary = "Crear un nuevo equipo")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Equipo creado con éxito", content = @Content(schema = @Schema(implementation = Equipo.class))),
        @ApiResponse(responseCode = "400", description = "Error en los datos proporcionados")
    })
    @PostMapping
    public ResponseEntity<Equipo> crear(@Valid @RequestBody Equipo equipo) {
        Equipo nuevoEquipo = servicio.guardar(equipo);
        return ResponseEntity.ok(nuevoEquipo);
    }
    
    @Operation(summary = "Obtener un equipo por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Equipo encontrado", content = @Content(schema = @Schema(implementation = Equipo.class))),
        @ApiResponse(responseCode = "404", description = "Equipo no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Equipo> porId(@PathVariable Long id) {
        return servicio.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @Operation(summary = "Actualizar los detalles de un equipo")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Equipo actualizado exitosamente", content = @Content(schema = @Schema(implementation = Equipo.class))),
        @ApiResponse(responseCode = "404", description = "Equipo no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Equipo> actualizar(@PathVariable Long id, @Valid @RequestBody Equipo equipo) {
        return servicio.actualizar(id, equipo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar un equipo por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Equipo eliminado"),
        @ApiResponse(responseCode = "404", description = "Equipo no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (servicio.eliminar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
