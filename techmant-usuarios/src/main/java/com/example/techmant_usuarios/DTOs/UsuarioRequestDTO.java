package com.example.techmant_usuarios.DTOs;

import lombok.Data;

@Data
public class UsuarioRequestDTO {
    private String nombre;
    private String correo;
    private String contrasena;
    private String rol; // "CLIENTE", "TECNICO", "ADMIN"
} 

