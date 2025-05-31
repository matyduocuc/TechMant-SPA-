package com.example.asignaciones.de.tecnicos.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.asignaciones.de.tecnicos.model.Asignacion;
import com.example.asignaciones.de.tecnicos.repository.AsignacionRepository;

@Service
public class AsignacionService {

        private AsignacionRepository repo;

        public AsignacionService(AsignacionRepository repo) {
        this.repo = repo;
    }

    public Asignacion asignar(Asignacion asignacion) {
        return repo.save(asignacion);
    }

    public List<Asignacion> listarPorTecnico(Long tecnicoId) {
        return repo.findByTecnicoId(tecnicoId);
    }

    public List<Asignacion> listarTodas() {
        return repo.findAll();
    }

}
