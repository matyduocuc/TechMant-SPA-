package com.example.gestion.reportes.dto;

import com.example.gestion.reportes.model.Reporte;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReporteConUsuarioDTO {
    private Reporte reporte;
    private UsuarioResponseDTO cliente;
    private UsuarioResponseDTO tecnico;
}
