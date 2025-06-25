package com.example.gestion_de_ventas.dto;

import com.example.gestion_de_ventas.model.Venta;
import lombok.*;

@Data
@AllArgsConstructor
public class VentaConUsuarioDTO {
    private Venta venta;
    private UsuarioResponseDTO usuario;
}