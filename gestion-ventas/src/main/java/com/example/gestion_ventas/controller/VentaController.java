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

    @PostMapping
    public Venta crear(@Valid @RequestBody Venta venta) {
        return servicio.guardar(venta);
    }

    @GetMapping
    public List<Venta> listar() {
        return servicio.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venta> obtenerPorId(@PathVariable Long id) {
        return servicio.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Nuevo endpoint para buscar por estado
    @GetMapping("/estado/{estado}")
    public List<Venta> obtenerPorEstado(@PathVariable String estado) {
        return servicio.buscarPorEstado(estado);
    }
}
