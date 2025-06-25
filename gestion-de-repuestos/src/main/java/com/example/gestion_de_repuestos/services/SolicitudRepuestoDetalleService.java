package com.example.gestion_de_repuestos.services;

import com.example.gestion_de_repuestos.dto.SolicitudDTO;
import com.example.gestion_de_repuestos.dto.SolicitudRepuestoConDetalleDTO;
import com.example.gestion_de_repuestos.model.SolicitudRepuesto;
import com.example.gestion_de_repuestos.repository.SolicitudRepuestoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class SolicitudRepuestoDetalleService {

    @Autowired
    private SolicitudRepuestoRepository repo;

    @Autowired
    private RestTemplate restTemplate;

    private final String URL_SOLICITUDES = "http://localhost:8082/api/solicitudes";

    public List<SolicitudRepuestoConDetalleDTO> listarConSolicitud() {
        List<SolicitudRepuesto> solicitudes = repo.findAll();
        List<SolicitudRepuestoConDetalleDTO> resultado = new ArrayList<>();

        for (SolicitudRepuesto sr : solicitudes) {
            SolicitudDTO solicitud = null;
            try {
                solicitud = restTemplate.getForObject(
                        URL_SOLICITUDES + "/" + sr.getSolicitudId(), SolicitudDTO.class);
            } catch (Exception e) {
                System.out.println("Error al obtener solicitud ID " + sr.getSolicitudId() + ": " + e.getMessage());
            }

            resultado.add(new SolicitudRepuestoConDetalleDTO(sr, solicitud));
        }

        return resultado;
    }
}