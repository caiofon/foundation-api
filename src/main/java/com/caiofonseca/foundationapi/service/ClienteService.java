package com.caiofonseca.foundationapi.service;

import com.caiofonseca.foundationapi.api.dto.ClienteDTO;
import com.caiofonseca.foundationapi.model.entity.Cidade;
import com.caiofonseca.foundationapi.model.entity.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ClienteService {

    Cliente save(Cliente any);

    Page<Cliente> find(Cliente any, Pageable any1);

    Optional<Cliente> getClienteById(Long id);

}
