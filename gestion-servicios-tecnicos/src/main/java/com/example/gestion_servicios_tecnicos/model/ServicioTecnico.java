package com.example.gestion_servicios_tecnicos.model;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "servicios_tecnicos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Entidad que representa un servicio técnico asignado a un técnico y relacionado con una solicitud y un equipo.")
public class ServicioTecnico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del servicio técnico", example = "1")
    private Long id;

    @Schema(description = "ID de la solicitud asociada", example = "1001")
    private Long solicitudId;

    @Schema(description = "ID del equipo asignado al servicio técnico", example = "501")
    private Long equipoId;

    @Schema(description = "ID del técnico responsable del servicio", example = "300")
    private Long tecnicoId;

    @Schema(description = "Descripción del diagnóstico y acciones realizadas", example = "Revisión completa del equipo y reemplazo del disco duro.")
    private String descripcion;

    @Schema(description = "Estado del servicio técnico", example = "EN_PROCESO")
    private String estado;

    @Schema(description = "Fecha en que se registró el servicio", example = "2025-07-01")
    private LocalDate fechaRegistro;
}
