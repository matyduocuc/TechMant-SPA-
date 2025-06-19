package com.example.gestion_de_repuestos.services;

import com.example.gestion_de_repuestos.model.Repuesto;
import com.example.gestion_de_repuestos.repository.RepuestoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
class RepuestoServiceTest {

    @Mock
    private RepuestoRepository repo;

    @InjectMocks
    private RepuestoService service;

    @Test
    void testListar() {
        when(repo.findAll()).thenReturn(Collections.emptyList());
        List<Repuesto> resultado = service.listar();
        assertThat(resultado).isEmpty();
    }
}
