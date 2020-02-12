package com.caiofonseca.foundationapi.service;

import com.caiofonseca.foundationapi.model.entity.Cidade;
import com.caiofonseca.foundationapi.model.repository.CidadeRepository;
import com.caiofonseca.foundationapi.service.impl.CidadeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")

public class CidadeServiceTest {

    CidadeService service;
    @MockBean
    CidadeRepository repository;

    @BeforeEach
    public void  setUp(){
        this.service = new CidadeServiceImpl(repository);
    }

    @Test
    @DisplayName("Deve salvar uma cidade")
    public void saveCidadeTest(){

        Cidade cidade = Cidade.builder().nome("Sao Paulo").uf("SP").build();

        Mockito.when(repository
                .save(cidade))
                .thenReturn(Cidade.builder()
                        .id((long) 1)
                        .nome("Sao Paulo")
                        .uf("SP")
                        .build());


        Cidade savedCidade = service.save(cidade);

        assertThat(savedCidade.getId()).isNotNull();
        assertThat(savedCidade.getNome()).isEqualTo("Sao Paulo");
        assertThat(savedCidade.getUf()).isEqualTo("SP");



    }
}
