package com.example.techmant_usuarios.model;

import jakarta.persistence.*;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuarios")
@Schema(description = "Entidad que representa a un usuario del sistema.")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del usuario", example = "10")
    private Long id;

    @Schema(description = "Nombre completo del usuario", example = "Juan Pérez")
    private String nombre;

    @Schema(description = "Correo electrónico del usuario", example = "juan@mail.com")
    private String correo;

    @Schema(description = "Contraseña encriptada del usuario", example = "$2a$10$abc123")
    private String contrasena;

    @ManyToOne
    @JoinColumn(name = "rol_id")
    @Schema(description = "Rol asignado al usuario")
    private Rol rol;
}
