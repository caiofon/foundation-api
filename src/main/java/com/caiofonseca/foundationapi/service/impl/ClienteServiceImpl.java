package com.caiofonseca.foundationapi.service.impl;

import com.caiofonseca.foundationapi.model.entity.Cliente;
import com.caiofonseca.foundationapi.model.repository.ClienteRepository;
import com.caiofonseca.foundationapi.service.ClienteService;
import org.springframework.data.domain.*;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    private ClienteRepository repository;

    public ClienteServiceImpl(ClienteRepository repository) {

        this.repository = repository;
    }

    @Override
    public Cliente save(Cliente cliente) {

        return repository.save(cliente);
    }

    @Override
    public Page<Cliente> find(Cliente filter, Pageable pageRequest) {
        Example<Cliente> example = Example.of(filter,
                ExampleMatcher
                        .matching()
                        .withIgnoreCase()
                        .withIgnoreNullValues()
                        .withStringMatcher( ExampleMatcher.StringMatcher.CONTAINING )
        );

        return repository.findAll(example, pageRequest);
    }

    @Override
    public Optional<Cliente> getClienteById(Long id) {
        return repository.findById(id);
    }
}
