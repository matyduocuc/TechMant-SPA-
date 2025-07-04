package com.example.gestion_de_repuestos.model;

import jakarta.persistence.*;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "solicitud_repuesto")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Entidad que representa una solicitud de repuesto para una reparación técnica.")
public class SolicitudRepuesto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único de la solicitud de repuesto", example = "1")
    private Long id;

    @Schema(description = "ID de la solicitud técnica relacionada (proveniente de otro microservicio)", example = "101")
    private Long solicitudId;

    @Schema(description = "ID del repuesto solicitado", example = "5")
    private Long repuestoId;

    @Schema(description = "Cantidad de repuestos requeridos", example = "2")
    private int cantidad;
}
