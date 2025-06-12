package com.example.gestion.reportes.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.gestion.reportes.model.Reporte;


@Repository
public interface ReporteRepository extends JpaRepository<Reporte, Long> {
    List<Reporte> findByClienteId(Long clienteId);
    List<Reporte> findByTecnicoId(Long tecnicoId);
    List<Reporte> findByFechaReporte(LocalDate fecha);
    List<Reporte> findByEstadoFinalIgnoreCase(String estadoFinal);
}
