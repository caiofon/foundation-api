package com.caiofonseca.foundationapi.api.resource;

import com.caiofonseca.foundationapi.api.dto.CidadeDTO;
import com.caiofonseca.foundationapi.model.entity.Cidade;
import com.caiofonseca.foundationapi.service.CidadeService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


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
    public CidadeDTO create(@RequestBody @Valid CidadeDTO dto){

        Cidade entity = modelMapper.map(dto, Cidade.class);
        entity = service.save(entity);
        CidadeDTO entityDTO = modelMapper.map(entity, CidadeDTO.class);
        return entityDTO;
    }

    @GetMapping
    public Page<CidadeDTO> find(CidadeDTO dto, Pageable pageRequest ){
        Cidade filter = modelMapper.map(dto, Cidade.class);
        Page<Cidade> result = service.find(filter, pageRequest);
        List<CidadeDTO> list = result.getContent()
                .stream()
                .map(entity -> modelMapper.map(entity, CidadeDTO.class))
                .collect(Collectors.toList());

        return new PageImpl<CidadeDTO>( list, pageRequest, result.getTotalElements() );
    }


}
