package com.thiagocosta.devmentor.backend.repository;

import java.util.List;

import com.thiagocosta.devmentor.backend.domain.model.ConteudoPlanejado;
import org.springframework.data.jpa.repository.JpaRepository;
import com.thiagocosta.devmentor.backend.domain.enums.StatusConteudo;
import com.thiagocosta.devmentor.backend.domain.model.Tecnologia;

public interface ConteudoPlanejadoRepository extends JpaRepository<ConteudoPlanejado, Long> {

    List<ConteudoPlanejado> findByTecnologia(Tecnologia tecnologia);

    List<ConteudoPlanejado> findByTecnologiaId(Long tecnologiaId);

    List<ConteudoPlanejado> findByTecnologiaIdAndStatus(Long tecnologiaId, StatusConteudo status);

    long countByTecnologia(Tecnologia tecnologia);

    long countByTecnologiaAndStatus(Tecnologia tecnologia, StatusConteudo status);
}
