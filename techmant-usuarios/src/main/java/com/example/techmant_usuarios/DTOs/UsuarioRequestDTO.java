package com.example.techmant_usuarios.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsuarioRequestDTO {
    private String nombre;
    private String correo;
    private String contrasena;
    private String rol; // "CLIENTE", "TECNICO", "ADMIN"
} 

