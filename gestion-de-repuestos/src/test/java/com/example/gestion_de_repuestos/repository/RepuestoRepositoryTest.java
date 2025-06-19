package com.example.gestion_de_repuestos.repository;

import com.example.gestion_de_repuestos.model.Repuesto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // usa tu MySQL real
public class RepuestoRepositoryTest {

    @Autowired
    private RepuestoRepository repo;

    @Test
    void guardarYListar() {
        Repuesto repuesto = new Repuesto(null, "Pantalla LCD", "Pantalla 15 pulgadas", 20, 150.00);
        repo.save(repuesto);

        List<Repuesto> lista = repo.findAll();

        assertThat(lista).isNotEmpty();
        assertThat(lista.get(0).getNombre()).isEqualTo("Pantalla LCD");
    }
}
