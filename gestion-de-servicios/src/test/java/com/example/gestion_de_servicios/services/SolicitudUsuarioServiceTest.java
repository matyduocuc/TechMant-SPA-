package com.example.gestion_de_servicios.services;

import com.example.gestion_de_servicios.dto.SolicitudConUsuarioDTO;
import com.example.gestion_de_servicios.dto.UsuarioResponseDTO;
import com.example.gestion_de_servicios.model.Solicitud;
import com.example.gestion_de_servicios.repository.SolicitudRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SolicitudUsuarioServiceTest {

    @Mock
    private SolicitudRepository solicitudRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private SolicitudUsuarioService solicitudUsuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testObtenerSolicitudesConUsuario() {
        Solicitud solicitud = new Solicitud(1L, "desc", "PC", "PENDIENTE", null, 8L);
        UsuarioResponseDTO usuario = new UsuarioResponseDTO(8L, "Carlos", "carlos@mail.com", "CLIENTE");

        when(solicitudRepository.findAll()).thenReturn(List.of(solicitud));
        when(restTemplate.getForObject("http://localhost:8081/usuarios/8", UsuarioResponseDTO.class))
                .thenReturn(usuario);

        List<SolicitudConUsuarioDTO> resultado = solicitudUsuarioService.obtenerSolicitudesConUsuario();

        assertEquals(1, resultado.size());
        assertEquals("Carlos", resultado.get(0).getUsuario().getNombre());
    }
}
