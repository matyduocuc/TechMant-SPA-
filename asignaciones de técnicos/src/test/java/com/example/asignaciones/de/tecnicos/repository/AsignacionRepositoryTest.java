package com.example.asignaciones.de.tecnicos.repository;

import com.example.asignaciones.de.tecnicos.model.Asignacion;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AsignacionRepositoryTest {

    @Autowired
    private AsignacionRepository repository;

    @Test
    void testGuardarYBuscarPorTecnico() {
        Asignacion a = new Asignacion(null, 1L, 99L, LocalDate.now());
        repository.save(a);

        List<Asignacion> resultados = repository.findByTecnicoId(99L);
        assertThat(resultados).isNotEmpty();
        assertThat(resultados.get(0).getTecnicoId()).isEqualTo(99L);
    }
}
