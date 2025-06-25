package com.example.gestion_de_repuestos.dto;

import com.example.gestion_de_repuestos.model.SolicitudRepuesto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SolicitudRepuestoConDetalleDTO {
    private SolicitudRepuesto solicitudRepuesto;
    private SolicitudDTO solicitudInfo;
}
