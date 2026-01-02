package com.thiagocosta.devmentor.backend.repository;

import java.util.Optional;

import com.thiagocosta.devmentor.backend.domain.model.PlanejamentoEstudo;
import org.springframework.data.jpa.repository.JpaRepository;
import com.thiagocosta.devmentor.backend.domain.model.Tecnologia;

public interface PlanenamentoEstudoRepository extends JpaRepository<PlanejamentoEstudo, Long> {

    Optional<PlanejamentoEstudo> findByTecnologiaAndAtivoTrue(Tecnologia tecnologia);

    boolean existsByTecnologiaAndAtivoTrue(Tecnologia tecnologia);
}
