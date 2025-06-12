package com.example.gestion.reportes.controller;

import com.example.gestion.reportes.services.ReporteEstadisticoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reportes-estadisticos")
public class ReporteEstadisticoController {

    @Autowired
    private ReporteEstadisticoService servicio;

    @GetMapping("/ventas")
    public List<Map<String, Object>> listarVentas() {
        return servicio.obtenerTodasLasVentas();
    }

    @GetMapping("/ventas/total")
    public Double totalRecaudado() {
        return servicio.calcularTotalRecaudado();
    }

    @GetMapping("/ventas/total-por-tecnico")
    public Map<Long, Double> totalPorTecnico() {
        return servicio.totalRecaudadoPorTecnico();
    }

    @GetMapping("/ventas/total-por-cliente")
    public Map<Long, Double> totalPorCliente() {
        return servicio.totalRecaudadoPorCliente();
    }

    @PutMapping("/ventas/{id}")
    public ResponseEntity<Map<String, Object>> actualizarVenta(@PathVariable Long id, @RequestBody Map<String, Object> datosVenta) {
        try {
            Map<String, Object> ventaActualizada = servicio.actualizarVenta(id, datosVenta);
            return ResponseEntity.ok(ventaActualizada);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @DeleteMapping("/ventas/{id}")
    public ResponseEntity<Void> eliminarVenta(@PathVariable Long id) {
        try {
            servicio.eliminarVenta(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
