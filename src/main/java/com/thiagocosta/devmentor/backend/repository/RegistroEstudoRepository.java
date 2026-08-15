package com.thiagocosta.devmentor.backend.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.thiagocosta.devmentor.backend.domain.model.RegistroEstudo;
import com.thiagocosta.devmentor.backend.domain.model.Tecnologia;
import com.thiagocosta.devmentor.backend.domain.model.ConteudoPlanejado;
import com.thiagocosta.devmentor.backend.domain.enums.TipoEstudo;

public interface RegistroEstudoRepository extends JpaRepository<RegistroEstudo, Long> {

    List<RegistroEstudo> findByConteudoPlanejado(ConteudoPlanejado conteudoPlanejado);

    List<RegistroEstudo> findByConteudoId(Long conteudoId);

    List<RegistroEstudo> findByConteudoIdAndTipo(Long conteudoId, TipoEstudo tipo);

    List<RegistroEstudo> findByDataBetween(LocalDate inicio, LocalDate fim);

    @Query(
            "SELECT COALESCE(SUM(r.tempoMinutos), 0) " +
                    "FROM RegistroEstudo r " +
                    "WHERE r.conteudoPlanejado.tecnologia = :tecnologia"
    )
    Long somarTempoPorTecnologia(@Param("tecnologia") Tecnologia tecnologia);

    @Query(
            "SELECT COALESCE(SUM(r.tempoMinutos), 0) " +
                    "FROM RegistroEstudo r " +
                    "WHERE r.conteudoPlanejado.tecnologia = :tecnologia " +
                    "AND r.data BETWEEN :inicio AND :fim"
    )
    Long somarTempoPorTecnologiaEPeriodo(
            @Param("tecnologia") Tecnologia tecnologia,
            @Param("inicio") LocalDate inicio,
            @Param("fim") LocalDate fim
    );
}
