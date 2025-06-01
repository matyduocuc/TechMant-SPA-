package com.example.gestion_de_equipos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.gestion_de_equipos.model.Equipo;

@Repository
public interface EquipoRepository extends JpaRepository<Equipo, Long>  {
    List<Equipo> findByTipoIgnoreCase(String tipo);
    List<Equipo> findByMarcaIgnoreCase(String marca);
}
