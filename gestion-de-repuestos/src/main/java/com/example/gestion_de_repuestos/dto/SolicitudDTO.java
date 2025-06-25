package com.example.gestion_de_repuestos.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudDTO {
    private Long id;
    private String descripcion;
    private String tipoEquipo;
    private String estado;
    private LocalDate fechaCreacion;
    private Long clienteId;
}
