package com.caiofonseca.foundationapi.api.resource;

import com.caiofonseca.foundationapi.api.dto.CidadeDTO;
import com.caiofonseca.foundationapi.model.entity.Cidade;
import com.caiofonseca.foundationapi.service.CidadeService;
import com.caiofonseca.foundationapi.service.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;



@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest
@AutoConfigureMockMvc
public class CidadeControllerTest {

    static String CIDADE_API = "/api/cidades";

    @Autowired
    MockMvc mvc;

    @MockBean
    CidadeService service;

    @MockBean
    ClienteService clienteService;


    @Test
    @DisplayName("Cria cidade com sucesso")
    public void createCidadeTest() throws Exception {

        CidadeDTO dto = CidadeDTO.builder().nome("Sao Paulo").uf("SP").build();
        Cidade savedCidade = Cidade.builder().id((long) 1).nome("Sao Paulo").uf("SP").build();
        BDDMockito.given(service.save(Mockito.any(Cidade.class))).willReturn(savedCidade);

        String json = new ObjectMapper().writeValueAsString(dto);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(CIDADE_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated() )
                .andExpect(MockMvcResultMatchers.jsonPath("id").isNotEmpty() )
                .andExpect(MockMvcResultMatchers.jsonPath("nome").isNotEmpty() )
                .andExpect(MockMvcResultMatchers.jsonPath("uf").isNotEmpty() );
    }

    @Test
    @DisplayName("Lanca erros de validacao ao criar Cidade com dados insuficientes")
    public void createInvalidCidadeTest() throws Exception{

        String json = new ObjectMapper().writeValueAsString(new CidadeDTO());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(CIDADE_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest() )
        ;

    }


    @Test
    @DisplayName("Deve filtrar cidades")
    public void findCidadeTest() throws Exception{

        String nome = "Sao Paulo";
        String uf = "SP";

        Cidade cidade =  Cidade.builder()
                .id((long) 1)
                .nome(nome)
                .uf(uf)
                .build();

        BDDMockito.given( service.find(Mockito.any(Cidade.class), Mockito.any(Pageable.class)) )
                .willReturn( new PageImpl<Cidade>( Arrays.asList(cidade), PageRequest.of(0,100), 1 )   );

        String queryString = String.format("?name=%s&uf=%s&page=0&size=100",
                cidade.getNome(), cidade.getUf());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(CIDADE_API.concat(queryString))
                .accept(MediaType.APPLICATION_JSON);

        mvc
                .perform( request )
                .andExpect( MockMvcResultMatchers.status().isOk() )
                .andExpect( MockMvcResultMatchers.jsonPath("content", Matchers.hasSize(1)))
                .andExpect( MockMvcResultMatchers.jsonPath("totalElements").value(1) )
                .andExpect( MockMvcResultMatchers.jsonPath("pageable.pageSize").value(100) )
                .andExpect( MockMvcResultMatchers.jsonPath("pageable.pageNumber").value(0));
    }


}
