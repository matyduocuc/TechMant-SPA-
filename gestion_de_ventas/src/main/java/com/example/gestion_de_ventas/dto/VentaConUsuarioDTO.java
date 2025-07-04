package com.example.gestion_de_ventas.dto;

import com.example.gestion_de_ventas.model.Venta;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@AllArgsConstructor
@Schema(description = "DTO que agrupa la venta con información del cliente.")
public class VentaConUsuarioDTO {
    private Venta venta;
    private UsuarioResponseDTO usuario;
}
