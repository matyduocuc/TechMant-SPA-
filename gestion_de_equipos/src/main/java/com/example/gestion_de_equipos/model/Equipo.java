package com.example.gestion_de_equipos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "equipos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Equipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del equipo es obligatorio")
    private String nombre;

    @NotBlank(message = "La marca es obligatoria")
    private String marca;

    private String modelo;

    @NotBlank(message = "El tipo de equipo es obligatorio")
    private String tipo; // ej: "Computadora", "Impresora", etc.

    private String descripcion;
}
