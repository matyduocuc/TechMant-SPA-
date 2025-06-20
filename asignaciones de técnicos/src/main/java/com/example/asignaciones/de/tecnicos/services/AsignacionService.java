package com.example.asignaciones.de.tecnicos.services;

import com.example.asignaciones.de.tecnicos.model.Asignacion;
import com.example.asignaciones.de.tecnicos.repository.AsignacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AsignacionService {

    @Autowired
    private AsignacionRepository repository;

    public Asignacion asignar(Asignacion asignacion) {
        return repository.save(asignacion);
    }

    public List<Asignacion> listarTodas() {
        return repository.findAll();
    }

    public List<Asignacion> listarPorTecnico(Long tecnicoId) {
        return repository.findByTecnicoId(tecnicoId);
    }

    public Optional<Asignacion> actualizar(Long id, Asignacion nueva) {
        return repository.findById(id).map(asig -> {
            asig.setTecnicoId(nueva.getTecnicoId());
            asig.setSolicitudId(nueva.getSolicitudId());
            asig.setFechaAsignacion(nueva.getFechaAsignacion());
            return repository.save(asig);
        });
    }

    public boolean eliminar(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
