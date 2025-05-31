package com.example.asignaciones.de.tecnicos.model;

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
@NoArgsConstructor
@AllArgsConstructor
public class Asignacion {

  @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long solicitudId;

    private Long tecnicoId;

    private LocalDate fechaAsignacion = LocalDate.now();

}
