package com.example.techmant_usuarios.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.techmant_usuarios.DTOs.UsuarioRequestDTO;
import com.example.techmant_usuarios.DTOs.UsuarioResponseDTO;
import com.example.techmant_usuarios.model.Usuario;
import com.example.techmant_usuarios.services.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @PostMapping("/registrar")
    public UsuarioResponseDTO registrar(@RequestBody UsuarioRequestDTO dto) {
        return service.registrar(dto);
    }

    @PostMapping("/login")
    public UsuarioResponseDTO login(@RequestParam String correo, @RequestParam String contrasena) {
        return service.login(correo, contrasena);
    }

    @GetMapping
    public List<Usuario> listar() {
        return service.listarTodos();
    }
}