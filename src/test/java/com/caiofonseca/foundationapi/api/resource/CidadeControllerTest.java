package com.caiofonseca.foundationapi.api.resource;

import com.caiofonseca.foundationapi.api.dto.cidadeDTO;
import com.caiofonseca.foundationapi.model.entity.Cidade;
import com.caiofonseca.foundationapi.service.CidadeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


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



    @Test
    @DisplayName("Cria cidade com sucesso")
    public void createCidadeTest() throws Exception {

        cidadeDTO dto = cidadeDTO.builder().nome("Sao Paulo").uf("SP").build();
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

        String json = new ObjectMapper().writeValueAsString(new cidadeDTO());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(CIDADE_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest() );



    }


}
