package com.example.gestion.reportes.dto;

import com.example.gestion.reportes.model.Reporte;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@AllArgsConstructor
@Schema(description = "DTO que combina un reporte con los datos del cliente y técnico involucrados.")
public class ReporteConUsuarioDTO {
    private Reporte reporte;
    private UsuarioResponseDTO cliente;
    private UsuarioResponseDTO tecnico;
}
