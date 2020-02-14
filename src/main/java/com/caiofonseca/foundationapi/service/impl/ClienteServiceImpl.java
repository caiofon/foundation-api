package com.caiofonseca.foundationapi.service.impl;

import com.caiofonseca.foundationapi.api.exceptions.BusinessException;
import com.caiofonseca.foundationapi.model.entity.Cliente;
import com.caiofonseca.foundationapi.model.repository.ClienteRepository;
import com.caiofonseca.foundationapi.service.ClienteService;
import com.caiofonseca.foundationapi.util.DataTools;
import org.springframework.data.domain.*;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    private ClienteRepository repository;

    public ClienteServiceImpl(ClienteRepository repository) {

        this.repository = repository;
    }

    @Override
    public Cliente save(Cliente cliente) {

        // Recupera Cidade a partir do codigo de cidade informado

        try {
            System.out.println("1");

            if (repository.existsCidade(cliente.getCidade())) {
                throw new BusinessException("Cidade invalida");
            }


            // Calcula a Idade baseado na data de nascimento
            System.out.println("2");
            Date dataSistema = new Date();
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            String stringDataSistema = df.format(dataSistema);
            DataTools dataTools = new DataTools();
            int idadeAtual = dataTools.getAnosEntreDuasDatas(cliente.getDataNascimento(), stringDataSistema);
            cliente.setIdade(idadeAtual);
            System.out.println("3");
            return repository.save(cliente);
        } catch (ParseException pe) {

            throw new BusinessException("Data de Nascimento Invalida");

        }


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
