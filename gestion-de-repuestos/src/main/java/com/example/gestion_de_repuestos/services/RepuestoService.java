package com.example.gestion_de_repuestos.services;

import com.example.gestion_de_repuestos.model.Repuesto;
import com.example.gestion_de_repuestos.repository.RepuestoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RepuestoService {

    @Autowired
    private RepuestoRepository repo;

    public Repuesto crear(Repuesto repuesto) {
        return repo.save(repuesto);
    }

    public List<Repuesto> listar() {
        return repo.findAll();
    }

    public Optional<Repuesto> buscarPorId(Long id) {
        return repo.findById(id);
    }

    public Optional<Repuesto> actualizar(Long id, Repuesto actualizado) {
        return repo.findById(id).map(r -> {
            r.setNombre(actualizado.getNombre());
            r.setDescripcion(actualizado.getDescripcion());
            r.setStock(actualizado.getStock());
            r.setPrecioUnidad(actualizado.getPrecioUnidad());
            return repo.save(r);
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
