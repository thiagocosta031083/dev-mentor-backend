package com.thiagocosta.devmentor.backend.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.thiagocosta.devmentor.backend.domain.model.RegistroEstudo;
import com.thiagocosta.devmentor.backend.domain.model.Tecnologia;
import com.thiagocosta.devmentor.backend.domain.model.ConteudoPlanejado;

public interface RegistroEstudoRepository extends JpaRepository<RegistroEstudo, Long> {

    List<RegistroEstudo> findByConteudoPlanejado(ConteudoPlanejado conteudoPlanejado);

    @Query(
            "SELECT COALESCE(SUM(r.tempoGastoMinutos), 0) " +
                    "FROM RegistroEstudo r " +
                    "WHERE r.tecnologia = :tecnologia"
    )
    Long somarTempoPorTecnologia(@Param("tecnologia") Tecnologia tecnologia);

    @Query(
            "SELECT COALESCE(SUM(r.tempoGastoMinutos), 0) " +
                    "FROM RegistroEstudo r " +
                    "WHERE r.tecnologia = :tecnologia " +
                    "AND r.data BETWEEN :inicio AND :fim"
    )
    Long somarTempoPorTecnologiaEPeriodo(
            @Param("tecnologia") Tecnologia tecnologia,
            @Param("inicio") LocalDate inicio,
            @Param("fim") LocalDate fim
    );
}
