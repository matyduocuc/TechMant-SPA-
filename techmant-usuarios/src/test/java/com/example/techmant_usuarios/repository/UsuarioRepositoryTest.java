
package com.example.techmant_usuarios.repository;

import com.example.techmant_usuarios.model.Rol;
import com.example.techmant_usuarios.model.Usuario;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Test
    void testFindByCorreo() {
        Rol rol = new Rol(null, "CLIENTE");
        rolRepository.save(rol);
        Usuario usuario = new Usuario(null, "Juan", "juan@example.com", "1234", rol);
        usuarioRepository.save(usuario);

        Optional<Usuario> found = usuarioRepository.findByCorreo("juan@example.com");

        assertTrue(found.isPresent());
    }
}
