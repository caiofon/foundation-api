package com.caiofonseca.foundationapi.service;

import com.caiofonseca.foundationapi.api.dto.ClienteDTO;
import com.caiofonseca.foundationapi.model.entity.Cidade;
import com.caiofonseca.foundationapi.model.entity.Cliente;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.text.ParseException;
import java.util.Optional;

public interface ClienteService {

    Cliente save(Cliente any) throws ParseException;

    Page<Cliente> find(Cliente any, Pageable any1);

    Optional<Cliente> getById(Long id);

    void delete(Cliente cliente);

    Cliente update(Cliente cliente);


}
