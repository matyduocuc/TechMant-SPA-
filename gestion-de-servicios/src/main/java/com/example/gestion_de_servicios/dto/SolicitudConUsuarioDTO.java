package com.example.gestion_de_servicios.dto;

import com.example.gestion_de_servicios.model.Solicitud;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@AllArgsConstructor
@Schema(description = "DTO que combina una solicitud con información del cliente.")
public class SolicitudConUsuarioDTO {
    private Solicitud solicitud;
    private UsuarioResponseDTO usuario;
}
