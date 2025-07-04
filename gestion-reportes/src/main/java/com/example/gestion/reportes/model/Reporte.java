package com.example.gestion.reportes.model;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "reporte")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Entidad que representa un reporte de servicio técnico.")
public class Reporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del reporte", example = "1")
    private Long id;

    @Schema(description = "ID de la solicitud relacionada", example = "101")
    private Long solicitudId;

    @Schema(description = "ID del cliente asociado", example = "12")
    private Long clienteId;

    @Schema(description = "ID del técnico que realizó el trabajo", example = "8")
    private Long tecnicoId;

    @Schema(description = "Resumen del diagnóstico o reparación", example = "Reemplazo de pantalla y revisión de batería")
    private String descripcion;

    @Schema(description = "Estado final del equipo tras la reparación", example = "REPARADO")
    private String estadoFinal;

    @Schema(description = "Fecha en que se generó el reporte", example = "2025-07-04")
    private LocalDate fechaReporte;

    @Schema(description = "Título del reporte", example = "Reparación de notebook HP")
    private String titulo;
}
