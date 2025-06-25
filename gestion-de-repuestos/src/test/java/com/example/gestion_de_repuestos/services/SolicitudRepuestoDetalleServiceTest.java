package com.example.gestion_de_repuestos.services;

import com.example.gestion_de_repuestos.dto.SolicitudDTO;
import com.example.gestion_de_repuestos.dto.SolicitudRepuestoConDetalleDTO;
import com.example.gestion_de_repuestos.model.SolicitudRepuesto;
import com.example.gestion_de_repuestos.repository.SolicitudRepuestoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static java.util.Collections.singletonList;

class SolicitudRepuestoDetalleServiceTest {

    @Mock
    private SolicitudRepuestoRepository repo;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private SolicitudRepuestoDetalleService service;

    @BeforeEach
    void setUp() {
        org.mockito.MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListarConSolicitud() {
        // Mock de entidad solicitudRepuesto
        SolicitudRepuesto solicitudRepuesto = new SolicitudRepuesto(1L, 10L, 20L, 3);

        // Mock del DTO recibido desde el microservicio de solicitudes
        SolicitudDTO solicitudDTO = new SolicitudDTO(
                10L,
                "Pantalla dañada",
                "Laptop",
                "PENDIENTE",
                LocalDate.of(2025, 6, 24),
                4L
        );

        // Mock de repositorio y restTemplate
        when(repo.findAll()).thenReturn(singletonList(solicitudRepuesto));
        when(restTemplate.getForObject("http://localhost:8082/api/solicitudes/10", SolicitudDTO.class))
                .thenReturn(solicitudDTO);

        // Ejecutar
        List<SolicitudRepuestoConDetalleDTO> resultado = service.listarConSolicitud();

        // Verificaciones
        assertEquals(1, resultado.size());
        assertEquals("Laptop", resultado.get(0).getSolicitudInfo().getTipoEquipo());
        assertEquals(3, resultado.get(0).getSolicitudRepuesto().getCantidad());
    }
}
