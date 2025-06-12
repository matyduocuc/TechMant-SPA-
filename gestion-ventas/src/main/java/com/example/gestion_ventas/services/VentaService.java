package com.example.gestion_ventas.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.gestion_ventas.model.Venta;
import com.example.gestion_ventas.repository.VentaRepository;

@Service
public class VentaService {
    @Autowired
    private VentaRepository repo;

    public Venta guardar(Venta v) {
        v.setFechaVenta(LocalDate.now());
        v.setTotalVenta(v.getMontoServicio() + v.getMontoRepuestos());
        return repo.save(v);
    }

    public List<Venta> listar() {
        return repo.findAll();
    }

    public Optional<Venta> buscarPorId(Long id) {
        return repo.findById(id);
    }

    // Nuevo método para buscar por estado
    public List<Venta> buscarPorEstado(String estado) {
        return repo.findByEstadoIgnoreCase(estado);
    }

    // Método para actualizar una venta
    public Venta actualizarVenta(Long id, Venta ventaActualizada) {
        // Buscar la venta existente por ID
        Optional<Venta> ventaExistenteOpt = repo.findById(id);
        if (ventaExistenteOpt.isPresent()) {
            Venta ventaExistente = ventaExistenteOpt.get();
            
            // Actualizar los campos de la venta
            ventaExistente.setSolicitudId(ventaActualizada.getSolicitudId());
            ventaExistente.setClienteId(ventaActualizada.getClienteId());
            ventaExistente.setTecnicoId(ventaActualizada.getTecnicoId());
            ventaExistente.setMontoServicio(ventaActualizada.getMontoServicio());
            ventaExistente.setMontoRepuestos(ventaActualizada.getMontoRepuestos());
            ventaExistente.setTotalVenta(ventaActualizada.getMontoServicio() + ventaActualizada.getMontoRepuestos());
            ventaExistente.setDetalleVenta(ventaActualizada.getDetalleVenta());
            ventaExistente.setEstado(ventaActualizada.getEstado());
            ventaExistente.setFechaVenta(ventaActualizada.getFechaVenta());

            // Guardar la venta actualizada
            return repo.save(ventaExistente);
        } else {
            throw new RuntimeException("Venta no encontrada");
        }
    }

    // Método para eliminar una venta
    public void eliminarVenta(Long id) {
    // Buscar la venta por ID
    Optional<Venta> ventaExistenteOpt = repo.findById(id);
    if (ventaExistenteOpt.isPresent()) {
        // Eliminar la venta
        repo.delete(ventaExistenteOpt.get());
    } else {
        throw new RuntimeException("Venta no encontrada");
    }
    }
}