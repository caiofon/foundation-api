package com.caiofonseca.foundationapi.service.impl;

import com.caiofonseca.foundationapi.model.entity.Cidade;
import com.caiofonseca.foundationapi.model.repository.CidadeRepository;
import com.caiofonseca.foundationapi.service.CidadeService;
import org.springframework.data.domain.*;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;


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

    @Override
    public Page<Cidade> find(Cidade filter, Pageable pageRequest) {
        Example<Cidade> example = Example.of(filter,
                ExampleMatcher
                        .matching()
                        .withIgnoreCase()
                        .withIgnoreNullValues()
                        .withStringMatcher( ExampleMatcher.StringMatcher.CONTAINING )
        ) ;

        return repository.findAll(example, pageRequest);
    }

    @Override
    public Optional<Cidade> getCidadeById(Long id) {
        return repository.findById(id);
    }
}


