package com.example.gestion_de_repuestos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gestion_de_repuestos.model.SolicitudRepuesto;
import com.example.gestion_de_repuestos.services.SolicitudRepuestoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/api/solicitud-repuesto")
public class SolicitudRepuestoController {
        
    @Autowired
    private SolicitudRepuestoService servicio;

    @Operation(summary = "Crear una solicitud de repuesto",
               description = "Este endpoint permite crear una nueva solicitud de repuesto.",
               responses = {
                   @ApiResponse(responseCode = "200", description = "Solicitud de repuesto creada correctamente", 
                                content = @Content(schema = @Schema(implementation = SolicitudRepuesto.class))),
                   @ApiResponse(responseCode = "400", description = "Datos inválidos")
               })
    @PostMapping
    public SolicitudRepuesto crear(@RequestBody SolicitudRepuesto sr) {
        return servicio.guardar(sr);
    }

    @Operation(summary = "Obtener todas las solicitudes de repuestos",
               description = "Este endpoint permite obtener una lista de todas las solicitudes de repuestos registradas en el sistema.",
               responses = {
                   @ApiResponse(responseCode = "200", description = "Lista de solicitudes de repuestos obtenida correctamente", 
                                content = @Content(schema = @Schema(implementation = SolicitudRepuesto.class)))
               })
    @GetMapping
    public List<SolicitudRepuesto> listarTodos() {
        return servicio.listar();
    }
}
