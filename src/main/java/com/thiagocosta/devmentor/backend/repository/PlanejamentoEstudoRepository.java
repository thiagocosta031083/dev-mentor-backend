package com.thiagocosta.devmentor.backend.repository;

import java.time.LocalDate;
import java.util.Optional;

import com.thiagocosta.devmentor.backend.domain.model.PlanejamentoEstudo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.thiagocosta.devmentor.backend.domain.model.Tecnologia;

public interface PlanejamentoEstudoRepository extends JpaRepository<PlanejamentoEstudo, Long> {

    Optional<PlanejamentoEstudo> findByConteudoId(Long conteudoId);

    @Query("SELECT p FROM PlanejamentoEstudo p " +
            "WHERE p.conteudo.tecnologia = :tecnologia " +
            "AND p.dataInicio <= :data AND p.dataFim >= :data")
    Optional<PlanejamentoEstudo> findAtivoByTecnologia(
            @Param("tecnologia") Tecnologia tecnologia,
            @Param("data") LocalDate data
    );
}
