package com.example.gestion.reportes.services;

import com.example.gestion.reportes.model.Reporte;
import com.example.gestion.reportes.repository.ReporteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ReporteService {

    @Autowired
    private ReporteRepository reporteRepository;

    // Crear un nuevo reporte
    public Reporte crearReporte(Reporte reporte) {
        reporte.setFechaReporte(LocalDate.now());
        return reporteRepository.save(reporte);
    }

    // Obtener todos los reportes
    public List<Reporte> listarTodos() {
        return reporteRepository.findAll();
    }

    // Obtener reportes por cliente
    public List<Reporte> listarPorCliente(Long clienteId) {
        return reporteRepository.findByClienteId(clienteId);
    }

    // Obtener reportes por técnico
    public List<Reporte> listarPorTecnico(Long tecnicoId) {
        return reporteRepository.findByTecnicoId(tecnicoId);
    }

    // Obtener reportes por fecha
    public List<Reporte> listarPorFecha(LocalDate fecha) {
        return reporteRepository.findByFechaReporte(fecha);
    }

    // Obtener reportes por estado
    public List<Reporte> listarPorEstado(String estado) {
        return reporteRepository.findByEstadoFinalIgnoreCase(estado);
    }

    // Obtener reportes por título (ignorar mayúsculas/minúsculas)
    public List<Reporte> listarPorTitulo(String titulo) {
        return reporteRepository.findByEstadoFinalIgnoreCase(titulo);
    }

    // Actualizar un reporte
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

    // Eliminar un reporte
    public void eliminarReporte(Long id) {
        Optional<Reporte> reporteExistenteOpt = reporteRepository.findById(id);
        if (reporteExistenteOpt.isPresent()) {
            reporteRepository.delete(reporteExistenteOpt.get());
        } else {
            throw new RuntimeException("Reporte no encontrado");
        }
    }

    public List<Reporte> obtenerReportes() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'obtenerReportes'");
    }
}
