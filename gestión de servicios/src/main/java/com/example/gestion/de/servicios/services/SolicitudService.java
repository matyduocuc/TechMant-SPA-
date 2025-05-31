package com.example.gestion.de.servicios.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.gestion.de.servicios.Model.Solicitud;
import com.example.gestion.de.servicios.repository.SolicitudRepository;

@Service

public class SolicitudService {
    private final SolicitudRepository repo;

public SolicitudService(SolicitudRepository repo) {
    this.repo = repo;
}

public Solicitud crear(Solicitud solicitud) {
    return repo.save(solicitud);
}

public List<Solicitud> listarPorCliente(Long clienteId) {
    return repo.findByClienteId(clienteId);
}

public List<Solicitud> listarTodas() {
    return repo.findAll();
}


}
