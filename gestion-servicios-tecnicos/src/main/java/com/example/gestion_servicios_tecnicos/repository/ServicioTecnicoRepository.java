package com.example.gestion_servicios_tecnicos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.gestion_servicios_tecnicos.model.ServicioTecnico;

@Repository
public interface ServicioTecnicoRepository  extends JpaRepository<ServicioTecnico, Long> {
        List<ServicioTecnico> findByTecnicoId(Long tecnicoId);
        List<ServicioTecnico> findBySolicitudId(Long solicitudId);

}
