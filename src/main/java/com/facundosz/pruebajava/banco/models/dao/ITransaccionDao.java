package com.facundosz.pruebajava.banco.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.facundosz.pruebajava.banco.models.entity.Transaccion;

public interface ITransaccionDao extends CrudRepository<Transaccion, Integer>{
    
}
