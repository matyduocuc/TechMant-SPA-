
package com.example.techmant_usuarios.config;

import com.example.techmant_usuarios.repository.RolRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class StartupDataLoaderTest {

    @Autowired
    private StartupDataLoader startupDataLoader;

    @Test
    void testCargarRolesCuandoVacio() {
        RolRepository rolRepository = Mockito.mock(RolRepository.class);
        startupDataLoader = new StartupDataLoader(rolRepository);

        Mockito.when(rolRepository.findAll()).thenReturn(java.util.Collections.emptyList());
        Mockito.when(rolRepository.save(any())).thenReturn(null);

        startupDataLoader.run();
        Mockito.verify(rolRepository, Mockito.times(3)).save(any());
    }
}
