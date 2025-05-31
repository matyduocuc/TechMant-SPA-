package com.example.gestion_de_repuestos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "solicitud_repuesto")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class SolicitudRepuesto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long solicitudId;  // ID que viene del microservicio de solicitudes
    private Long repuestoId;   // ID del repuesto que se necesita
    private int cantidad;      // cuántos repuestos se necesitan
}
