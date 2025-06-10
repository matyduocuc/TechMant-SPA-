package com.example.Gestion_de_ticket.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TicketDTO {

    @NotNull
    private Long idUsuario;

    @NotNull
    private Long idEquipo;

    @NotNull
    private Long idSolicitud;

    @NotBlank
    private String problemaReportado;
}
