package com.thiagocosta.devmentor.backend.repository;

import com.thiagocosta.devmentor.backend.domain.model.Tecnologia;
import com.thiagocosta.devmentor.backend.domain.enums.StatusTecnologia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TecnologiaRepository extends JpaRepository<Tecnologia, Long> {

    Optional<Tecnologia> findByNomeIgnoreCase(String nome);

    List<Tecnologia> findByUsuarioId(Long usuarioId);

    List<Tecnologia> findByUsuarioIdAndStatusAtiva(Long usuarioId, StatusTecnologia status);

    List<Tecnologia> findByUsuarioIdAndNome(Long usuarioId, String nome);
}
