package com.thiagocosta.devmentor.backend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.thiagocosta.devmentor.backend.domain.enums.StatusProjeto;
import com.thiagocosta.devmentor.backend.domain.model.ProjetoPessoal;
import com.thiagocosta.devmentor.backend.domain.model.Usuario;
import com.thiagocosta.devmentor.backend.repository.ProjetoPessoalRepository;
import com.thiagocosta.devmentor.backend.repository.UsuarioRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjetoPessoalServiceTest {

    @Mock
    private ProjetoPessoalRepository projetoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private ProjetoPessoalService projetoService;

    private ProjetoPessoal projeto;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario("João", "joao@example.com");
        projeto = new ProjetoPessoal(
            "E-commerce", 
            "Java + Spring Boot", 
            StatusProjeto.IDEIA, 
            "Configurar banco de dados", 
            null, 
            usuario
        );
    }

    @Test
    void testObterProjetoComSucesso() {
        when(projetoRepository.findById(1L)).thenReturn(Optional.of(projeto));

        ProjetoPessoal resultado = projetoService.obter(1L);

        assertNotNull(resultado);
        assertEquals("E-commerce", resultado.getNome());
    }

    @Test
    void testDeletarProjetoComSucesso() {
        when(projetoRepository.findById(1L)).thenReturn(Optional.of(projeto));
        doNothing().when(projetoRepository).delete(projeto);

        projetoService.deletar(1L);

        verify(projetoRepository, times(1)).delete(projeto);
    }

}
