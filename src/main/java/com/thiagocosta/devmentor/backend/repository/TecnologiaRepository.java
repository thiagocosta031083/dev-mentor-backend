package com.thiagocosta.devmentor.backend.repository;

import com.thiagocosta.devmentor.backend.domain.enums.StatusTecnologia;
import com.thiagocosta.devmentor.backend.domain.model.Tecnologia;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TecnologiaRepository extends JpaRepository<Tecnologia, Long> {
    Optional<Tecnologia> findByNomeIgnoreCase(String nome);

    List<Tecnologia> findAllByUsuarioIdOrderByNomeAsc(Long usuarioId);

    List<Tecnologia> findAllByUsuarioIdAndStatusOrderByNomeAsc(
            Long usuarioId, StatusTecnologia status);

    boolean existsByNomeIgnoreCase(String nome);

    boolean existsByNomeIgnoreCaseAndIdNot(String nome, Long id);
}
