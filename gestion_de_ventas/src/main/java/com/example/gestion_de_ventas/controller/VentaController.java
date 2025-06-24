package com.example.gestion_de_ventas.controller;

import com.example.gestion_de_ventas.model.Venta;
import com.example.gestion_de_ventas.services.VentaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @Operation(
        summary = "Registrar una nueva venta",
        description = "Crea y guarda una nueva venta en el sistema.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Venta creada exitosamente",
                         content = @Content(schema = @Schema(implementation = Venta.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
        }
    )
    @PostMapping
    public Venta crear(@RequestBody Venta venta) {
        return ventaService.crearVenta(venta);
    }

    @Operation(
        summary = "Listar todas las ventas",
        description = "Retorna la lista de todas las ventas registradas.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Lista de ventas obtenida correctamente",
                         content = @Content(schema = @Schema(implementation = Venta.class)))
        }
    )
    @GetMapping
    public List<Venta> listar() {
        return ventaService.listarVentas();
    }

    @Operation(
        summary = "Obtener una venta por ID",
        description = "Retorna una venta específica según su ID.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Venta encontrada",
                         content = @Content(schema = @Schema(implementation = Venta.class))),
            @ApiResponse(responseCode = "404", description = "Venta no encontrada")
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Venta> porId(@PathVariable Long id) {
        return ventaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
        summary = "Actualizar una venta",
        description = "Actualiza los datos de una venta existente.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Venta actualizada correctamente",
                         content = @Content(schema = @Schema(implementation = Venta.class))),
            @ApiResponse(responseCode = "404", description = "Venta no encontrada")
        }
    )
    @PutMapping("/{id}")
    public ResponseEntity<Venta> actualizar(@PathVariable Long id, @RequestBody Venta datos) {
        return ventaService.actualizarVenta(id, datos)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
        summary = "Eliminar una venta",
        description = "Elimina una venta por su ID.",
        responses = {
            @ApiResponse(responseCode = "204", description = "Venta eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Venta no encontrada")
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (ventaService.eliminarVenta(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
