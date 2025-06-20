package com.example.asignaciones.de.tecnicos.controller;

import com.example.asignaciones.de.tecnicos.model.Asignacion;
import com.example.asignaciones.de.tecnicos.services.AsignacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asignaciones")
public class AsignacionController {

    private final AsignacionService service;

    public AsignacionController(AsignacionService service) {
        this.service = service;
    }

    @PostMapping
    public Asignacion asignar(@RequestBody Asignacion asignacion) {
        return service.asignar(asignacion);
    }

    @GetMapping
    public List<Asignacion> listar() {
        return service.listarTodas();
    }

    @GetMapping("/tecnico/{id}")
    public List<Asignacion> listarPorTecnico(@PathVariable Long id) {
        return service.listarPorTecnico(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Asignacion> actualizar(@PathVariable Long id, @RequestBody Asignacion nueva) {
        return service.actualizar(id, nueva)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (service.eliminar(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
