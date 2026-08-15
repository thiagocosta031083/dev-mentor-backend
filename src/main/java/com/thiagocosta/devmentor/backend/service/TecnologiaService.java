package com.thiagocosta.devmentor.backend.service;

import com.thiagocosta.devmentor.backend.domain.model.*;
import com.thiagocosta.devmentor.backend.dto.request.TecnologiaRequestDTO;
import com.thiagocosta.devmentor.backend.exception.*;
import com.thiagocosta.devmentor.backend.repository.TecnologiaRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TecnologiaService {
    private final TecnologiaRepository repository;
    private final UsuarioService usuarios;

    public TecnologiaService(TecnologiaRepository repository, UsuarioService usuarios) {
        this.repository = repository;
        this.usuarios = usuarios;
    }

    @Transactional(readOnly = true)
    public List<Tecnologia> listar(String email) {
        Usuario u = usuarios.porEmail(email);
        return repository.findAllByUsuarioIdOrderByNomeAsc(u.getId());
    }

    @Transactional(readOnly = true)
    public Tecnologia buscar(Long id, String email) {
        Tecnologia t =
                repository
                        .findById(id)
                        .orElseThrow(
                                () -> new ResourceNotFoundException("Tecnologia não encontrada"));
        if (!t.getUsuario().getEmail().equalsIgnoreCase(email))
            throw new ResourceNotFoundException("Tecnologia não encontrada");
        return t;
    }

    @Transactional
    public Tecnologia criar(TecnologiaRequestDTO r, String email) {
        if (repository.existsByNomeIgnoreCase(r.getNome().trim()))
            throw new ConflictException("Já existe uma tecnologia com este nome");
        Usuario u = usuarios.porEmail(email);
        return repository.save(
                new Tecnologia(
                        r.getNome().trim(),
                        r.getTipo(),
                        r.getDescricao(),
                        r.getCargaHorariaPlanejada(),
                        u));
    }

    @Transactional
    public Tecnologia atualizar(Long id, TecnologiaRequestDTO r, String email) {
        Tecnologia t = buscar(id, email);
        if (repository.existsByNomeIgnoreCaseAndIdNot(r.getNome().trim(), id))
            throw new ConflictException("Já existe uma tecnologia com este nome");
        t.atualizar(
                r.getNome().trim(), r.getTipo(), r.getDescricao(), r.getCargaHorariaPlanejada());
        return t;
    }

    @Transactional
    public Tecnologia inativar(Long id, String email) {
        Tecnologia t = buscar(id, email);
        t.inativar();
        return t;
    }

    @Transactional
    public Tecnologia ativar(Long id, String email) {
        Tecnologia t = buscar(id, email);
        t.ativar();
        return t;
    }
}
