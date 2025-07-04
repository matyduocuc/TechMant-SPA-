package com.example.Gestion_de_ticket.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO con los datos del usuario asociado al ticket.")
public class UsuarioResponseDTO {

    @Schema(description = "ID del usuario", example = "5")
    private Long id;

    @Schema(description = "Nombre del usuario", example = "Lucía Ramírez")
    private String nombre;

    @Schema(description = "Correo electrónico del usuario", example = "lucia@correo.com")
    private String correo;

    @Schema(description = "Rol del usuario", example = "CLIENTE")
    private String rol;
}
