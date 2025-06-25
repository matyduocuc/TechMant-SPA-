package com.example.gestion_de_ventas.controller;

import com.example.gestion_de_ventas.model.Venta;
import com.example.gestion_de_ventas.dto.VentaConUsuarioDTO;
import com.example.gestion_de_ventas.services.VentaService;
import com.example.gestion_de_ventas.services.VentaUsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @Autowired
    private VentaUsuarioService ventaUsuarioService;

    @Operation(summary = "Registrar una nueva venta")
    @ApiResponse(responseCode = "201", description = "Venta registrada con éxito", 
        content = @Content(schema = @Schema(implementation = Venta.class)))
    @PostMapping
    public ResponseEntity<Venta> crear(@Valid @RequestBody Venta venta) {
        Venta guardada = ventaService.guardar(venta);
        return ResponseEntity.ok(guardada);
    }

    @Operation(summary = "Listar todas las ventas")
    @ApiResponse(responseCode = "200", description = "Lista de ventas",
        content = @Content(schema = @Schema(implementation = Venta.class)))
    @GetMapping
    public List<Venta> listar() {
        return ventaService.listar();
    }

    @Operation(summary = "Buscar venta por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Venta encontrada", 
            content = @Content(schema = @Schema(implementation = Venta.class))),
        @ApiResponse(responseCode = "404", description = "Venta no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Venta> buscarPorId(@PathVariable Long id) {
        return ventaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Actualizar una venta existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Venta actualizada con éxito", 
            content = @Content(schema = @Schema(implementation = Venta.class))),
        @ApiResponse(responseCode = "404", description = "Venta no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Venta> actualizar(@PathVariable Long id, @RequestBody Venta nueva) {
        return ventaService.actualizar(id, nueva)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar venta por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Venta eliminada con éxito"),
        @ApiResponse(responseCode = "404", description = "Venta no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (ventaService.eliminar(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Listar ventas con detalle de cliente")
    @ApiResponse(responseCode = "200", description = "Lista de ventas con información de cliente",
        content = @Content(schema = @Schema(implementation = VentaConUsuarioDTO.class)))
    @GetMapping("/detalle-clientes")
    public List<VentaConUsuarioDTO> detalleClientes() {
        return ventaUsuarioService.obtenerVentasConUsuarios();
    }
}
