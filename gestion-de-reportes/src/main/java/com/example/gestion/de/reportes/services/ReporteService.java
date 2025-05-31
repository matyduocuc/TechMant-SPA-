package com.example.gestion.de.reportes.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.gestion.de.reportes.model.Reporte;
import com.example.gestion.de.reportes.repository.ReporteRepository;

@Service
public class ReporteService {
  @Autowired
    private ReporteRepository reporteRepository;

    public Reporte crearReporte(Reporte reporte) {
        reporte.setFechaReporte(LocalDate.now());
        return reporteRepository.save(reporte);
    }

    public List<Reporte> listarTodos() {
        return reporteRepository.findAll();
    }

    public List<Reporte> listarPorCliente(Long clienteId) {
        return reporteRepository.findByClienteId(clienteId);
    }

    public List<Reporte> listarPorTecnico(Long tecnicoId) {
        return reporteRepository.findByTecnicoId(tecnicoId);
    }
}
