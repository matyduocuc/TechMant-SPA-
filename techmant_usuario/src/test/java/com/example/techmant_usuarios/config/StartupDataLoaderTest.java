package com.example.techmant_usuarios.config;

import com.example.techmant_usuarios.model.Rol;
import com.example.techmant_usuarios.repository.RolRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import org.mockito.ArgumentMatchers;

public class StartupDataLoaderTest {

    @Mock
    private RolRepository rolRepository;

    @InjectMocks
    private StartupDataLoader startupDataLoader;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRunWhenRolesAreEmpty() throws Exception {
        // Simula que la base de datos está vacía
        when(rolRepository.findAll()).thenReturn(java.util.Collections.emptyList());

        // Llama al método run
        startupDataLoader.run();

        // Verifica que save() se haya llamado una vez para cada rol
        verify(rolRepository, times(1)).save(ArgumentMatchers.argThat(role -> role.getNombre().equals("ADMIN")));
        verify(rolRepository, times(1)).save(ArgumentMatchers.argThat(role -> role.getNombre().equals("TECNICO")));
        verify(rolRepository, times(1)).save(ArgumentMatchers.argThat(role -> role.getNombre().equals("CLIENTE")));
    }

    @Test
    public void testRunWhenRolesAreNotEmpty() throws Exception {
        // Simula que ya existen roles
        when(rolRepository.findAll()).thenReturn(java.util.Arrays.asList(new Rol(1L, "ADMIN"), new Rol(2L, "TECNICO"), new Rol(3L, "CLIENTE")));

        // Llama al método run
        startupDataLoader.run();

        // Verifica que save() no se haya llamado
        verify(rolRepository, times(0)).save(any());
    }
}
