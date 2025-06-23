package com.example.gestion.reportes.controller;

import com.example.gestion.reportes.services.ReporteEstadisticoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(summary = "Listar todas las ventas",
               description = "Retorna una lista de todas las ventas registradas.",
               responses = {
                   @ApiResponse(responseCode = "200", description = "Lista de ventas obtenida correctamente",
                                content = @Content(schema = @Schema(implementation = Map.class)))
               })
    @GetMapping("/ventas")
    public List<Map<String, Object>> listarVentas() {
        return servicio.obtenerTodasLasVentas();
    }

    @Operation(summary = "Total recaudado general",
               description = "Retorna el total recaudado por todas las ventas.",
               responses = {
                   @ApiResponse(responseCode = "200", description = "Total recaudado calculado correctamente",
                                content = @Content(schema = @Schema(implementation = Double.class)))
               })
    @GetMapping("/ventas/total")
    public Double totalRecaudado() {
        return servicio.calcularTotalRecaudado();
    }

    @Operation(summary = "Total recaudado por técnico",
               description = "Muestra el total recaudado agrupado por cada técnico.",
               responses = {
                   @ApiResponse(responseCode = "200", description = "Totales por técnico calculados correctamente",
                                content = @Content(schema = @Schema(implementation = Map.class)))
               })
    @GetMapping("/ventas/total-por-tecnico")
    public Map<Long, Double> totalPorTecnico() {
        return servicio.totalRecaudadoPorTecnico();
    }

    @Operation(summary = "Total recaudado por cliente",
               description = "Muestra el total recaudado agrupado por cada cliente.",
               responses = {
                   @ApiResponse(responseCode = "200", description = "Totales por cliente calculados correctamente",
                                content = @Content(schema = @Schema(implementation = Map.class)))
               })
    @GetMapping("/ventas/total-por-cliente")
    public Map<Long, Double> totalPorCliente() {
        return servicio.totalRecaudadoPorCliente();
    }

    @Operation(summary = "Actualizar una venta",
               description = "Permite actualizar los datos de una venta específica.",
               responses = {
                   @ApiResponse(responseCode = "200", description = "Venta actualizada correctamente",
                                content = @Content(schema = @Schema(implementation = Map.class))),
                   @ApiResponse(responseCode = "400", description = "Error en los datos enviados")
               })
    @PutMapping("/ventas/{id}")
    public ResponseEntity<Map<String, Object>> actualizarVenta(@PathVariable Long id,
                                                               @RequestBody Map<String, Object> datosVenta) {
        try {
            Map<String, Object> ventaActualizada = servicio.actualizarVenta(id, datosVenta);
            return ResponseEntity.ok(ventaActualizada);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @Operation(summary = "Eliminar una venta",
               description = "Elimina una venta existente del sistema.",
               responses = {
                   @ApiResponse(responseCode = "204", description = "Venta eliminada correctamente"),
                   @ApiResponse(responseCode = "404", description = "Venta no encontrada")
               })
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
