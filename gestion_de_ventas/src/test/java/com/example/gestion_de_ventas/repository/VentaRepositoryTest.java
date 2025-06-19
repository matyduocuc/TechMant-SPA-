package com.example.gestion_de_ventas.repository;

import com.example.gestion_de_ventas.model.Venta;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 👈 importante
public class VentaRepositoryTest {

    @Autowired
    private VentaRepository ventaRepository;

    @Test
    void testGuardarYBuscar() {
        Venta venta = new Venta(null, 1L, 2L, 300.0, LocalDate.now());
        Venta guardada = ventaRepository.save(venta);

        Optional<Venta> resultado = ventaRepository.findById(guardada.getId());

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getTotalVenta()).isEqualTo(300.0);
    }
}