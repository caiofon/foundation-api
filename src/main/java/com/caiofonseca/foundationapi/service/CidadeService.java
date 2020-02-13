package com.caiofonseca.foundationapi.service;

import com.caiofonseca.foundationapi.model.entity.Cidade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CidadeService {
    Cidade save(Cidade any);

    Page<Cidade> find(Cidade any, Pageable any1);

    Optional<Cidade> getCidadeById(Long id);
    }

