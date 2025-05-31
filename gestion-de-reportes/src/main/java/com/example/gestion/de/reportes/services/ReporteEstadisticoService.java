package com.example.gestion.de.reportes.services;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ReporteEstadisticoService {
     @Autowired
    private RestTemplate restTemplate;

    private final String URL_VENTAS = "http://localhost:8086/api/ventas";

    public List<Map<String, Object>> obtenerTodasLasVentas() {
        Map<String, Object>[] respuesta = restTemplate.getForObject(URL_VENTAS, Map[].class);
        return Arrays.asList(respuesta);
    }

    public Double calcularTotalRecaudado() {
        return obtenerTodasLasVentas().stream()
                .mapToDouble(v -> ((Number) v.get("totalVenta")).doubleValue())
                .sum();
    }

    public Map<Long, Double> totalRecaudadoPorTecnico() {
        return obtenerTodasLasVentas().stream()
                .filter(v -> v.get("tecnicoId") != null)
                .collect(Collectors.groupingBy(
                        v -> ((Number) v.get("tecnicoId")).longValue(),
                        Collectors.summingDouble(v -> ((Number) v.get("totalVenta")).doubleValue())
                ));
    }

    public Map<Long, Double> totalRecaudadoPorCliente() {
        return obtenerTodasLasVentas().stream()
                .filter(v -> v.get("clienteId") != null)
                .collect(Collectors.groupingBy(
                        v -> ((Number) v.get("clienteId")).longValue(),
                        Collectors.summingDouble(v -> ((Number) v.get("totalVenta")).doubleValue())
                ));
    }
}
