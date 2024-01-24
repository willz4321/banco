package com.facundosz.pruebajava.banco.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.facundosz.pruebajava.banco.models.entity.Cuenta;

public interface ICuentaDao extends CrudRepository<Cuenta, Long>{
    
}
