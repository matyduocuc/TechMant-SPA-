package com.example.gestion_de_servicios.dto;

import com.example.gestion_de_servicios.model.Solicitud;
import lombok.*;

@Data
@AllArgsConstructor
public class SolicitudConUsuarioDTO {
    private Solicitud solicitud;
    private UsuarioResponseDTO usuario;
}
