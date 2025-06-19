package com.example.gestion_de_repuestos.controller;

import com.example.gestion_de_repuestos.model.Repuesto;
import com.example.gestion_de_repuestos.services.RepuestoService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
class RepuestoControllerTest {

    @Mock
    private RepuestoService repuestoService;

    @InjectMocks
    private RepuestoController controller;

    @Test
    void testListar() {
        when(repuestoService.listar()).thenReturn(Collections.emptyList());
        List<Repuesto> resultado = controller.listar();
        assertThat(resultado).isEmpty();
    }
}
