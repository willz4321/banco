package com.facundosz.pruebajava.banco.models.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.facundosz.pruebajava.banco.models.entity.Cliente;
import com.facundosz.pruebajava.banco.models.entity.Cuenta;

public interface ICuentaDao extends CrudRepository<Cuenta, Long>{
    Cuenta findByNumeroCuenta(Long numeroCuenta);
     List<Cuenta> findByCliente(Cliente cliente);
}
