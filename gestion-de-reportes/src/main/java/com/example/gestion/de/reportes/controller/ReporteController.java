package com.example.gestion.de.reportes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gestion.de.reportes.model.Reporte;
import com.example.gestion.de.reportes.services.ReporteService;

@RestController
@RequestMapping("/api/reportes")
public class ReporteController {
      @Autowired
    private ReporteService reporteService;

    @PostMapping
    public Reporte crear(@RequestBody Reporte reporte) {
        return reporteService.crearReporte(reporte);
    }

    @GetMapping
    public List<Reporte> listarTodos() {
        return reporteService.listarTodos();
    }

    @GetMapping("/cliente/{id}")
    public List<Reporte> porCliente(@PathVariable Long id) {
        return reporteService.listarPorCliente(id);
    }

    @GetMapping("/tecnico/{id}")
    public List<Reporte> porTecnico(@PathVariable Long id) {
        return reporteService.listarPorTecnico(id);
    }

}
