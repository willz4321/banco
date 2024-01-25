package com.facundosz.pruebajava.banco.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.facundosz.pruebajava.banco.models.entity.Tipo;
import com.facundosz.pruebajava.banco.models.entity.TipoTransaccion;

public interface ITipoTransaccionDao extends CrudRepository<TipoTransaccion, Integer>{
    TipoTransaccion findByNombre(Tipo nombre);
    TipoTransaccion findById(int id);
}
