package com.example.gestion.de.servicios.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gestion.de.servicios.Model.Solicitud;
import com.example.gestion.de.servicios.services.SolicitudService;

@RestController
@RequestMapping("/api/solicitudes")
public class SolicitudController {
    private final SolicitudService service;

public SolicitudController(SolicitudService service) {
    this.service = service;
}

@PostMapping
public Solicitud crear(@RequestBody Solicitud solicitud) {
    return service.crear(solicitud);
}

@GetMapping("/cliente/{id}")
public List<Solicitud> listarPorCliente(@PathVariable Long id) {
    return service.listarPorCliente(id);
}

@GetMapping
public List<Solicitud> listarTodas() {
    return service.listarTodas();
}


}
