package com.example.gestion.reportes.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO que representa un usuario vinculado al reporte.")
public class UsuarioResponseDTO {

    @Schema(description = "ID único del usuario", example = "5")
    private Long id;

    @Schema(description = "Nombre completo", example = "María García")
    private String nombre;

    @Schema(description = "Correo del usuario", example = "maria@cliente.com")
    private String correo;

    @Schema(description = "Rol del usuario", example = "CLIENTE")
    private String rol;
}
