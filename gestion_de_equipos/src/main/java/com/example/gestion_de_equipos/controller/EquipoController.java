package com.example.gestion_de_equipos.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import com.example.gestion_de_equipos.model.Equipo;
import com.example.gestion_de_equipos.services.EquipoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/equipos")
public class EquipoController {

    @Autowired
    private EquipoService servicio;

    // Crear un nuevo equipo
    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Equipo equipo, BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            for (FieldError error : result.getFieldErrors()) {
                errorMessage.append(error.getField()).append(" ").append(error.getDefaultMessage()).append(";");
            }
            return ResponseEntity.badRequest().body(errorMessage.toString());
        }
        Equipo nuevoEquipo = servicio.guardar(equipo);
        return ResponseEntity.ok(nuevoEquipo);
    }

    // Listar todos los equipos
    @GetMapping
    public List<Equipo> listar() {
        return servicio.listar();
    }

    // Obtener equipo por ID
    @GetMapping("/{id}")
    public ResponseEntity<Equipo> porId(@PathVariable Long id) {
        return servicio.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Actualizar equipo por ID
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody Equipo datosActualizados, BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            for (FieldError error : result.getFieldErrors()) {
                errorMessage.append(error.getField()).append(" ").append(error.getDefaultMessage()).append("; ");
            }
            return ResponseEntity.badRequest().body(errorMessage.toString());
        }

        Optional<Equipo> equipoActualizado = servicio.actualizar(id, datosActualizados);
        return equipoActualizado.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Eliminar equipo por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (servicio.eliminar(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Buscar equipos por tipo
    @GetMapping("/tipo/{tipo}")
    public List<Equipo> buscarPorTipo(@PathVariable String tipo) {
        return servicio.buscarPorTipo(tipo);
    }

    // Buscar equipos por marca
    @GetMapping("/marca/{marca}")
    public List<Equipo> buscarPorMarca(@PathVariable String marca) {
        return servicio.buscarPorMarca(marca);
    }
}

