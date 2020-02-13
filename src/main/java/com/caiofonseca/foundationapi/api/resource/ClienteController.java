package com.caiofonseca.foundationapi.api.resource;

import com.caiofonseca.foundationapi.api.dto.ClienteDTO;
import com.caiofonseca.foundationapi.model.entity.Cidade;
import com.caiofonseca.foundationapi.model.entity.Cliente;
import com.caiofonseca.foundationapi.service.CidadeService;
import com.caiofonseca.foundationapi.service.ClienteService;
import com.caiofonseca.foundationapi.util.DataTools;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {


    private final ClienteService clienteService;
    private final CidadeService cidadeService;
    private ModelMapper modelMapper;



    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente create(@RequestBody @Valid ClienteDTO dto) throws ParseException {

        // Recupera Cidade a partir do codigo de cidade informado
       Cidade cidade = cidadeService
                .getCidadeById(dto.getCidadeId())
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cidade não cadastrada"));


        // Calcula a Idade baseado na data de nascimento
        Date dataSistema = new Date();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String stringDataSistema = df.format(dataSistema);
        DataTools dataTools = new DataTools();
        int idadeAtual = dataTools.getAnosEntreDuasDatas(dto.getDataNascimento(), stringDataSistema);
        System.out.println("idade"+ idadeAtual);


        Cliente entity = Cliente.builder()
                .nome(dto.getNome())
                .cidade(cidade)
                .dataNascimento(dto.getDataNascimento())
                .idade(idadeAtual)
                .build();

        entity = clienteService.save(entity);
        return entity;

    }

}
