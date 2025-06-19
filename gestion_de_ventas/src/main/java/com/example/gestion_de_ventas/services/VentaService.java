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
    private VentaRepository ventaRepository;

    public Venta crearVenta(Venta venta) {
        if (venta.getFechaVenta() == null) {
            venta.setFechaVenta(LocalDate.now());
        }
        return ventaRepository.save(venta);
    }

    public List<Venta> listarVentas() {
        return ventaRepository.findAll();
    }

    public Optional<Venta> obtenerPorId(Long id) {
        return ventaRepository.findById(id);
    }

    public Optional<Venta> actualizarVenta(Long id, Venta datos) {
        return ventaRepository.findById(id).map(v -> {
            v.setTecnicoId(datos.getTecnicoId());
            v.setClienteId(datos.getClienteId());
            v.setTotalVenta(datos.getTotalVenta());
            v.setFechaVenta(datos.getFechaVenta());
            return ventaRepository.save(v);
        });
    }

    public boolean eliminarVenta(Long id) {
        if (ventaRepository.existsById(id)) {
            ventaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Object guardar(Object any) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'guardar'");
    }

    public Object porId(long l) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'porId'");
    }
}
