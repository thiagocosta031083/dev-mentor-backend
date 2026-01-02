package com.thiagocosta.devmentor.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thiagocosta.devmentor.backend.domain.model.ProjetoPessoal;
import com.thiagocosta.devmentor.backend.domain.enums.StatusProjeto;
import com.thiagocosta.devmentor.backend.domain.model.Tecnologia;

public interface ProjetoPessoalRepository extends JpaRepository<ProjetoPessoal, Long> {

    List<ProjetoPessoal> findByTecnologia(Tecnologia tecnologia);

    List<ProjetoPessoal> findByStatus(StatusProjeto status);
}
