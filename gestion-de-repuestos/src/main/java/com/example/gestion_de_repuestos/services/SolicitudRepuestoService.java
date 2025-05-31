package com.example.gestion_de_repuestos.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.gestion_de_repuestos.model.SolicitudRepuesto;
import com.example.gestion_de_repuestos.repository.SolicitudRepuestoRepository;

@Service
public class SolicitudRepuestoService {
      @Autowired
     private SolicitudRepuestoRepository repo;

    public SolicitudRepuesto guardar(SolicitudRepuesto sr) {
        return repo.save(sr);
    }

    public List<SolicitudRepuesto> listar() {
        return repo.findAll();
    }

}
