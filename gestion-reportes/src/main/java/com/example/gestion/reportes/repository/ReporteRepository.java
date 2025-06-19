package com.example.gestion.reportes.repository;

import com.example.gestion.reportes.model.Reporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReporteRepository extends JpaRepository<Reporte, Long> {

    // Buscar reportes por clienteId
    List<Reporte> findByClienteId(Long clienteId);

    // Buscar reportes por tecnicoId
    List<Reporte> findByTecnicoId(Long tecnicoId);

    // Buscar reportes por fecha
    List<Reporte> findByFechaReporte(LocalDate fecha);

    // Buscar reportes por estado (sin importar mayúsculas/minúsculas)
    List<Reporte> findByEstadoFinalIgnoreCase(String estadoFinal);

    // Buscar reportes por título (sin importar mayúsculas/minúsculas)
    List<Reporte> findByTituloIgnoreCase(String titulo);
}



