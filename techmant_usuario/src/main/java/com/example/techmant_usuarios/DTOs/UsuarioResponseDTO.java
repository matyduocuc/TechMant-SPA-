package com.example.techmant_usuarios.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "DTO devuelto al registrar, autenticar o consultar un usuario.")
public class UsuarioResponseDTO {

    @Schema(description = "ID único del usuario", example = "1")
    private Long id;

    @Schema(description = "Nombre completo del usuario", example = "Juan Pérez")
    private String nombre;

    @Schema(description = "Correo electrónico del usuario", example = "juan@mail.com")
    private String correo;

    @Schema(description = "Rol asignado al usuario", example = "CLIENTE")
    private String rol;
}
