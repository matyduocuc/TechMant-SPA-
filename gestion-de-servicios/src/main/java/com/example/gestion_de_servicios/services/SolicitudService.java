package com.example.gestion_de_servicios.services;

import com.example.gestion_de_servicios.dto.SolicitudConUsuarioDTO;
import com.example.gestion_de_servicios.dto.UsuarioResponseDTO;
import com.example.gestion_de_servicios.model.Solicitud;
import com.example.gestion_de_servicios.repository.SolicitudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.*;

@Service
public class SolicitudService {

    @Autowired
    private SolicitudRepository repo;

    @Autowired
    private RestTemplate restTemplate;

    private final String URL_USUARIOS = "http://localhost:8081/usuarios";

    public Solicitud guardar(Solicitud solicitud) {
        if (solicitud.getEstado() == null || solicitud.getEstado().isBlank())
            solicitud.setEstado("PENDIENTE");
        if (solicitud.getFechaCreacion() == null)
            solicitud.setFechaCreacion(LocalDate.now());
        return repo.save(solicitud);
    }

    public List<Solicitud> listar() {
        return repo.findAll();
    }

    public Optional<Solicitud> buscarPorId(Long id) {
        return repo.findById(id);
    }

    public List<Solicitud> listarPorCliente(Long clienteId) {
        return repo.findByClienteId(clienteId);
    }

    public Optional<Solicitud> actualizar(Long id, Solicitud actualizada) {
        return repo.findById(id).map(s -> {
            s.setDescripcion(actualizada.getDescripcion());
            s.setTipoEquipo(actualizada.getTipoEquipo());
            s.setEstado(actualizada.getEstado());
            s.setFechaCreacion(actualizada.getFechaCreacion());
            s.setClienteId(actualizada.getClienteId());
            return repo.save(s);
        });
    }

    public boolean eliminar(Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        }
        return false;
    }

    public List<SolicitudConUsuarioDTO> listarConUsuario() {
        List<Solicitud> solicitudes = repo.findAll();
        List<SolicitudConUsuarioDTO> resultado = new ArrayList<>();

        for (Solicitud solicitud : solicitudes) {
            UsuarioResponseDTO usuario = restTemplate.getForObject(
                URL_USUARIOS + "/" + solicitud.getClienteId(), UsuarioResponseDTO.class);
            resultado.add(new SolicitudConUsuarioDTO(solicitud, usuario));
        }

        return resultado;
    }
}
