package com.example.techmant_usuarios.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "DTO para registrar o autenticar un usuario.")
public class UsuarioRequestDTO {

    @Schema(description = "Nombre completo del usuario", example = "Juan Pérez")
    private String nombre;

    @Schema(description = "Correo electrónico del usuario", example = "juan@mail.com")
    private String correo;

    @Schema(description = "Contraseña en texto plano", example = "password123")
    private String contrasena;

    @Schema(description = "Rol del usuario (CLIENTE, ADMIN, TECNICO)", example = "CLIENTE")
    private String rol;
}
