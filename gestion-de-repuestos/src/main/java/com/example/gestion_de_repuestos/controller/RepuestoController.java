package com.example.gestion_de_repuestos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gestion_de_repuestos.model.Repuesto;
import com.example.gestion_de_repuestos.services.RepuestoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/api/repuestos")
public class RepuestoController {
    @Autowired
    private RepuestoService servicio;

    @Operation(summary = "Agregar un nuevo repuesto",
               description = "Este endpoint permite agregar un nuevo repuesto al sistema.",
               responses = {
                   @ApiResponse(responseCode = "200", description = "Repuesto creado correctamente", 
                                content = @Content(schema = @Schema(implementation = Repuesto.class))),
                   @ApiResponse(responseCode = "400", description = "Datos inválidos")
               })
    @PostMapping
    public Repuesto agregar(@RequestBody Repuesto repuesto) {
        return servicio.crear(repuesto);
    }

    @Operation(summary = "Obtener lista de repuestos",
               description = "Este endpoint permite obtener una lista de todos los repuestos registrados en el sistema.",
               responses = {
                   @ApiResponse(responseCode = "200", description = "Lista de repuestos obtenida correctamente", 
                                content = @Content(schema = @Schema(implementation = Repuesto.class)))
               })
    @GetMapping
    public List<Repuesto> listar() {
        return servicio.listar();
    }
}
