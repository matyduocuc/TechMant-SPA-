package com.example.gestion.de.reportes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.gestion.de.reportes.model.Reporte;

@Repository
public interface ReporteRepository extends JpaRepository<Reporte, Long> {
      List<Reporte> findByClienteId(Long clienteId);

     List<Reporte> findByTecnicoId(Long tecnicoId);

}
