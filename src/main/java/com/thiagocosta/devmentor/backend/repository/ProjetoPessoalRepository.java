package com.thiagocosta.devmentor.backend.repository;

import com.thiagocosta.devmentor.backend.domain.enums.StatusProjeto;
import com.thiagocosta.devmentor.backend.domain.model.ProjetoPessoal;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProjetoPessoalRepository extends JpaRepository<ProjetoPessoal, Long> {
    List<ProjetoPessoal> findAllByUsuarioIdOrderByIdDesc(Long usuarioId);
    List<ProjetoPessoal> findAllByStatusOrderByIdDesc(StatusProjeto status);
    List<ProjetoPessoal> findAllByTecnologiaId(Long tecnologiaId);
}
