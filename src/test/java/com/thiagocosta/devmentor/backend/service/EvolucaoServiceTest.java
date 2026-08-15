package com.thiagocosta.devmentor.backend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.thiagocosta.devmentor.backend.domain.enums.NivelDominio;
import com.thiagocosta.devmentor.backend.domain.model.Tecnologia;
import com.thiagocosta.devmentor.backend.domain.model.Usuario;
import com.thiagocosta.devmentor.backend.repository.TecnologiaRepository;
import com.thiagocosta.devmentor.backend.repository.ConteudoPlanejadoRepository;
import com.thiagocosta.devmentor.backend.repository.RegistroEstudoRepository;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EvolucaoServiceTest {

    @Mock
    private TecnologiaRepository tecnologiaRepository;

    @Mock
    private ConteudoPlanejadoRepository conteudoRepository;

    @Mock
    private RegistroEstudoRepository registroRepository;

    @InjectMocks
    private EvolucaoService evolucaoService;

    private Tecnologia tecnologia;

    @BeforeEach
    void setUp() {
        Usuario usuario = new Usuario("João", "joao@example.com");
        tecnologia = new Tecnologia("Java", "Linguagem", usuario);
    }

    @Test
    void testObterNivelProficiencia() {
        String nivel = evolucaoService.obterNivelProficiencia(NivelDominio.NIVEL_1);
        assertNotNull(nivel);
    }

}

