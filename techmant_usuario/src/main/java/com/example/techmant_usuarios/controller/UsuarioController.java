package com.example.techmant_usuarios.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.techmant_usuarios.DTOs.UsuarioRequestDTO;
import com.example.techmant_usuarios.DTOs.UsuarioResponseDTO;
import com.example.techmant_usuarios.services.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Operation(summary = "Registrar un nuevo usuario",
               description = "Este endpoint permite registrar un nuevo usuario en el sistema.",
               responses = {
                   @ApiResponse(responseCode = "200", description = "Usuario registrado correctamente", 
                                content = @Content(schema = @Schema(implementation = UsuarioResponseDTO.class))),
                   @ApiResponse(responseCode = "400", description = "Datos inválidos")
               })
    @PostMapping("/registrar")
    public UsuarioResponseDTO registrarUsuario(@RequestBody UsuarioRequestDTO usuarioRequestDTO) {
        return usuarioService.registrar(usuarioRequestDTO);
    }

    @Operation(summary = "Login de un usuario",
               description = "Este endpoint permite realizar el login de un usuario utilizando su correo y contraseña.",
               responses = {
                   @ApiResponse(responseCode = "200", description = "Login exitoso", 
                                content = @Content(schema = @Schema(implementation = UsuarioResponseDTO.class))),
                   @ApiResponse(responseCode = "400", description = "Correo o contraseña incorrectos")
               })
    @PostMapping("/login")
    public UsuarioResponseDTO login(@RequestBody UsuarioRequestDTO usuarioRequestDTO) {
        return usuarioService.login(usuarioRequestDTO.getCorreo(), usuarioRequestDTO.getContrasena());
    }

    @Operation(summary = "Obtener todos los usuarios",
               description = "Este endpoint permite obtener la lista de todos los usuarios registrados en el sistema.",
               responses = {
                   @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida correctamente", 
                                content = @Content(schema = @Schema(implementation = UsuarioResponseDTO.class)))
               })
    @GetMapping
    public List<UsuarioResponseDTO> obtenerUsuarios() {
        return usuarioService.obtenerUsuarios();
    }

    @Operation(summary = "Actualizar un usuario",
               description = "Este endpoint permite actualizar los detalles de un usuario.",
               responses = {
                   @ApiResponse(responseCode = "200", description = "Usuario actualizado correctamente", 
                                content = @Content(schema = @Schema(implementation = UsuarioResponseDTO.class))),
                   @ApiResponse(responseCode = "400", description = "Datos inválidos"),
                   @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
               })
    @PutMapping("/{id}")
    public UsuarioResponseDTO actualizarUsuario(@PathVariable Long id, @RequestBody UsuarioRequestDTO usuarioRequestDTO) {
        return usuarioService.actualizarUsuario(id, usuarioRequestDTO);
    }

    @Operation(summary = "Eliminar un usuario",
               description = "Este endpoint permite eliminar un usuario del sistema.",
               responses = {
                   @ApiResponse(responseCode = "200", description = "Usuario eliminado correctamente"),
                   @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
               })
    @DeleteMapping("/{id}")
    public void eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
    }
}
