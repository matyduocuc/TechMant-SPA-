package com.example.gestion.reportes.controller;

import com.example.gestion.reportes.model.Reporte;
import com.example.gestion.reportes.services.ReporteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reportes")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    @Operation(summary = "Crear un nuevo reporte",
               description = "Crea y registra un nuevo reporte en el sistema.",
               responses = {
                   @ApiResponse(responseCode = "201", description = "Reporte creado correctamente",
                                content = @Content(schema = @Schema(implementation = Reporte.class)))
               })
    @PostMapping
    public ResponseEntity<Reporte> crear(@RequestBody Reporte reporte) {
        Reporte reporteCreado = reporteService.crearReporte(reporte);
        return ResponseEntity.status(201).body(reporteCreado);
    }

    @Operation(summary = "Listar todos los reportes",
               description = "Devuelve una lista de todos los reportes existentes.",
               responses = {
                   @ApiResponse(responseCode = "200", description = "Lista de reportes obtenida correctamente",
                                content = @Content(schema = @Schema(implementation = Reporte.class)))
               })
    @GetMapping
    public List<Reporte> listarTodos() {
        return reporteService.listarTodos();
    }

    @Operation(summary = "Reportes por cliente", description = "Obtiene los reportes asociados a un cliente.")
    @GetMapping("/cliente/{id}")
    public List<Reporte> porCliente(@PathVariable Long id) {
        return reporteService.listarPorCliente(id);
    }

    @Operation(summary = "Reportes por técnico", description = "Obtiene los reportes asignados a un técnico.")
    @GetMapping("/tecnico/{id}")
    public List<Reporte> porTecnico(@PathVariable Long id) {
        return reporteService.listarPorTecnico(id);
    }

    @Operation(summary = "Reportes por fecha", description = "Filtra reportes por una fecha dada.")
    @GetMapping("/fecha/{fecha}")
    public List<Reporte> porFecha(@PathVariable String fecha) {
        return reporteService.listarPorFecha(LocalDate.parse(fecha));
    }

    @Operation(summary = "Reportes por estado", description = "Muestra los reportes con un estado específico.")
    @GetMapping("/estado/{estado}")
    public List<Reporte> porEstado(@PathVariable String estado) {
        return reporteService.listarPorEstado(estado);
    }

    @Operation(summary = "Reportes por título", description = "Filtra reportes según el título.")
    @GetMapping("/titulo/{titulo}")
    public List<Reporte> porTitulo(@PathVariable String titulo) {
        return reporteService.listarPorTitulo(titulo);
    }

    @Operation(summary = "Actualizar reporte",
               description = "Actualiza los datos de un reporte existente.",
               responses = {
                   @ApiResponse(responseCode = "200", description = "Reporte actualizado correctamente",
                                content = @Content(schema = @Schema(implementation = Reporte.class))),
                   @ApiResponse(responseCode = "404", description = "Reporte no encontrado")
               })
    @PutMapping("/{id}")
    public ResponseEntity<Reporte> actualizarReporte(@PathVariable Long id, @RequestBody Reporte reporte) {
        Reporte reporteActualizado = reporteService.actualizarReporte(id, reporte);
        return ResponseEntity.ok(reporteActualizado);
    }

    @Operation(summary = "Eliminar reporte",
               description = "Elimina un reporte por su ID.",
               responses = {
                   @ApiResponse(responseCode = "204", description = "Reporte eliminado correctamente"),
                   @ApiResponse(responseCode = "404", description = "Reporte no encontrado")
               })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReporte(@PathVariable Long id) {
        reporteService.eliminarReporte(id);
        return ResponseEntity.noContent().build();
    }
}
