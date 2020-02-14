package com.caiofonseca.foundationapi.service;


import com.caiofonseca.foundationapi.model.entity.Cliente;
import com.caiofonseca.foundationapi.model.repository.ClienteRepository;
import com.caiofonseca.foundationapi.service.impl.ClienteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import java.text.ParseException;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class ClienteServiceTest {

    ClienteService service;
    @MockBean
    ClienteRepository repository;

    @BeforeEach
    public void  setUp(){
        this.service = new ClienteServiceImpl(repository);
    }

    @Test
    @DisplayName("Deve salvar um Cliente")
    public void saveClienteTest() throws ParseException {

        Cliente cliente = Cliente.builder()
                .nome("Caio Fonseca")
                .dataNascimento("24/04/1974")
                .idade(45)
                .cidade((long) 1)
                .build();


        Mockito.when(repository
                .save(cliente))
                .thenReturn(Cliente.builder()
                        .id((long) 1)
                        .nome("Caio Fonseca")
                        .dataNascimento("24/04/1974")
                        .idade(45)
                        .cidade((long) 1)
                        .build());

        try {

            Cliente savedCliente = service.save(cliente);
            assertThat(savedCliente.getId()).isNotNull();
            assertThat(savedCliente.getNome()).isEqualTo("Caio Fonseca");

        }   catch (ParseException pe) {
            pe.printStackTrace();
        }




    }

}
