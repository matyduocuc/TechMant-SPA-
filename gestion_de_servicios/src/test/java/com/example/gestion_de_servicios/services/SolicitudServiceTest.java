package com.example.gestion_de_servicios.services;

import com.example.gestion_de_servicios.model.Solicitud;
import com.example.gestion_de_servicios.repository.SolicitudRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class SolicitudServiceTest {

    @Mock
    private SolicitudRepository repo;

    @InjectMocks
    private SolicitudService service;

    public SolicitudServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void guardarDebeAsignarValoresPorDefecto() {
        Solicitud solicitud = new Solicitud(null, "desc", "tipo", null, null, 1L);
        when(repo.save(any())).thenAnswer(i -> i.getArgument(0));

        Solicitud guardada = service.guardar(solicitud);

        assertThat(guardada.getEstado()).isEqualTo("PENDIENTE");
        assertThat(guardada.getFechaCreacion()).isNotNull();
        verify(repo).save(solicitud);
    }

    @Test
    void buscarPorIdDebeRetornarSolicitud() {
        Solicitud solicitud = new Solicitud(1L, "desc", "tipo", "PENDIENTE", LocalDate.now(), 1L);
        when(repo.findById(1L)).thenReturn(Optional.of(solicitud));

        Optional<Solicitud> resultado = service.buscarPorId(1L);

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getId()).isEqualTo(1L);
    }
}
