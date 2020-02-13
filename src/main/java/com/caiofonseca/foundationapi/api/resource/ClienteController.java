package com.caiofonseca.foundationapi.api.resource;


import com.caiofonseca.foundationapi.api.dto.ClienteDTO;
import com.caiofonseca.foundationapi.model.entity.Cidade;
import com.caiofonseca.foundationapi.model.entity.Cliente;
import com.caiofonseca.foundationapi.service.CidadeService;
import com.caiofonseca.foundationapi.service.ClienteService;
import com.caiofonseca.foundationapi.util.DataTools;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {


    private final ClienteService clienteService;
    private final CidadeService cidadeService;
    private ModelMapper modelMapper;


    public ClienteController(ClienteService clienteService, CidadeService cidadeService, ModelMapper mapper) {
        this.clienteService = clienteService;
        this.cidadeService = cidadeService;
        this.modelMapper = mapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteDTO create(@RequestBody @Valid ClienteDTO dto) throws ParseException {

        // Recupera Cidade a partir do codigo de cidade informado
       Cidade cidade = cidadeService
                .getCidadeById(dto.getCidade())
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cidade não cadastrada"));


        // Calcula a Idade baseado na data de nascimento
        Date dataSistema = new Date();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String stringDataSistema = df.format(dataSistema);
        DataTools dataTools = new DataTools();
        int idadeAtual = dataTools.getAnosEntreDuasDatas(dto.getDataNascimento(), stringDataSistema);
        dto.setIdade(idadeAtual);

        Cliente entity = modelMapper.map(dto, Cliente.class);
        entity = clienteService.save(entity);
        return modelMapper.map(entity,ClienteDTO.class);
    }

    @GetMapping
    public Page<ClienteDTO> find(ClienteDTO dto, Pageable pageRequest ){
        Cliente filter = modelMapper.map(dto, Cliente.class);
        Page<Cliente> result = clienteService.find(filter, pageRequest);
        List<ClienteDTO> list = result.getContent()
                .stream()
                .map(entity -> modelMapper.map(entity, ClienteDTO.class))
                .collect(Collectors.toList());

        return new PageImpl<ClienteDTO>( list, pageRequest, result.getTotalElements() );
    }

}
