package com.example.techmant_usuarios.controller;

import com.example.techmant_usuarios.DTOs.UsuarioRequestDTO;
import com.example.techmant_usuarios.DTOs.UsuarioResponseDTO;
import com.example.techmant_usuarios.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Método POST para registrar un nuevo usuario
    @PostMapping("/registrar")
    @Operation(summary = "Registrar un nuevo usuario")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Usuario registrado correctamente"),
        @ApiResponse(responseCode = "400", description = "Correo duplicado o datos inválidos")
    })
    public ResponseEntity<UsuarioResponseDTO> registrarUsuario(@RequestBody UsuarioRequestDTO usuarioRequestDTO) {
        UsuarioResponseDTO registrado = usuarioService.registrar(usuarioRequestDTO);
        return ResponseEntity.status(201).body(registrado);
    }

    // Método POST para login de usuario
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UsuarioRequestDTO usuarioRequestDTO) {
        String token = usuarioService.login(usuarioRequestDTO.getCorreo(), usuarioRequestDTO.getContrasena());
        return ResponseEntity.ok(token);
    }

    // Método GET para obtener todos los usuarios
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> obtenerUsuarios() {
        List<UsuarioResponseDTO> lista = usuarioService.obtenerUsuarios();
        return lista.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(lista);
    }

    // Método GET para obtener un usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> obtenerPorId(@PathVariable Long id) {
        return usuarioService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Método PUT para actualizar un usuario
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> actualizarUsuario(@PathVariable Long id, @RequestBody UsuarioRequestDTO usuarioRequestDTO) {
        return ResponseEntity.ok(usuarioService.actualizarUsuario(id, usuarioRequestDTO));
    }

    // Método DELETE para eliminar un usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
