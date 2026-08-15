package com.thiagocosta.devmentor.backend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.thiagocosta.devmentor.backend.domain.enums.StatusConteudo;
import com.thiagocosta.devmentor.backend.domain.model.ConteudoPlanejado;
import com.thiagocosta.devmentor.backend.domain.model.Tecnologia;
import com.thiagocosta.devmentor.backend.domain.model.Usuario;
import com.thiagocosta.devmentor.backend.repository.ConteudoPlanejadoRepository;
import com.thiagocosta.devmentor.backend.repository.TecnologiaRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConteudoServiceTest {

    @Mock
    private ConteudoPlanejadoRepository conteudoRepository;

    @Mock
    private TecnologiaRepository tecnologiaRepository;

    @InjectMocks
    private ConteudoService conteudoService;

    private ConteudoPlanejado conteudo;
    private Tecnologia tecnologia;

    @BeforeEach
    void setUp() {
        Usuario usuario = new Usuario("João", "joao@example.com");
        tecnologia = new Tecnologia("Java", "Linguagem de programação", usuario);
        conteudo = new ConteudoPlanejado("Collections em Java", "Aprendo Collections", tecnologia);
    }

    @Test
    void testIniciarConteudo() {
        when(conteudoRepository.findById(1L)).thenReturn(Optional.of(conteudo));
        when(conteudoRepository.save(any(ConteudoPlanejado.class))).thenReturn(conteudo);

        conteudoService.iniciar(1L);

        assertEquals(StatusConteudo.EM_ESTUDO, conteudo.getStatus());
        verify(conteudoRepository, times(1)).save(conteudo);
    }

    @Test
    void testConcluirConteudo() {
        conteudo.setStatus(StatusConteudo.EM_ESTUDO);
        when(conteudoRepository.findById(1L)).thenReturn(Optional.of(conteudo));
        when(conteudoRepository.save(any(ConteudoPlanejado.class))).thenReturn(conteudo);

        conteudoService.concluir(1L);

        assertEquals(StatusConteudo.CONCLUIDO, conteudo.getStatus());
        verify(conteudoRepository, times(1)).save(conteudo);
    }

    @Test
    void testObterConteudoComSucesso() {
        when(conteudoRepository.findById(1L)).thenReturn(Optional.of(conteudo));

        ConteudoPlanejado resultado = conteudoService.obter(1L);

        assertNotNull(resultado);
        assertEquals("Collections em Java", resultado.getTitulo());
    }

}

