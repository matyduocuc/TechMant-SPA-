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

    // Crear una nueva asignación
    public Asignacion asignar(Asignacion asignacion) {
        return repository.save(asignacion);
    }

    // Listar todas las asignaciones
    public List<Asignacion> listarTodas() {
        return repository.findAll();
    }

    // Listar las asignaciones por técnico
    public List<Asignacion> listarPorTecnico(Long tecnicoId) {
        return repository.findByTecnicoId(tecnicoId);
    }

    // Actualizar una asignación por ID
    public Optional<Asignacion> actualizar(Long id, Asignacion nueva) {
        return repository.findById(id).map(asig -> {
            asig.setTecnicoId(nueva.getTecnicoId());
            asig.setSolicitudId(nueva.getSolicitudId());
            asig.setFechaAsignacion(nueva.getFechaAsignacion());
            return repository.save(asig);
        });
    }

    // Eliminar una asignación por ID
    public boolean eliminar(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    // Buscar una asignación por ID
    public Optional<Asignacion> buscarPorId(Long id) {
        return repository.findById(id);
    }
}
