package com.example.techmant_usuarios.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.techmant_usuarios.model.Rol;
import com.example.techmant_usuarios.repository.RolRepository;

@Component
public class StartupDataLoader implements CommandLineRunner {

    private final RolRepository rolRepository;

    public StartupDataLoader(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Verifica si los roles ya existen
        if (rolRepository.findAll().isEmpty()) {
            rolRepository.save(new Rol(null, "ADMIN"));
            rolRepository.save(new Rol(null, "TECNICO"));
            rolRepository.save(new Rol(null, "CLIENTE"));
            System.out.println("⚙️ Roles insertados automáticamente.");
        }
    }
}
