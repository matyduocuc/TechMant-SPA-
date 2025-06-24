package com.example.gestion_de_servicios.services;

import com.example.gestion_de_servicios.DTO.SolicitudConUsuarioDTO;
import com.example.gestion_de_servicios.DTO.UsuarioResponseDTO;
import com.example.gestion_de_servicios.model.Solicitud;
import com.example.gestion_de_servicios.repository.SolicitudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
                            URL_USUARIOS + "/" + solicitud.getClienteId(),
                            UsuarioResponseDTO.class
                    );
                } catch (Exception e) {
                    // Puedes registrar el error si el usuario no se encuentra o falla la conexión
                    System.err.println("Error al obtener usuario con ID: " + solicitud.getClienteId());
                }
            }

            resultado.add(new SolicitudConUsuarioDTO(solicitud, usuario));
        }

        return resultado;
    }
}
