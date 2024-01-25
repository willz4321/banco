package com.facundosz.pruebajava.banco.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.facundosz.pruebajava.banco.models.entity.Cliente;

public interface IClienteDao extends CrudRepository<Cliente, Integer>{
    Cliente findByDui(String dui);
    boolean existsByDui(String dui);
}
