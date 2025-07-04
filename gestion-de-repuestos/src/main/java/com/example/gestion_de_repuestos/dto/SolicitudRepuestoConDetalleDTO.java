package com.example.gestion_de_repuestos.dto;

import com.example.gestion_de_repuestos.model.SolicitudRepuesto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@AllArgsConstructor
@Schema(description = "DTO que agrupa una solicitud de repuesto con su solicitud técnica.")
public class SolicitudRepuestoConDetalleDTO {
    private SolicitudRepuesto solicitudRepuesto;
    private SolicitudDTO solicitudInfo;
}
