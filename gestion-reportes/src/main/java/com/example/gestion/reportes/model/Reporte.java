package com.example.gestion.reportes.model;

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

    private String descripcion;      // Resumen del servicio
    private String estadoFinal;      // Ej: "REPARADO", "NO REPARADO", etc.
    private LocalDate fechaReporte;  // Fecha del reporte
    private String titulo;           // Titulo del reporte

    // Getter y setter para "titulo"
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}

