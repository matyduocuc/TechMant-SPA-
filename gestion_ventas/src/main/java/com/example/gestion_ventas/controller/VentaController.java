package com.example.gestion_ventas.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.gestion_ventas.model.Venta;
import com.example.gestion_ventas.services.VentaService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    // Endpoint para crear una nueva venta (HTTP POST)
    @PostMapping
    public ResponseEntity<Venta> crearVenta(@Valid @RequestBody Venta venta) {
        Venta ventaCreada = ventaService.crearVenta(venta);
        return new ResponseEntity<>(ventaCreada, HttpStatus.CREATED);
    }

    // Endpoint para listar todas las ventas (HTTP GET)
    @GetMapping
    public List<Venta> listarTodasVentas() {
        return ventaService.listarVentas();
    }

    // Endpoint para obtener una venta por ID (HTTP GET)
    @GetMapping("/{id}")
    public Venta obtenerVentaPorId(@PathVariable Long id) {
        return ventaService.obtenerVentaPorId(id);
    }

    // Endpoint para actualizar una venta existente (HTTP PUT)
    @PutMapping("/{id}")
    public ResponseEntity<Venta> actualizarVenta(
            @PathVariable Long id, @Valid @RequestBody Venta venta) {
        Venta ventaActualizada = ventaService.actualizarVenta(id, venta);
        return ResponseEntity.ok(ventaActualizada);
    }

    // Endpoint para eliminar una venta por ID (HTTP DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarVenta(@PathVariable Long id) {
        ventaService.eliminarVenta(id);
        return ResponseEntity.noContent().build();
    }
}
