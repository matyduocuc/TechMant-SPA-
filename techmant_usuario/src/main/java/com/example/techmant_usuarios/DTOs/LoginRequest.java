package com.example.techmant_usuarios.DTOs;

import lombok.Data;

@Data
public class LoginRequest {
    private String correo;
    private String clave;
}
