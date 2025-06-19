
package com.example.techmant_usuarios.config;

import com.example.techmant_usuarios.repository.RolRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

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
    public void testRunWhenRolesAreEmpty() {
        when(rolRepository.findAll()).thenReturn(java.util.Collections.emptyList());

        startupDataLoader.run();

        verify(rolRepository, times(1)).save(any());
        verify(rolRepository, times(1)).save(argThat(role -> role.getNombre().equals("ADMIN")));
        verify(rolRepository, times(1)).save(argThat(role -> role.getNombre().equals("TECNICO")));
        verify(rolRepository, times(1)).save(argThat(role -> role.getNombre().equals("CLIENTE")));
    }
}
