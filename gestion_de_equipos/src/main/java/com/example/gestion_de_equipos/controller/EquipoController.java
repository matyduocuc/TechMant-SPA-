package com.example.gestion_de_equipos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.gestion_de_equipos.model.Equipo;
import com.example.gestion_de_equipos.services.EquipoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/equipos")
public class EquipoController {

    @Autowired
    private EquipoService servicio;

    @PostMapping
    public Equipo crear(@Valid @RequestBody Equipo equipo) {
        return servicio.guardar(equipo);
    }

    @GetMapping
    public List<Equipo> listar() {
        return servicio.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Equipo> porId(@PathVariable Long id) {
        return servicio.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Equipo> actualizar(@PathVariable Long id, @Valid @RequestBody Equipo datosActualizados) {
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

    @GetMapping("/tipo/{tipo}")
    public List<Equipo> buscarPorTipo(@PathVariable String tipo) {
        return servicio.buscarPorTipo(tipo);
    }

    @GetMapping("/marca/{marca}")
    public List<Equipo> buscarPorMarca(@PathVariable String marca) {
        return servicio.buscarPorMarca(marca);
    }
}