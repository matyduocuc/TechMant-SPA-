package com.example.gestion_de_servicios.services;

import com.example.gestion_de_servicios.model.Solicitud;
import com.example.gestion_de_servicios.repository.SolicitudRepository;
import com.example.gestion_de_servicios.dto.UsuarioResponseDTO;
import com.example.gestion_de_servicios.dto.SolicitudConUsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class SolicitudUsuarioService {

    @Autowired
    private SolicitudRepository solicitudRepository;

    @Autowired
    private RestTemplate restTemplate;

    private final String URL_USUARIOS = "http://localhost:8081/usuarios";

    public List<SolicitudConUsuarioDTO> obtenerSolicitudesConUsuario() {
        List<Solicitud> solicitudes = solicitudRepository.findAll();
        List<SolicitudConUsuarioDTO> resultado = new ArrayList<>();

        for (Solicitud solicitud : solicitudes) {
            UsuarioResponseDTO usuario = null;

            if (solicitud.getClienteId() != null) {
                try {
                    usuario = restTemplate.getForObject(
                            URL_USUARIOS + "/" + solicitud.getClienteId(), UsuarioResponseDTO.class);
                } catch (HttpClientErrorException.NotFound e) {
                    System.out.println("Usuario no encontrado con ID: " + solicitud.getClienteId());
                } catch (Exception e) {
                    System.out.println("Error al obtener usuario con ID " + solicitud.getClienteId() + ": " + e.getMessage());
                }
            }

            resultado.add(new SolicitudConUsuarioDTO(solicitud, usuario));
        }

        return resultado;
    }
}

