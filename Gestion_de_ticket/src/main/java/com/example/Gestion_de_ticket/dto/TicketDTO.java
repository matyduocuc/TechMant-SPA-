package com.example.Gestion_de_ticket.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO para la creación de un nuevo ticket.")
public class TicketDTO {

    @NotNull
    @Schema(description = "ID del usuario que reporta", example = "5")
    private Long idUsuario;

    @NotNull
    @Schema(description = "ID del equipo afectado", example = "10")
    private Long idEquipo;

    @NotNull
    @Schema(description = "ID de la solicitud asociada", example = "15")
    private Long idSolicitud;

    @NotBlank
    @Schema(description = "Descripción del problema reportado", example = "Pantalla no enciende")
    private String problemaReportado;
}
