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
                ExampleMatcher.matchingAny());

        return repository.findAll(example, pageRequest);
    }

    @Override
    public Optional<Cliente> getById(Long id) {
        return this.repository.findById(id);
    }

    @Override
    public void delete(Cliente cliente) {
        if(cliente == null || cliente.getId() == null){
            throw new IllegalArgumentException("Identificado do cliente nao pode ser nulo");
        }
        this.repository.delete(cliente);
    }

    @Override
    public Cliente update(Cliente cliente) {
        if(cliente == null || cliente.getId() == null){
            throw new IllegalArgumentException("Identificado cliente nao pode ser nulo");
        }
        return this.repository.save(cliente);
    }

}
