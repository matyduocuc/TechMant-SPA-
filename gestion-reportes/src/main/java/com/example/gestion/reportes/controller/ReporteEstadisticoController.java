package com.example.gestion.reportes.controller;

import com.example.gestion.reportes.services.ReporteEstadisticoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Listar todas las ventas", description = "Retorna una lista de todas las ventas registradas.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de ventas obtenida correctamente",
            content = @Content(schema = @Schema(implementation = Map.class))),
        @ApiResponse(responseCode = "204", description = "No hay ventas disponibles"),
        @ApiResponse(responseCode = "500", description = "Error interno")
    })
    @GetMapping("/ventas")
    public List<Map<String, Object>> listarVentas() {
        return servicio.obtenerTodasLasVentas();
    }

    @Operation(summary = "Total recaudado general", description = "Retorna el total recaudado por todas las ventas.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Total recaudado calculado correctamente",
            content = @Content(schema = @Schema(implementation = Double.class))),
        @ApiResponse(responseCode = "500", description = "Error al calcular el total")
    })
    @GetMapping("/ventas/total")
    public Double totalRecaudado() {
        return servicio.calcularTotalRecaudado();
    }

    @Operation(summary = "Total recaudado por técnico", description = "Muestra el total recaudado agrupado por técnico.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Totales por técnico obtenidos correctamente",
            content = @Content(schema = @Schema(implementation = Map.class))),
        @ApiResponse(responseCode = "204", description = "No hay datos para técnicos")
    })
    @GetMapping("/ventas/total-por-tecnico")
    public Map<Long, Double> totalPorTecnico() {
        return servicio.totalRecaudadoPorTecnico();
    }

    @Operation(summary = "Total recaudado por cliente", description = "Muestra el total recaudado agrupado por cliente.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Totales por cliente obtenidos correctamente",
            content = @Content(schema = @Schema(implementation = Map.class))),
        @ApiResponse(responseCode = "204", description = "No hay datos para clientes")
    })
    @GetMapping("/ventas/total-por-cliente")
    public Map<Long, Double> totalPorCliente() {
        return servicio.totalRecaudadoPorCliente();
    }

    @Operation(summary = "Actualizar una venta", description = "Permite actualizar los datos de una venta específica.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Venta actualizada correctamente",
            content = @Content(schema = @Schema(implementation = Map.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o incompletos"),
        @ApiResponse(responseCode = "404", description = "Venta no encontrada")
    })
    @PutMapping("/ventas/{id}")
    public ResponseEntity<Map<String, Object>> actualizarVenta(@PathVariable Long id,
                                                               @RequestBody Map<String, Object> datosVenta) {
        try {
            Map<String, Object> ventaActualizada = servicio.actualizarVenta(id, datosVenta);
            return ResponseEntity.ok(ventaActualizada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Eliminar una venta", description = "Elimina una venta existente del sistema.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Venta eliminada correctamente"),
        @ApiResponse(responseCode = "404", description = "Venta no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno al eliminar")
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
