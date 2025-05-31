package com.example.gestion.de.servicios.Model;


import java.time.LocalDate;

import jakarta.annotation.sql.DataSourceDefinition;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity



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
