package com.facundosz.pruebajava.banco.models.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.facundosz.pruebajava.banco.models.dao.IClienteDao;
import com.facundosz.pruebajava.banco.models.entity.Cliente;

@Service
public class ClienteService {
    
    private final IClienteDao clienteDao;

    public ClienteService(IClienteDao clienteDao) {
        this.clienteDao = clienteDao;
    }

    public List<Cliente> findAll(){  
        return (List<Cliente>) clienteDao.findAll();
    }
}
