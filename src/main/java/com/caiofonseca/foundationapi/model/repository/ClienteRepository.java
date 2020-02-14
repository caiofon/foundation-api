package com.caiofonseca.foundationapi.model.repository;

import com.caiofonseca.foundationapi.model.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{

}
