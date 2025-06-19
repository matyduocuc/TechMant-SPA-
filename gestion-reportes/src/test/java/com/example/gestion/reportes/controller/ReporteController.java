package com.example.gestion.reportes.controller;

import com.example.gestion.reportes.model.Reporte;
import com.example.gestion.reportes.services.ReporteService;
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

    // Crear un reporte (POST)
    @PostMapping
    public ResponseEntity<Reporte> crear(@RequestBody Reporte reporte) {
        Reporte reporteCreado = reporteService.crearReporte(reporte);
        return ResponseEntity.status(201).body(reporteCreado);  // Retorna 201 Created
    }

    // Obtener todos los reportes (GET)
    @GetMapping
    public List<Reporte> listarTodos() {
        return reporteService.listarTodos();
    }

    // Obtener reportes por cliente (GET)
    @GetMapping("/cliente/{id}")
    public List<Reporte> porCliente(@PathVariable Long id) {
        return reporteService.listarPorCliente(id);
    }

    // Obtener reportes por técnico (GET)
    @GetMapping("/tecnico/{id}")
    public List<Reporte> porTecnico(@PathVariable Long id) {
        return reporteService.listarPorTecnico(id);
    }

    // Obtener reportes por fecha (GET)
    @GetMapping("/fecha/{fecha}")
    public List<Reporte> porFecha(@PathVariable String fecha) {
        return reporteService.listarPorFecha(LocalDate.parse(fecha));
    }

    // Obtener reportes por estado (GET)
    @GetMapping("/estado/{estado}")
    public List<Reporte> porEstado(@PathVariable String estado) {
        return reporteService.listarPorEstado(estado);
    }

    // Obtener reportes por título (GET)
    @GetMapping("/titulo/{titulo}")
    public List<Reporte> porTitulo(@PathVariable String titulo) {
        return reporteService.listarPorTitulo(titulo);
    }

    // Actualizar un reporte (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<Reporte> actualizarReporte(@PathVariable Long id, @RequestBody Reporte reporte) {
        Reporte reporteActualizado = reporteService.actualizarReporte(id, reporte);
        return ResponseEntity.ok(reporteActualizado);  // Retorna 200 OK con el reporte actualizado
    }

    // Eliminar un reporte (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReporte(@PathVariable Long id) {
        reporteService.eliminarReporte(id);
        return ResponseEntity.noContent().build();  // Retorna 204 No Content después de la eliminación
    }
}
