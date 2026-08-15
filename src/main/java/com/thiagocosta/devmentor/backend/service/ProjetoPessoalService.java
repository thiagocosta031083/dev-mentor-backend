package com.thiagocosta.devmentor.backend.service;

import com.thiagocosta.devmentor.backend.domain.model.*;
import com.thiagocosta.devmentor.backend.dto.request.ProjetoPessoalRequestDTO;
import com.thiagocosta.devmentor.backend.exception.ResourceNotFoundException;
import com.thiagocosta.devmentor.backend.repository.ProjetoPessoalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ProjetoPessoalService {
    private final ProjetoPessoalRepository repository;private final UsuarioService usuarios;private final TecnologiaService tecnologias;
    public ProjetoPessoalService(ProjetoPessoalRepository r,UsuarioService u,TecnologiaService t){repository=r;usuarios=u;tecnologias=t;}
    @Transactional(readOnly=true) public List<ProjetoPessoal> listar(String email){return repository.findAllByUsuarioIdOrderByIdDesc(usuarios.porEmail(email).getId());}
    @Transactional(readOnly=true) public ProjetoPessoal buscar(Long id,String email){ProjetoPessoal p=repository.findById(id)
        .orElseThrow(()->new ResourceNotFoundException("Projeto não encontrado"));if(!p.getUsuario().getEmail().equalsIgnoreCase(email))
        throw new ResourceNotFoundException("Projeto não encontrado");return p;}
    @Transactional public ProjetoPessoal criar(ProjetoPessoalRequestDTO r,String email){Usuario u=usuarios.porEmail(email);
        Tecnologia t=r.getTecnologiaId()==null?null:tecnologias.buscar(r.getTecnologiaId(),email);
        return repository.save(new ProjetoPessoal(r.getNome().trim(),r.getStack().trim(),r.getStatus(),r.getProximoPasso(),t,u));}
    @Transactional public ProjetoPessoal atualizar(Long id,ProjetoPessoalRequestDTO r,String email){ProjetoPessoal p=buscar(id,email);
        Tecnologia t=r.getTecnologiaId()==null?null:tecnologias.buscar(r.getTecnologiaId(),email);p.setNome(r.getNome().trim());
        p.setStack(r.getStack().trim());p.setStatus(r.getStatus());p.setProximoPasso(r.getProximoPasso());p.setTecnologia(t);return p;}
}
