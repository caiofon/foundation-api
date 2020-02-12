package com.caiofonseca.foundationapi.model.repository;

import com.caiofonseca.foundationapi.model.entity.Cidade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class CidadeRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    CidadeRepository repository;

    public static Cidade createNewCidade(String nome) {
        return Cidade.builder().nome(nome).uf("SP").build();
    }

    @Test
    @DisplayName("Deve salvar uma Cidade.")
    public void saveBookTest(){

    Cidade cidade = createNewCidade("Sao Paulo");

        Cidade savedCidade = repository.save(cidade);

        assertThat(savedCidade.getId()).isNotNull();

    }

}
