package com.caiofonseca.foundationapi.model.repository;


import com.caiofonseca.foundationapi.model.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{

    @Query(value = " select case when ( count(c.id) = 0 ) then true  else false end " +
            " from Cidade c where c.id = :cidade ")
    boolean existsCidade( @Param("cidade") long cidade );


}
