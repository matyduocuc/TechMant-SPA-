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

    public void eliminar(Long id) {
        repo.deleteById(id);
    }

    public List<Equipo> buscarPorTipo(String tipo) {
    return repo.findByTipoIgnoreCase(tipo);
    }

    public List<Equipo> buscarPorMarca(String marca) {
    return repo.findByMarcaIgnoreCase(marca);
    }


}
