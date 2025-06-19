package com.example.gestion_de_ventas.controller;

import com.example.gestion_de_ventas.model.Venta;
import com.example.gestion_de_ventas.services.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @PostMapping
    public Venta crear(@RequestBody Venta venta) {
        return ventaService.crearVenta(venta);
    }

    @GetMapping
    public List<Venta> listar() {
        return ventaService.listarVentas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venta> porId(@PathVariable Long id) {
        return ventaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Venta> actualizar(@PathVariable Long id, @RequestBody Venta datos) {
        return ventaService.actualizarVenta(id, datos)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (ventaService.eliminarVenta(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
