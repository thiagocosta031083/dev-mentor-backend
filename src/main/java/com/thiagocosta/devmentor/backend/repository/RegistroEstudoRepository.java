package com.thiagocosta.devmentor.backend.repository;

import com.thiagocosta.devmentor.backend.domain.model.RegistroEstudo;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RegistroEstudoRepository extends JpaRepository<RegistroEstudo, Long> {
    List<RegistroEstudo> findAllByTecnologiaIdOrderByDataDesc(Long tecnologiaId);

    List<RegistroEstudo> findAllByConteudoPlanejadoIdOrderByDataDesc(Long conteudoId);

    @Query(
            "select coalesce(sum(r.tempoMinutos), 0) from RegistroEstudo r where r.tecnologia.id = :tecnologiaId")
    Long somarMinutosPorTecnologia(@Param("tecnologiaId") Long tecnologiaId);

    @Query(
            "select coalesce(sum(r.tempoMinutos), 0) from RegistroEstudo r "
                    + "where r.tecnologia.id = :tecnologiaId and r.data between :inicio and :fim")
    Long somarMinutosPorTecnologiaEPeriodo(
            @Param("tecnologiaId") Long tecnologiaId,
            @Param("inicio") LocalDate inicio,
            @Param("fim") LocalDate fim);
}
