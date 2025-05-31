package com.example.gestion_de_repuestos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.gestion_de_repuestos.model.SolicitudRepuesto;

@Repository
public interface SolicitudRepuestoRepository extends JpaRepository<SolicitudRepuesto, Long> {

}
