package com.thiagocosta.devmentor.backend.repository;

import com.thiagocosta.devmentor.backend.domain.model.PlanejamentoEstudo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PlanejamentoEstudoRepository extends JpaRepository<PlanejamentoEstudo, Long> {
    List<PlanejamentoEstudo> findAllByTecnologiaIdOrderByDataInicioDesc(Long tecnologiaId);

    @Query("select p from PlanejamentoEstudo p where p.tecnologia.id = :tecnologiaId " +
           "and p.dataInicio <= :data and p.dataFim >= :data")
    Optional<PlanejamentoEstudo> findAtivoByTecnologiaId(@Param("tecnologiaId") Long tecnologiaId,
                                                         @Param("data") LocalDate data);

    @Query("select case when count(p) > 0 then true else false end from PlanejamentoEstudo p " +
           "where p.tecnologia.id = :tecnologiaId and p.dataInicio <= :fim and p.dataFim >= :inicio " +
           "and (:ignorarId is null or p.id <> :ignorarId)")
    boolean existsConflito(@Param("tecnologiaId") Long tecnologiaId,
                           @Param("inicio") LocalDate inicio,
                           @Param("fim") LocalDate fim,
                           @Param("ignorarId") Long ignorarId);
}
