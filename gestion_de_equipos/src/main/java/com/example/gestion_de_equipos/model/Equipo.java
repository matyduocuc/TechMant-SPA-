package com.example.gestion_de_equipos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "equipos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Entidad que representa un equipo tecnológico en el sistema.")
public class Equipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del equipo", example = "1")
    private Long id;

    @NotBlank(message = "El nombre del equipo es obligatorio")
    @Schema(description = "Nombre del equipo", example = "Impresora HP 2055")
    private String nombre;

    @NotBlank(message = "La marca es obligatoria")
    @Schema(description = "Marca del equipo", example = "HP")
    private String marca;

    @Schema(description = "Modelo del equipo", example = "LaserJet 2055")
    private String modelo;

    @NotBlank(message = "El tipo de equipo es obligatorio")
    @Schema(description = "Tipo de equipo", example = "Impresora")
    private String tipo;

    @Schema(description = "Descripción adicional del equipo", example = "Equipo en excelente estado con Wi-Fi incorporado")
    private String descripcion;
}
