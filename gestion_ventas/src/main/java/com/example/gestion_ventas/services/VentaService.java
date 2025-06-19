package com.example.gestion_ventas.services;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import com.example.gestion_ventas.model.Venta;
import com.example.gestion_ventas.repository.VentaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VentaService {

    private final VentaRepository ventaRepository;

    // Crear una nueva venta
    public Venta crearVenta(Venta venta) {
        return ventaRepository.save(venta);
    }

    // Listar todas las ventas
    public List<Venta> listarVentas() {
        return ventaRepository.findAll();
    }

    // Obtener una venta por su ID (lanza 404 si no existe)
    public Venta obtenerVentaPorId(Long id) {
        return ventaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Venta no encontrada"));
    }

    // Actualizar una venta existente por ID
    public Venta actualizarVenta(Long id, Venta venta) {
        if (!ventaRepository.existsById(id)) {
            // Si no existe la venta, lanzamos excepción 404
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Venta no encontrada");
        }
        // Asignar el ID existente a la entidad nueva y guardar
        venta.setId(id);
        return ventaRepository.save(venta);
    }

    // Eliminar una venta por ID
    public void eliminarVenta(Long id) {
        if (!ventaRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Venta no encontrada");
        }
        ventaRepository.deleteById(id);
    }
}
