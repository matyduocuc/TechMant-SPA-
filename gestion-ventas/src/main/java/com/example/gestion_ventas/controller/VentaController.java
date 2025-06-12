package com.example.gestion_ventas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.gestion_ventas.model.Venta;
import com.example.gestion_ventas.services.VentaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    @Autowired
    private VentaService servicio;

    // Método POST para crear una venta
    @PostMapping
    public Venta crear(@Valid @RequestBody Venta venta) {
        return servicio.guardar(venta);
    }

    // Método GET para listar todas las ventas
    @GetMapping
    public List<Venta> listar() {
        return servicio.listar();
    }

    // Método GET para obtener una venta por ID
    @GetMapping("/{id}")
    public ResponseEntity<Venta> obtenerPorId(@PathVariable Long id) {
        return servicio.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Método GET para buscar ventas por estado
    @GetMapping("/estado/{estado}")
    public List<Venta> obtenerPorEstado(@PathVariable String estado) {
        return servicio.buscarPorEstado(estado);
    }

    // Nuevo endpoint PUT para actualizar una venta
    @PutMapping("/{id}")
    public ResponseEntity<Venta> actualizarVenta(@PathVariable Long id, @Valid @RequestBody Venta venta) {
        try {
            Venta ventaActualizada = servicio.actualizarVenta(id, venta);
            return ResponseEntity.ok(ventaActualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build(); // Si no se encuentra la venta, retorna 404
        }
    }

    // Nuevo endpoint DELETE para eliminar una venta
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarVenta(@PathVariable Long id) {
        try {
            servicio.eliminarVenta(id);
            return ResponseEntity.noContent().build(); // Retorna un 204 sin contenido después de la eliminación
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build(); // Si no se encuentra la venta, retorna 404
        }
    }
}