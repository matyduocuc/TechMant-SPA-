package com.example.gestion_servicios_tecnicos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.gestion_servicios_tecnicos.model.ServicioTecnico;
import com.example.gestion_servicios_tecnicos.services.ServicioTecnicoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/servicios-tecnicos")
public class ServicioTecnicoController {

    @Autowired
    private ServicioTecnicoService servicio;

    @PostMapping
    public ServicioTecnico crear(@Valid @RequestBody ServicioTecnico st) {
        return servicio.crear(st);
    }

    @GetMapping
    public List<ServicioTecnico> listar() {
        return servicio.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServicioTecnico> porId(@PathVariable Long id) {
        return servicio.porId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/tecnico/{tecnicoId}")
    public List<ServicioTecnico> porTecnico(@PathVariable Long tecnicoId) {
        return servicio.listarPorTecnico(tecnicoId);
    }

    @GetMapping("/solicitud/{solicitudId}")
    public List<ServicioTecnico> porSolicitud(@PathVariable Long solicitudId) {
        return servicio.listarPorSolicitud(solicitudId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServicioTecnico> actualizar(@PathVariable Long id, @Valid @RequestBody ServicioTecnico actualizado) {
        return servicio.actualizar(id, actualizado)
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
