package com.example.gestion.de.reportes.model;

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
@Table(name = "reporte")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long solicitudId;
    private Long clienteId;
    private Long tecnicoId;

    private String descripcion;      // resumen del servicio
    private String estadoFinal;      // EJ: "REPARADO", "NO REPARADO", etc.
    private LocalDate fechaReporte;
}
