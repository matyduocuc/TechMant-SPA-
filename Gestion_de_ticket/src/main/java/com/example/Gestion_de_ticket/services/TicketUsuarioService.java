package com.example.Gestion_de_ticket.services;

import com.example.Gestion_de_ticket.dto.UsuarioResponseDTO;
import com.example.Gestion_de_ticket.model.Ticket;
import com.example.Gestion_de_ticket.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class TicketUsuarioService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private RestTemplate restTemplate;

    private final String URL_USUARIOS = "http://localhost:8081/usuarios";

    public List<Map<String, Object>> obtenerTicketsConUsuario() {
        List<Ticket> tickets = ticketRepository.findAll();
        List<Map<String, Object>> resultado = new ArrayList<>();

        for (Ticket ticket : tickets) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", ticket.getId());
            map.put("problemaReportado", ticket.getProblemaReportado());
            map.put("estado", ticket.getEstado());
            map.put("fechaCreacion", ticket.getFechaCreacion());
            map.put("fechaActualizacion", ticket.getFechaActualizacion());
            map.put("idEquipo", ticket.getIdEquipo());
            map.put("idSolicitud", ticket.getIdSolicitud());

            // Consulta usuario desde techmant-usuarios
            if (ticket.getIdUsuario() != null) {
                try {
                    UsuarioResponseDTO usuario = restTemplate.getForObject(
                            URL_USUARIOS + "/" + ticket.getIdUsuario(), UsuarioResponseDTO.class);
                    map.put("usuario", usuario);
                } catch (RestClientException ex) {
                    // En caso de error, registra mensaje claro y continúa
                    map.put("usuario", "No disponible (error al consultar usuario: " + ex.getMessage() + ")");
                }
            } else {
                map.put("usuario", "No asignado");
            }

            resultado.add(map);
        }

        return resultado;
    }
}