package com.example.gestion.de.reportes.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gestion.de.reportes.services.ReporteEstadisticoService;


@RestController
@RequestMapping("/api/reportes")
public class ReporteEstadisticoController {

    @Autowired
    private ReporteEstadisticoService servicio;


    // GETs para estadísticas
    @GetMapping("/ventas")
    public List<Map<String, Object>> listarVentas() {
        return servicio.obtenerTodasLasVentas();
    }

    @GetMapping("/ventas/total")
    public Double totalRecaudado() {
        return servicio.calcularTotalRecaudado();
    }

    @GetMapping("/ventas/total-por-tecnico")
    public Map<Long, Double> totalPorTecnico() {
        return servicio.totalRecaudadoPorTecnico();
    }

    @GetMapping("/ventas/total-por-cliente")
    public Map<Long, Double> totalPorCliente() {
        return servicio.totalRecaudadoPorCliente();
    }
}
