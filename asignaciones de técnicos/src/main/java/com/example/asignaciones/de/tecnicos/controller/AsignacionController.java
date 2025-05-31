package com.example.asignaciones.de.tecnicos.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.asignaciones.de.tecnicos.model.Asignacion;
import com.example.asignaciones.de.tecnicos.services.AsignacionService;

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
}
