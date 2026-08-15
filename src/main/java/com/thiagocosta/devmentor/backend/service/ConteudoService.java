package com.thiagocosta.devmentor.backend.service;

import com.thiagocosta.devmentor.backend.domain.enums.NivelDominio;
import com.thiagocosta.devmentor.backend.domain.model.*;
import com.thiagocosta.devmentor.backend.dto.request.ConteudoRequestDTO;
import com.thiagocosta.devmentor.backend.exception.*;
import com.thiagocosta.devmentor.backend.repository.ConteudoPlanejadoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ConteudoService {
    private final ConteudoPlanejadoRepository repository; private final TecnologiaService tecnologias;
    public ConteudoService(ConteudoPlanejadoRepository repository,TecnologiaService tecnologias){this.repository=repository;this.tecnologias=tecnologias;}
    @Transactional(readOnly=true) public ConteudoPlanejado buscar(Long id,String email){ConteudoPlanejado c=repository.findById(id)
            .orElseThrow(()->new ResourceNotFoundException("Conteúdo não encontrado"));
        tecnologias.buscar(c.getTecnologia().getId(),email);return c;}
    @Transactional(readOnly=true) public List<ConteudoPlanejado> listar(Long tecnologiaId,String email){tecnologias.buscar(tecnologiaId,email);
        return repository.findAllByTecnologiaIdOrderByIdAsc(tecnologiaId);}
    @Transactional public ConteudoPlanejado criar(ConteudoRequestDTO r,String email){Tecnologia t=tecnologias.buscar(r.getTecnologiaId(),email);
        return repository.save(new ConteudoPlanejado(t,r.getTitulo().trim(),r.getTipo(),r.getPeso()));}
    @Transactional public ConteudoPlanejado atualizar(Long id,ConteudoRequestDTO r,String email){ConteudoPlanejado c=buscar(id,email);
        if(!c.getTecnologia().getId().equals(r.getTecnologiaId()))throw new BusinessException("A tecnologia do conteúdo não pode ser alterada");
        c.atualizar(r.getTitulo().trim(),r.getTipo(),r.getPeso());return c;}
    @Transactional public ConteudoPlanejado iniciar(Long id,String email){ConteudoPlanejado c=buscar(id,email);
        if(c.getStatus().name().equals("CONCLUIDO"))throw new BusinessException("Conteúdo concluído não pode ser reiniciado");c.iniciar();return c;}
    @Transactional public ConteudoPlanejado concluir(Long id,NivelDominio nivel,String email){ConteudoPlanejado c=buscar(id,email);
        if(nivel==null)throw new BusinessException("Nível de domínio é obrigatório para conteúdo concluído");c.concluir(nivel);return c;}
}
