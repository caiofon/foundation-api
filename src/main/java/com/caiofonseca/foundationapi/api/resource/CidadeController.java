package com.caiofonseca.foundationapi.api.resource;

import com.caiofonseca.foundationapi.api.dto.cidadeDTO;
import com.caiofonseca.foundationapi.model.entity.Cidade;
import com.caiofonseca.foundationapi.service.CidadeService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/cidades")
public class CidadeController {

    private CidadeService service;
    private ModelMapper modelMapper;

    public CidadeController(CidadeService service, ModelMapper mapper) {
        this.service = service;
        this.modelMapper = mapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public cidadeDTO create(@RequestBody @Valid cidadeDTO dto){

        Cidade entity = modelMapper.map(dto, Cidade.class);
        entity = service.save(entity);
        return modelMapper.map(entity, cidadeDTO.class);
    }


}
