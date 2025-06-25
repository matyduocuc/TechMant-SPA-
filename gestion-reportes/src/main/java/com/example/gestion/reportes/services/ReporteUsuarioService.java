package com.example.gestion.reportes.services;

import com.example.gestion.reportes.dto.ReporteConUsuarioDTO;
import com.example.gestion.reportes.dto.UsuarioResponseDTO;
import com.example.gestion.reportes.model.Reporte;
import com.example.gestion.reportes.repository.ReporteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReporteUsuarioService {

    @Autowired
    private ReporteRepository reporteRepository;

    @Autowired
    private RestTemplate restTemplate;

    private final String URL_USUARIOS = "http://localhost:8081/usuarios";

    public List<ReporteConUsuarioDTO> obtenerReportesConUsuarios() {
        List<Reporte> reportes = reporteRepository.findAll();
        List<ReporteConUsuarioDTO> resultado = new ArrayList<>();

        for (Reporte r : reportes) {
            UsuarioResponseDTO cliente = null;
            UsuarioResponseDTO tecnico = null;

            try {
                if (r.getClienteId() != null) {
                    cliente = restTemplate.getForObject(URL_USUARIOS + "/" + r.getClienteId(), UsuarioResponseDTO.class);
                }
                if (r.getTecnicoId() != null) {
                    tecnico = restTemplate.getForObject(URL_USUARIOS + "/" + r.getTecnicoId(), UsuarioResponseDTO.class);
                }
            } catch (Exception e) {
                System.out.println("Error consultando usuarios: " + e.getMessage());
            }

            resultado.add(new ReporteConUsuarioDTO(r, cliente, tecnico));
        }

        return resultado;
    }
}
