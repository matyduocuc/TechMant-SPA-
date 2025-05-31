package com.example.gestion_de_repuestos.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.gestion_de_repuestos.model.Repuesto;
import com.example.gestion_de_repuestos.repository.RepuestoRepository;

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

    
    

}
