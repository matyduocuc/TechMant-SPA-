package com.example.gestion_de_repuestos.repository;

import com.example.gestion_de_repuestos.model.SolicitudRepuesto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolicitudRepuestoRepository extends JpaRepository<SolicitudRepuesto, Long> {
    
}
