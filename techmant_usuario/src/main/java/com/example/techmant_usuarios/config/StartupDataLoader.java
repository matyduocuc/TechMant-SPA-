package com.example.techmant_usuarios.config;

import com.example.techmant_usuarios.model.Rol;
import com.example.techmant_usuarios.model.Usuario;
import com.example.techmant_usuarios.repository.RolRepository;
import com.example.techmant_usuarios.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupDataLoader implements CommandLineRunner {

    private final RolRepository rolRepository;
    private final UsuarioRepository usuarioRepository;

    public StartupDataLoader(RolRepository rolRepository, UsuarioRepository usuarioRepository) {
        this.rolRepository = rolRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Insertar roles
        if (rolRepository.count() == 0) {
            Rol adminRol = new Rol(null, "ADMIN");
            Rol clienteRol = new Rol(null, "CLIENTE");
            Rol tecnicoRol = new Rol(null, "TECNICO");

            rolRepository.save(adminRol);
            rolRepository.save(clienteRol);
            rolRepository.save(tecnicoRol);
        }

        // Insertar usuarios
        if (usuarioRepository.count() == 0) {
            Usuario adminUsuario = new Usuario();
            adminUsuario.setCorreo("admin@mail.com");
            adminUsuario.setContrasena("admin123"); // Encriptar la contraseña antes de guardar
            adminUsuario.setRol(rolRepository.findByNombre("ADMIN").orElseThrow());

            Usuario clienteUsuario = new Usuario();
            clienteUsuario.setCorreo("cliente@mail.com");
            clienteUsuario.setContrasena("cliente123"); // Encriptar la contraseña antes de guardar
            clienteUsuario.setRol(rolRepository.findByNombre("CLIENTE").orElseThrow());

            usuarioRepository.save(adminUsuario);
            usuarioRepository.save(clienteUsuario);
        }
    }
}
