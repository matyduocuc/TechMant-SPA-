package com.example.Gestion_de_ticket.repository;

import com.example.Gestion_de_ticket.model.Ticket;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 👈 evita que reemplace tu DataSource
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:mysql://localhost:3306/db_ticket",
        "spring.datasource.username=root",
        "spring.datasource.password=",
        "spring.jpa.hibernate.ddl-auto=update",
        "spring.jpa.show-sql=true",
        "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect"
})
public class TicketRepositoryTest {

    @Autowired
    private TicketRepository repository;

    @Test
    void testGuardarYBuscarTicket() {
        Ticket ticket = Ticket.builder()
                .idUsuario(1L)
                .idEquipo(2L)
                .idSolicitud(3L)
                .problemaReportado("Pantalla no enciende")
                .estado("NUEVO")
                .diagnosticoTecnico("Sin diagnosticar")
                .fechaCreacion(LocalDateTime.now())
                .fechaActualizacion(LocalDateTime.now())
                .build();

        Ticket saved = repository.save(ticket);

        assertThat(saved.getId()).isNotNull();
        assertThat(repository.findById(saved.getId())).isPresent();
    }
}
