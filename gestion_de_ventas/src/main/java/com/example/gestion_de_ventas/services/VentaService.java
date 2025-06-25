package com.example.gestion_de_ventas.services;

import com.example.gestion_de_ventas.model.Venta;
import com.example.gestion_de_ventas.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class VentaService {

    @Autowired
    private VentaRepository repository;

    public Venta guardar(Venta venta) {
        if (venta.getFechaVenta() == null)
            venta.setFechaVenta(LocalDate.now());
        return repository.save(venta);
    }

    public List<Venta> listar() {
        return repository.findAll();
    }

    public Optional<Venta> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Optional<Venta> actualizar(Long id, Venta nueva) {
        return repository.findById(id).map(v -> {
            v.setProducto(nueva.getProducto());
            v.setCantidad(nueva.getCantidad());
            v.setPrecio(nueva.getPrecio());
            v.setFechaVenta(nueva.getFechaVenta());
            v.setClienteId(nueva.getClienteId());
            return repository.save(v);
        });
    }

    public boolean eliminar(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}