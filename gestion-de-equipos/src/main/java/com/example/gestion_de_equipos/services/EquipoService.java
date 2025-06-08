package com.example.gestion_de_equipos.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.gestion_de_equipos.model.Equipo;
import com.example.gestion_de_equipos.repository.EquipoRepository;

@Service
public class EquipoService {
    @Autowired
    private EquipoRepository repo;

    public Equipo guardar(Equipo equipo) {
        return repo.save(equipo);
    }

    public List<Equipo> listar() {
        return repo.findAll();
    }

    public Optional<Equipo> buscarPorId(Long id) {
        return repo.findById(id);
    }

    public Optional<Equipo> actualizar(Long id, Equipo actualizado) {
        return repo.findById(id).map(e -> {
            e.setNombre(actualizado.getNombre());
            e.setMarca(actualizado.getMarca());
            e.setModelo(actualizado.getModelo());
            e.setTipo(actualizado.getTipo());
            e.setDescripcion(actualizado.getDescripcion());
            return repo.save(e);
        });
    }

    public boolean eliminar(Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Equipo> buscarPorTipo(String tipo) {
        return repo.findAll();
    }

    public List<Equipo> buscarPorMarca(String marca) {
        return repo.findByMarca(marca);
    }
}
