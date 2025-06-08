package com.example.gestion_de_solicitudes.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.gestion_de_solicitudes.model.Solicitud;
import com.example.gestion_de_solicitudes.services.SolicitudService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/solicitudes")
public class SolicitudController {

    @Autowired
    private SolicitudService servicio;

    @PostMapping
    public Solicitud crear(@Valid @RequestBody Solicitud solicitud) {
        return servicio.guardar(solicitud);
    }

    @GetMapping
    public List<Solicitud> listar() {
        return servicio.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Solicitud> porId(@PathVariable Long id) {
        return servicio.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Solicitud> actualizar(@PathVariable Long id, @Valid @RequestBody Solicitud datosActualizados) {
        return servicio.actualizar(id, datosActualizados)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (servicio.eliminar(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

