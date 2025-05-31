package com.example.gestion.de.servicios.Model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Solicitud {
    @Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

private String descripcion;

private String tipoEquipo;

private String estado = "PENDIENTE";

private LocalDate fechaCreacion = LocalDate.now();

private Long clienteId; // ID del usuario con rol CLIENTE (relación lógica)



}
