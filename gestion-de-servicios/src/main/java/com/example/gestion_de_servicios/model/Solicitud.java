package com.example.gestion_de_servicios.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Entidad que representa una solicitud de servicio.")
public class Solicitud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(example = "1", description = "ID de la solicitud")
    private Long id;

    @Schema(example = "Pantalla no enciende", description = "Descripción del problema reportado")
    private String descripcion;

    @Schema(example = "Notebook", description = "Tipo de equipo reportado")
    private String tipoEquipo;

    @Schema(example = "ABIERTA", description = "Estado actual de la solicitud")
    private String estado;

    @Schema(example = "2025-07-05", description = "Fecha en que se creó la solicitud")
    private LocalDate fechaCreacion;

    @Schema(example = "12", description = "ID del cliente que registró la solicitud")
    private Long clienteId;
}
