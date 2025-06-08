package com.example.gestion_servicios_tecnicos.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.gestion_servicios_tecnicos.model.ServicioTecnico;
import com.example.gestion_servicios_tecnicos.repository.ServicioTecnicoRepository;

@Service
public class ServicioTecnicoService {
    @Autowired
    private ServicioTecnicoRepository repo;

    public ServicioTecnico crear(ServicioTecnico st) {
        if (st.getFechaRegistro() == null) st.setFechaRegistro(LocalDate.now());
        return repo.save(st);
    }

    public List<ServicioTecnico> listar() {
        return repo.findAll();
    }

    public Optional<ServicioTecnico> porId(Long id) {
        return repo.findById(id);
    }

    public List<ServicioTecnico> listarPorTecnico(Long tecnicoId) {
        return repo.findByTecnicoId(tecnicoId);
    }

    public List<ServicioTecnico> listarPorSolicitud(Long solicitudId) {
        return repo.findBySolicitudId(solicitudId);
    }

    public Optional<ServicioTecnico> actualizar(Long id, ServicioTecnico actualizado) {
        return repo.findById(id).map(st -> {
            st.setSolicitudId(actualizado.getSolicitudId());
            st.setEquipoId(actualizado.getEquipoId());
            st.setTecnicoId(actualizado.getTecnicoId());
            st.setDescripcion(actualizado.getDescripcion());
            st.setEstado(actualizado.getEstado());
            st.setFechaRegistro(actualizado.getFechaRegistro());
            return repo.save(st);
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
