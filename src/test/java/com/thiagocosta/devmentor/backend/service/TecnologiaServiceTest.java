package com.thiagocosta.devmentor.backend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.thiagocosta.devmentor.backend.domain.enums.StatusTecnologia;
import com.thiagocosta.devmentor.backend.domain.model.Tecnologia;
import com.thiagocosta.devmentor.backend.domain.model.Usuario;
import com.thiagocosta.devmentor.backend.repository.TecnologiaRepository;
import com.thiagocosta.devmentor.backend.repository.UsuarioRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TecnologiaServiceTest {

    @Mock
    private TecnologiaRepository tecnologiaRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private TecnologiaService tecnologiaService;

    private Tecnologia tecnologia;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario("João", "joao@example.com");
        tecnologia = new Tecnologia("Java", "Linguagem", usuario);
    }

    @Test
    void testCriarTecnologiaComSucesso() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(tecnologiaRepository.save(any(Tecnologia.class))).thenReturn(tecnologia);

        Tecnologia resultado = tecnologiaService.criar(1L, "Java", "Linguagem");

        assertNotNull(resultado);
        verify(tecnologiaRepository, times(1)).save(any(Tecnologia.class));
    }

    @Test
    void testListarTecnologiasDoUsuario() {
        List<Tecnologia> tecnologias = Arrays.asList(tecnologia);
        when(tecnologiaRepository.findByUsuarioId(1L)).thenReturn(tecnologias);

        List<Tecnologia> resultado = tecnologiaService.listarPorUsuario(1L);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }

    @Test
    void testBuscarTecnologiaPorIdComSucesso() {
        when(tecnologiaRepository.findById(1L)).thenReturn(Optional.of(tecnologia));

        Optional<Tecnologia> resultado = tecnologiaService.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Java", resultado.get().getNome());
    }

}
