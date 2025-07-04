package com.example.gestion_de_servicios.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO con la información del usuario.")
public class UsuarioResponseDTO {

    @Schema(example = "10", description = "ID del usuario")
    private Long id;

    @Schema(example = "Carlos Díaz", description = "Nombre del usuario")
    private String nombre;

    @Schema(example = "carlos@mail.com", description = "Correo electrónico del usuario")
    private String correo;

    @Schema(example = "CLIENTE", description = "Rol del usuario")
    private String rol;
}
