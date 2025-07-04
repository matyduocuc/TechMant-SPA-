package com.example.Gestion_de_ticket.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Entidad que representa un ticket de soporte técnico.")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID del ticket", example = "1")
    private Long id;

    @Schema(description = "ID del usuario que reporta", example = "5")
    private Long idUsuario;

    @Schema(description = "ID del equipo", example = "10")
    private Long idEquipo;

    @Schema(description = "ID de la solicitud", example = "15")
    private Long idSolicitud;

    @Schema(description = "Problema reportado", example = "Pantalla no enciende")
    private String problemaReportado;

    @Schema(description = "Estado del ticket", example = "EN_PROCESO")
    private String estado;

    @Schema(description = "Diagnóstico técnico del problema", example = "Pantalla rota, requiere reemplazo")
    private String diagnosticoTecnico;

    @Schema(description = "Fecha de creación del ticket", example = "2025-07-05T14:23:00")
    private LocalDateTime fechaCreacion;

    @Schema(description = "Fecha de última actualización del ticket", example = "2025-07-06T09:45:00")
    private LocalDateTime fechaActualizacion;
}
