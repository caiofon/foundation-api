package com.caiofonseca.foundationapi.api.resource;

import com.caiofonseca.foundationapi.api.dto.ClienteDTO;
import com.caiofonseca.foundationapi.model.entity.Cidade;
import com.caiofonseca.foundationapi.model.entity.Cliente;
import com.caiofonseca.foundationapi.service.CidadeService;
import com.caiofonseca.foundationapi.service.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import static org.mockito.ArgumentMatchers.anyLong;
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
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest
@AutoConfigureMockMvc
public class ClienteControllerTest {

    static String CLIENTE_API = "/api/clientes";

    @Autowired
    MockMvc mvc;

    @MockBean
    private CidadeService cidadeService;

    @MockBean
    private ClienteService clienteService;

    @Test
    @DisplayName("Cria cliente com sucesso")
    public void createClienteTest() throws Exception {

        ClienteDTO dto = ClienteDTO.builder()
                .nome("Caio Fonseca")
                .dataNascimento("24/04/1974")
                .idade(45)
                .cidade((long) 1)
                .build();

        String json = new ObjectMapper().writeValueAsString(dto);

        Cidade cidade = Cidade.builder().id((long) 1).nome("Sao Paulo").uf("SP").build();
        BDDMockito.given( cidadeService.getCidadeById((long) 1)).willReturn(Optional.of(cidade) );

        Cliente cliente = Cliente.builder()
                .id((long) 1)
                .nome("Caio Fonseca")
                .dataNascimento("24/04/1974")
                .idade(45)
                .cidade((long) 1)
                .build();

        BDDMockito.given( clienteService.save(Mockito.any(Cliente.class)) ).willReturn(cliente);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(CLIENTE_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform( request )
                .andExpect(MockMvcResultMatchers.status().isCreated() )
                .andExpect(MockMvcResultMatchers.jsonPath("id").isNotEmpty() )
                .andExpect(MockMvcResultMatchers.jsonPath("nome").isNotEmpty() )
                .andExpect(MockMvcResultMatchers.jsonPath("cidade").isNotEmpty() )
                .andExpect(MockMvcResultMatchers.jsonPath("idade").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("dataNascimento").isNotEmpty() );
    }

    @Test
    @DisplayName("Lanca erros de validacao ao criar Cliente com dados insuficientes\"")
    public void createClienteInvalidoTest() throws Exception {

        ClienteDTO dto = ClienteDTO.builder()
                .nome("Caio Fonseca")
                .dataNascimento(null)
                .idade(45)
                .cidade((long) 1)
                .build();

        String json = new ObjectMapper().writeValueAsString(dto);

        Cliente cliente = Cliente.builder()
                .id((long) 1)
                .nome("Caio Fonseca")
                .dataNascimento(null)
                .idade(45)
                .build();

        BDDMockito.given( clienteService.save(Mockito.any(Cliente.class)) ).willReturn(cliente);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(CLIENTE_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
                .perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest() );
    }

    @Test
    @DisplayName("Deve filtrar clientes")
    public void findClienteTest() throws Exception{

        String nome = "Caio Fonseca";

        Cliente cliente =  Cliente.builder()
                .id((long) 1)
                .nome(nome)
                .dataNascimento("24/04/1974")
                .idade(45)
                .cidade((long) 1)
                .build();

        BDDMockito.given( clienteService.find(Mockito.any(Cliente.class), Mockito.any(Pageable.class)) )
                .willReturn( new PageImpl<Cliente>( Arrays.asList(cliente), PageRequest.of(0,100), 1 )   );

        String queryString = String.format("?name=%s&page=0&size=100",
                cliente.getNome());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(CLIENTE_API.concat(queryString))
                .accept(MediaType.APPLICATION_JSON);

        mvc
                .perform( request )
                .andExpect( MockMvcResultMatchers.status().isOk() )
                .andExpect( MockMvcResultMatchers.jsonPath("content", Matchers.hasSize(1)))
                .andExpect( MockMvcResultMatchers.jsonPath("totalElements").value(1) )
                .andExpect( MockMvcResultMatchers.jsonPath("pageable.pageSize").value(100) )
                .andExpect( MockMvcResultMatchers.jsonPath("pageable.pageNumber").value(0));
    }

    @Test
    @DisplayName("Deve obter informacoes de um Cliente.")
    public void getClienteDetailsTest() throws Exception{

        Long id = Long.valueOf(1);
        String nome = "Caio Fonseca";

        Cliente cliente = Cliente.builder()
                .id((long) 1)
                .nome(nome)
                .dataNascimento("24/04/1974")
                .idade(45)
                .cidade((long) 1)
                .build();

        BDDMockito.given( clienteService.getById(id) ).willReturn(Optional.of(cliente));


        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(CLIENTE_API.concat("/" + id))
                .accept(MediaType.APPLICATION_JSON);

        mvc
                .perform( request )
                .andExpect( MockMvcResultMatchers.status().isOk() );

    }

    @Test
    @DisplayName("Deve retornar resource not found quando o cliente procurado não existir")
    public void bookNotFoundTest() throws Exception {

        BDDMockito.given( clienteService.getById(Mockito.anyLong()) ).willReturn( Optional.empty() );

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(CLIENTE_API.concat("/" + 1))
                .accept(MediaType.APPLICATION_JSON);

        mvc
                .perform(request)
                .andExpect( MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Deve deletar um cliente")
    public void deleteClienteTest() throws Exception {

        BDDMockito.given(clienteService.getById(anyLong())).willReturn(Optional.of(Cliente.builder()
                .id((long) 1)
                .nome("Caio Fonseca")
                .dataNascimento("24/04/1974")
                .idade(45)
                .cidade((long) 1)
                .build()));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(CLIENTE_API.concat("/" + 1));

        mvc.perform( request )
                .andExpect( MockMvcResultMatchers.status().isNoContent() );
    }

    @Test
    @DisplayName("Deve retornar resource not found quando não encontrar o cliente para deletar")
    public void deleteInexistentClienteTest() throws Exception {

        BDDMockito.given(clienteService.getById(anyLong())).willReturn(Optional.empty());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(CLIENTE_API.concat("/" + 1));

        mvc.perform( request )
                .andExpect( MockMvcResultMatchers.status().isNotFound() );
    }

    @Test
    @DisplayName("Deve atualizar um cliente")
    public void updateClienteTest() throws Exception {


        ClienteDTO dto = ClienteDTO.builder()
                .id((long) 1)
                .nome("Joao da Silva")
                .dataNascimento("24/04/1974")
                .idade(45)
                .cidade((long) 1)
                .build();

        String json = new ObjectMapper().writeValueAsString(dto);

        Cliente updatingCliente = Cliente.builder()
                .id((long) 1)
                .nome("Caio Fonseca")
                .dataNascimento("24/04/1974")
                .idade(45)
                .cidade((long) 1)
                .build();

        BDDMockito.given( clienteService.getById((long) 1) ).willReturn( Optional.of(updatingCliente) );


        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(CLIENTE_API.concat("/" + 1))
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform( request )
                .andExpect( MockMvcResultMatchers.status().isOk() )
                .andExpect( MockMvcResultMatchers.jsonPath("id").value(1) )
                .andExpect( MockMvcResultMatchers.jsonPath("nome").value("Joao da Silva") );
    }



}
