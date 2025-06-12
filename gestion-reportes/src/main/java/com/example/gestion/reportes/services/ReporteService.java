package com.example.gestion.reportes.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.gestion.reportes.model.Reporte;
import com.example.gestion.reportes.repository.ReporteRepository;

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

    public List<Reporte> listarPorFecha(LocalDate fecha) {
        return reporteRepository.findByFechaReporte(fecha);
    }

    public List<Reporte> listarPorEstado(String estado) {
        return reporteRepository.findByEstadoFinalIgnoreCase(estado);
    }

    // Método para actualizar un reporte
    public Reporte actualizarReporte(Long id, Reporte reporteActualizado) {
        Optional<Reporte> reporteExistenteOpt = reporteRepository.findById(id);
        if (reporteExistenteOpt.isPresent()) {
            Reporte reporteExistente = reporteExistenteOpt.get();
            reporteExistente.setSolicitudId(reporteActualizado.getSolicitudId());
            reporteExistente.setClienteId(reporteActualizado.getClienteId());
            reporteExistente.setTecnicoId(reporteActualizado.getTecnicoId());
            reporteExistente.setDescripcion(reporteActualizado.getDescripcion());
            reporteExistente.setEstadoFinal(reporteActualizado.getEstadoFinal());
            reporteExistente.setFechaReporte(reporteActualizado.getFechaReporte());

            return reporteRepository.save(reporteExistente);
        } else {
            throw new RuntimeException("Reporte no encontrado");
        }
    }

    // Método para eliminar un reporte
    public void eliminarReporte(Long id) {
        Optional<Reporte> reporteExistenteOpt = reporteRepository.findById(id);
        if (reporteExistenteOpt.isPresent()) {
            reporteRepository.delete(reporteExistenteOpt.get());
        } else {
            throw new RuntimeException("Reporte no encontrado");
        }
    }
}
