package com.example.gestion_ventas.repository;

import com.example.gestion_ventas.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {
    // Los métodos CRUD básicos son heredados de JpaRepository.
}
