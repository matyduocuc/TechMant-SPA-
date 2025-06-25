package com.example.gestion_de_ventas.services;

import com.example.gestion_de_ventas.dto.UsuarioResponseDTO;
import com.example.gestion_de_ventas.dto.VentaConUsuarioDTO;
import com.example.gestion_de_ventas.model.Venta;
import com.example.gestion_de_ventas.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class VentaUsuarioService {

    @Autowired
    private VentaRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    private final String URL_USUARIOS = "http://localhost:8081/usuarios";

    public List<VentaConUsuarioDTO> obtenerVentasConUsuarios() {
        List<Venta> ventas = repository.findAll();
        List<VentaConUsuarioDTO> resultado = new ArrayList<>();

        for (Venta venta : ventas) {
            UsuarioResponseDTO usuario = null;
            if (venta.getClienteId() != null) {
                try {
                    usuario = restTemplate.getForObject(URL_USUARIOS + "/" + venta.getClienteId(), UsuarioResponseDTO.class);
                } catch (Exception e) {
                    System.out.println("No se pudo obtener usuario: " + e.getMessage());
                }
            }
            resultado.add(new VentaConUsuarioDTO(venta, usuario));
        }

        return resultado;
    }
}