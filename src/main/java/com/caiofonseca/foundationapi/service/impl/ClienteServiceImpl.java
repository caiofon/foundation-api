package com.caiofonseca.foundationapi.service.impl;

import com.caiofonseca.foundationapi.model.entity.Cliente;
import com.caiofonseca.foundationapi.model.repository.ClienteRepository;
import com.caiofonseca.foundationapi.service.ClienteService;
import org.springframework.stereotype.Service;

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

}
