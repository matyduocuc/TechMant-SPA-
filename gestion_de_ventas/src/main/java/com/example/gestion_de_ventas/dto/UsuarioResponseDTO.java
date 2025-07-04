package com.example.gestion_de_ventas.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO con la información del cliente asociado a una venta.")
public class UsuarioResponseDTO {

    @Schema(example = "10", description = "ID del usuario")
    private Long id;

    @Schema(example = "Ana Morales", description = "Nombre completo del usuario")
    private String nombre;

    @Schema(example = "ana@correo.cl", description = "Correo del cliente")
    private String correo;

    @Schema(example = "CLIENTE", description = "Rol del usuario")
    private String rol;
}
