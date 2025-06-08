package com.example.gestion.de.servicios.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.gestion_de_solicitudes.model.Solicitud;
import com.example.gestion_de_solicitudes.repository.SolicitudRepository;

@Service
public class SolicitudService {

    @Autowired
    private SolicitudRepository repo;

    public Solicitud guardar(Solicitud solicitud) {
        return repo.save(solicitud);
    }

    public List<Solicitud> listar() {
        return repo.findAll();
    }

    public Optional<Solicitud> buscarPorId(Long id) {
        return repo.findById(id);
    }

    public Optional<Solicitud> actualizar(Long id, Solicitud actualizada) {
        return repo.findById(id).map(s -> {
            s.setDescripcion(actualizada.getDescripcion());
            s.setEstado(actualizada.getEstado());
            s.setClienteId(actualizada.getClienteId());
            // Agrega aquí otros campos si tienes más
            return repo.save(s);
        });
    }

    public boolean eliminar(Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        }
        return false;
    }
}
