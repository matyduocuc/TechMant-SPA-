package com.example.gestion.reportes.controller;

import com.example.gestion.reportes.dto.ReporteConUsuarioDTO;
import com.example.gestion.reportes.model.Reporte;
import com.example.gestion.reportes.services.ReporteService;
import com.example.gestion.reportes.services.ReporteUsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reportes")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    @Autowired
    private ReporteUsuarioService reporteUsuarioService;

    @Operation(summary = "Crear un nuevo reporte", description = "Crea y registra un nuevo reporte en el sistema.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Reporte creado correctamente", content = @Content(schema = @Schema(implementation = Reporte.class))),
        @ApiResponse(responseCode = "400", description = "Solicitud mal formada"),
        @ApiResponse(responseCode = "500", description = "Error interno")
    })
    @PostMapping
    public ResponseEntity<Reporte> crear(@RequestBody Reporte reporte) {
        Reporte reporteCreado = reporteService.crearReporte(reporte);
        return ResponseEntity.status(201).body(reporteCreado);
    }

    @Operation(summary = "Listar todos los reportes")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de reportes obtenida correctamente"),
        @ApiResponse(responseCode = "204", description = "No hay reportes"),
        @ApiResponse(responseCode = "500", description = "Error interno")
    })
    @GetMapping
    public List<Reporte> listarTodos() {
        return reporteService.listarTodos();
    }

    @Operation(summary = "Reportes por cliente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reportes del cliente obtenidos"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @GetMapping("/cliente/{id}")
    public List<Reporte> porCliente(@PathVariable Long id) {
        return reporteService.listarPorCliente(id);
    }

    @Operation(summary = "Reportes por técnico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reportes del técnico obtenidos"),
        @ApiResponse(responseCode = "404", description = "Técnico no encontrado")
    })
    @GetMapping("/tecnico/{id}")
    public List<Reporte> porTecnico(@PathVariable Long id) {
        return reporteService.listarPorTecnico(id);
    }

    @Operation(summary = "Reportes por fecha")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reportes filtrados por fecha"),
        @ApiResponse(responseCode = "400", description = "Fecha mal formada")
    })
    @GetMapping("/fecha/{fecha}")
    public List<Reporte> porFecha(@PathVariable String fecha) {
        return reporteService.listarPorFecha(LocalDate.parse(fecha));
    }

    @Operation(summary = "Reportes por estado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reportes filtrados por estado")
    })
    @GetMapping("/estado/{estado}")
    public List<Reporte> porEstado(@PathVariable String estado) {
        return reporteService.listarPorEstado(estado);
    }

    @Operation(summary = "Reportes por título")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reportes filtrados por título")
    })
    @GetMapping("/titulo/{titulo}")
    public List<Reporte> porTitulo(@PathVariable String titulo) {
        return reporteService.listarPorTitulo(titulo);
    }

    @Operation(summary = "Actualizar reporte")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reporte actualizado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "404", description = "Reporte no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Reporte> actualizarReporte(@PathVariable Long id, @RequestBody Reporte reporte) {
        Reporte reporteActualizado = reporteService.actualizarReporte(id, reporte);
        return ResponseEntity.ok(reporteActualizado);
    }

    @Operation(summary = "Eliminar reporte")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Reporte eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Reporte no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReporte(@PathVariable Long id) {
        reporteService.eliminarReporte(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Listar reportes con datos de usuario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de reportes con usuarios",
            content = @Content(schema = @Schema(implementation = ReporteConUsuarioDTO.class)))
    })
    @GetMapping("/detalle-usuarios")
    public List<ReporteConUsuarioDTO> detalleConUsuarios() {
        return reporteUsuarioService.obtenerReportesConUsuarios();
    }
}
