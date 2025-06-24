package com.example.techmant_usuarios.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.techmant_usuarios.DTOs.UsuarioRequestDTO;
import com.example.techmant_usuarios.DTOs.UsuarioResponseDTO;
import com.example.techmant_usuarios.services.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // ======================
    //       POST
    // ======================

    @Operation(summary = "Registrar un nuevo usuario",
               description = "Este endpoint permite registrar un nuevo usuario en el sistema.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario registrado correctamente",
                     content = @Content(schema = @Schema(implementation = UsuarioResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping("/registrar")
    public UsuarioResponseDTO registrarUsuario(@RequestBody UsuarioRequestDTO usuarioRequestDTO) {
        return usuarioService.registrar(usuarioRequestDTO);
    }

    @Operation(summary = "Login de un usuario",
               description = "Este endpoint permite realizar el login de un usuario utilizando su correo y contraseña.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login exitoso",
                     content = @Content(schema = @Schema(implementation = UsuarioResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Correo o contraseña incorrectos")
    })
    @PostMapping("/login")
    public UsuarioResponseDTO login(@RequestBody UsuarioRequestDTO usuarioRequestDTO) {
        return usuarioService.login(usuarioRequestDTO.getCorreo(), usuarioRequestDTO.getContrasena());
    }

    // ======================
    //        GET
    // ======================

    @Operation(summary = "Obtener todos los usuarios",
               description = "Este endpoint permite obtener la lista de todos los usuarios registrados en el sistema.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida correctamente",
                     content = @Content(schema = @Schema(implementation = UsuarioResponseDTO.class)))
    })
    @GetMapping
    public List<UsuarioResponseDTO> obtenerUsuarios() {
        return usuarioService.obtenerUsuarios();
    }

    @Operation(summary = "Obtener un usuario por ID",
               description = "Devuelve un usuario específico por su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> obtenerPorId(@PathVariable Long id) {
        return usuarioService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ======================
    //        PUT
    // ======================

    @Operation(summary = "Actualizar un usuario",
               description = "Este endpoint permite actualizar los detalles de un usuario.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario actualizado correctamente",
                     content = @Content(schema = @Schema(implementation = UsuarioResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @PutMapping("/{id}")
    public UsuarioResponseDTO actualizarUsuario(@PathVariable Long id, @RequestBody UsuarioRequestDTO usuarioRequestDTO) {
        return usuarioService.actualizarUsuario(id, usuarioRequestDTO);
    }

    // ======================
    //       DELETE
    // ======================

    @Operation(summary = "Eliminar un usuario",
               description = "Este endpoint permite eliminar un usuario del sistema.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @DeleteMapping("/{id}")
    public void eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
    }
}