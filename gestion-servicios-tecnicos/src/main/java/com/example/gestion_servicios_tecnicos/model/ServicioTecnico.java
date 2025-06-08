package com.example.gestion_servicios_tecnicos.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "servicios_tecnicos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServicioTecnico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long solicitudId;
    private Long equipoId;
    private Long tecnicoId;

    private String descripcion;
    private String estado; // Ejemplo: "EN PROGRESO", "FINALIZADO"
    private LocalDate fechaRegistro;
}