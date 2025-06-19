package com.example.gestion_ventas.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ventas")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long clienteId;    // ID del cliente asociado a la venta

    @NotNull
    private Long tecnicoId;    // ID del técnico que realizó la venta

    @NotNull
    private LocalDateTime fechaVenta;   // Fecha y hora de la venta

    @NotNull
    @Positive
    private Double totalVenta; // Monto total de la venta
}
