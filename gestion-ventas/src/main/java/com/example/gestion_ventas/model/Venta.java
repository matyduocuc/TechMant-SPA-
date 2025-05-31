package com.example.gestion_ventas.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ventas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El ID de la solicitud es obligatorio")
    private Long solicitudId;

    @NotNull(message = "El ID del cliente es obligatorio")
    private Long clienteId;

    @NotNull(message = "El ID del técnico es obligatorio")
    private Long tecnicoId;

    @NotNull(message = "El monto del servicio no puede ser nulo")
    @PositiveOrZero(message = "El monto del servicio no puede ser negativo")
    private Double montoServicio;

    @NotNull(message = "El monto de repuestos no puede ser nulo")
    @PositiveOrZero(message = "El monto de repuestos no puede ser negativo")
    private Double montoRepuestos;

    private Double totalVenta;

    @NotBlank(message = "El detalle de la venta no puede estar vacío")
    private String detalleVenta;

    @NotBlank(message = "El estado de la venta es obligatorio")
    private String estado;

    private LocalDate fechaVenta;
}