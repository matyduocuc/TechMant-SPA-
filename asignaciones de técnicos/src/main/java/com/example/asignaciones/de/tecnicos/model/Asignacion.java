package com.example.asignaciones.de.tecnicos.model;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Entidad que representa la asignación de un técnico a una solicitud.")
public class Asignacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID de la asignación", example = "1")
    private Long id;

    @Schema(description = "ID de la solicitud técnica", example = "101")
    private Long solicitudId;

    @Schema(description = "ID del técnico asignado", example = "7")
    private Long tecnicoId;

    @Schema(description = "Fecha en que se realizó la asignación", example = "2025-07-06")
    private LocalDate fechaAsignacion = LocalDate.now();
}
