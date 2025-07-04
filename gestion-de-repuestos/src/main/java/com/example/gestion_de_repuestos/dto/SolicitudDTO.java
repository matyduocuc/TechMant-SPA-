package com.example.gestion_de_repuestos.dto;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Información de la solicitud relacionada con un repuesto.")
public class SolicitudDTO {

    @Schema(example = "101")
    private Long id;

    @Schema(example = "Pantalla rota")
    private String descripcion;

    @Schema(example = "Notebook")
    private String tipoEquipo;

    @Schema(example = "PENDIENTE")
    private String estado;

    @Schema(example = "2025-07-04")
    private LocalDate fechaCreacion;

    @Schema(example = "5")
    private Long clienteId;
}
