package com.example.gestion_servicios_tecnicos.repository;

import com.example.gestion_servicios_tecnicos.model.ServicioTecnico;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ServicioTecnicoRepositoryTest {

    @Autowired
    private ServicioTecnicoRepository repository;

    @Test
    void testGuardarYBuscar() {
        ServicioTecnico st = new ServicioTecnico(null, 10L, 20L, 30L, "Test", "FINALIZADO", LocalDate.now());
        ServicioTecnico saved = repository.save(st);

        assertThat(saved.getId()).isNotNull();
        assertThat(repository.findById(saved.getId())).isPresent();
    }
}
