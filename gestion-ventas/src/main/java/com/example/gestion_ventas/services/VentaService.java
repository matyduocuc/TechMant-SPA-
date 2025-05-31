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
}
