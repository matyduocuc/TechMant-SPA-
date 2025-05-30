package com.techmant.controller;

import com.techmant.dto.UsuarioRequestDTO;
import com.techmant.dto.UsuarioResponseDTO;
import com.techmant.model.Usuario;
import com.techmant.service.UsuarioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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