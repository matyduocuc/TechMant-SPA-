package com.example.gestion_de_servicios.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.gestion_de_servicios.model.Solicitud;
import com.example.gestion_de_servicios.repository.SolicitudRepository;

@Service
public class SolicitudService {
    @Autowired
    private SolicitudRepository repo;

    public Solicitud guardar(Solicitud solicitud) {
        // Si no viene estado, pon "PENDIENTE"
        if (solicitud.getEstado() == null || solicitud.getEstado().isBlank())
            solicitud.setEstado("PENDIENTE");
        // Si no viene fecha, pon hoy
        if (solicitud.getFechaCreacion() == null)
            solicitud.setFechaCreacion(LocalDate.now());

        return repo.save(solicitud);
    }

    public List<Solicitud> listar() {
        return repo.findAll();
    }

    public Optional<Solicitud> buscarPorId(Long id) {
        return repo.findById(id);
    }

    public List<Solicitud> listarPorCliente(Long clienteId) {
        return repo.findByClienteId(clienteId);
    }

    public Optional<Solicitud> actualizar(Long id, Solicitud actualizada) {
        return repo.findById(id).map(s -> {
            s.setDescripcion(actualizada.getDescripcion());
            s.setTipoEquipo(actualizada.getTipoEquipo());
            s.setEstado(actualizada.getEstado());
            s.setFechaCreacion(actualizada.getFechaCreacion());
            s.setClienteId(actualizada.getClienteId());
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
