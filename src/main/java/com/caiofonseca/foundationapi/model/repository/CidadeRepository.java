package com.caiofonseca.foundationapi.model.repository;

import com.caiofonseca.foundationapi.model.entity.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CidadeRepository extends JpaRepository<Cidade, Long> {

}
