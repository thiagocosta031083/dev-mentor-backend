package com.thiagocosta.devmentor.backend.repository;

import com.thiagocosta.devmentor.backend.domain.enums.StatusConteudo;
import com.thiagocosta.devmentor.backend.domain.model.ConteudoPlanejado;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConteudoPlanejadoRepository extends JpaRepository<ConteudoPlanejado, Long> {
    List<ConteudoPlanejado> findAllByTecnologiaIdOrderByIdAsc(Long tecnologiaId);

    List<ConteudoPlanejado> findAllByTecnologiaIdAndStatus(
            Long tecnologiaId, StatusConteudo status);

    long countByTecnologiaId(Long tecnologiaId);

    long countByTecnologiaIdAndStatus(Long tecnologiaId, StatusConteudo status);
}
