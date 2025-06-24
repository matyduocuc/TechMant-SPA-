package com.example.gestion_de_servicios.repository;

import com.example.gestion_de_servicios.model.Solicitud;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 👈 NECESARIO
class SolicitudRepositoryTest {

    @Autowired
    private SolicitudRepository repo;

    @Test
    void guardarYBuscarPorCliente() {
        Solicitud solicitud = new Solicitud(null, "desc", "tipo", "PENDIENTE", LocalDate.now(), 99L);
        repo.save(solicitud);

        List<Solicitud> resultados = repo.findByClienteId(99L);
        assertThat(resultados).isNotEmpty();
        assertThat(resultados.get(0).getClienteId()).isEqualTo(99L);
    }
}
