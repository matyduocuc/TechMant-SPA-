package com.example.gestion_de_ventas.model;

import jakarta.persistence.*;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Entidad que representa una venta registrada.")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID de la venta", example = "1")
    private Long id;

    @Schema(description = "Nombre del producto vendido", example = "Disco Duro SSD 1TB")
    private String producto;

    @Schema(description = "Cantidad de productos vendidos", example = "2")
    private int cantidad;

    @Schema(description = "Precio total de la venta", example = "120000")
    private double precio;

    @Schema(description = "Fecha de la venta", example = "2025-07-04")
    private LocalDate fechaVenta;

    @Schema(description = "ID del cliente que realizó la compra", example = "10")
    private Long clienteId;
}
