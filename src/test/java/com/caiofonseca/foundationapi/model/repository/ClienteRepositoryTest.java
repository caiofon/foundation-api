package com.caiofonseca.foundationapi.model.repository;



import com.caiofonseca.foundationapi.model.entity.Cidade;
import com.caiofonseca.foundationapi.model.entity.Cliente;
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
public class ClienteRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    ClienteRepository repository;

    public static Cliente createNewCliente() {
            return  Cliente.builder()
                    .nome("Caio Fonseca")
                    .dataNascimento("24/04/1974")
                    .idade(45)
                    .cidade((long) 1)
                    .build();
    }

    @Test
    @DisplayName("Deve salvar um Cliente.")
    public void saveClienteTest(){

        Cliente cliente = createNewCliente();

        Cliente savedCliente = repository.save(cliente);

        assertThat(savedCliente.getId()).isNotNull();

    }



}

