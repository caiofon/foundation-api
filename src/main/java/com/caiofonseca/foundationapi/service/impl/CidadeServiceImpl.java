package com.caiofonseca.foundationapi.service.impl;

import com.caiofonseca.foundationapi.model.entity.Cidade;
import com.caiofonseca.foundationapi.model.repository.CidadeRepository;
import com.caiofonseca.foundationapi.service.CidadeService;
import org.springframework.stereotype.Service;


@Service
public class CidadeServiceImpl implements CidadeService {

    private CidadeRepository repository;

    public CidadeServiceImpl(CidadeRepository repository) {
        this.repository = repository;
    }

    @Override
    public Cidade save(Cidade cidade) {
        return repository.save(cidade);
    }
}
