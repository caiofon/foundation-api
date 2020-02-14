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

    @GetMapping("{id}")
    public ClienteDTO get( @PathVariable Long id ){

        return clienteService
                .getById(id)
                .map( cliente -> modelMapper.map(cliente, ClienteDTO.class)  )
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND) );
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        Cliente cliente = clienteService.getById(id).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND) );
        clienteService.delete(cliente);
    }

    @PutMapping("{id}")
    public ClienteDTO update( @PathVariable Long id, @RequestBody @Valid ClienteDTO dto){

        return clienteService.getById(id).map( cliente -> {

            cliente.setNome(dto.getNome());

            clienteService.update(cliente);
            return modelMapper.map(cliente, ClienteDTO.class);


        }).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND) );
    }


}
