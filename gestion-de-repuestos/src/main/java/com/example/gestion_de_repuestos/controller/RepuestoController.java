package com.example.gestion_de_repuestos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gestion_de_repuestos.model.Repuesto;
import com.example.gestion_de_repuestos.services.RepuestoService;

@RestController
@RequestMapping("/api/repuestos")
public class RepuestoController {
    @Autowired
    private RepuestoService servicio;

    @PostMapping
    public Repuesto agregar(@RequestBody Repuesto repuesto) {
        return servicio.crear(repuesto);
    }

    @GetMapping
    public List<Repuesto> listar() {
        return servicio.listar();
    }

    

}
