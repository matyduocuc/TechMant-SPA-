package com.example.gestion_de_repuestos.model;

import jakarta.persistence.*;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "repuestos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Entidad que representa un repuesto disponible en el sistema.")
public class Repuesto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(example = "1", description = "ID del repuesto")
    private Long id;

    @Schema(example = "Pantalla LED 15.6\"", description = "Nombre del repuesto")
    private String nombre;

    @Schema(example = "Pantalla para notebook Lenovo", description = "Descripción detallada")
    private String descripcion;

    @Schema(example = "20", description = "Cantidad de repuestos disponibles en stock")
    private int stock;

    @Schema(example = "55000.0", description = "Precio unitario del repuesto en CLP")
    private double precioUnidad;
}
