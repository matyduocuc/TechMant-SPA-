package com.example.gestion_de_repuestos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gestion_de_repuestos.model.SolicitudRepuesto;
import com.example.gestion_de_repuestos.services.SolicitudRepuestoService;

@RestController
@RequestMapping("/api/solicitud-repuesto")
public class SolicitudRepuestoController {
        
    @Autowired
    private SolicitudRepuestoService servicio;

    @PostMapping
    public SolicitudRepuesto crear(@RequestBody SolicitudRepuesto sr) {
        return servicio.guardar(sr);
    }

    @GetMapping
    public List<SolicitudRepuesto> listarTodos() {
        return servicio.listar();
    }
}
