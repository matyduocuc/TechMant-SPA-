package com.example.gestion_ventas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.gestion_ventas.model.Venta;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {
  List<Venta> findByEstadoIgnoreCase(String estado);
}
