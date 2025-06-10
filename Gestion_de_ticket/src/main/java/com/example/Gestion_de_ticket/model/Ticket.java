package com.example.Gestion_de_ticket.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long idUsuario;
    private Long idEquipo;
    private Long idSolicitud;

    private String problemaReportado;
    private String estado;
    private String diagnosticoTecnico;

    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
}
