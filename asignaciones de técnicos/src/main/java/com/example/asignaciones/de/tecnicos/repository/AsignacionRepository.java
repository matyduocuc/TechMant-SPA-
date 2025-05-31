package com.example.asignaciones.de.tecnicos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.asignaciones.de.tecnicos.model.Asignacion;

@Repository
public interface AsignacionRepository  extends JpaRepository<Asignacion, Long>{
      List<Asignacion> findByTecnicoId(Long tecnicoId);
      List<Asignacion> findBySolicitudId(Long solicitudId);
}
