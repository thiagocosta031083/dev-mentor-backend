package com.thiagocosta.devmentor.backend.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import com.thiagocosta.devmentor.backend.dto.request.PlanoEstudoRequestDTO;
import com.thiagocosta.devmentor.backend.exception.BusinessException;
import com.thiagocosta.devmentor.backend.repository.PlanejamentoEstudoRepository;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class PlanoEstudoServiceTest {
    @Test
    void deveRejeitarPeriodoInvalido() {
        PlanoEstudoService service =
                new PlanoEstudoService(
                        mock(PlanejamentoEstudoRepository.class), mock(TecnologiaService.class));
        PlanoEstudoRequestDTO request = new PlanoEstudoRequestDTO();
        request.setTecnologiaId(1L);
        request.setDataInicio(LocalDate.of(2026, 8, 15));
        request.setDataFim(LocalDate.of(2026, 8, 15));
        request.setHorasPlanejadasTotais(20.0);
        request.setHorasSemanais(5.0);
        assertThrows(BusinessException.class, () -> service.criar(request, "user@test.com"));
    }
}
