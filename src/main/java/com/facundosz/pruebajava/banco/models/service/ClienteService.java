package com.facundosz.pruebajava.banco.models.service;

import java.util.Date;
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

    public boolean existsByDui(String dui) {
        return clienteDao.existsByDui(dui);
    }

    public List<Cliente> findAll(){  
        return (List<Cliente>) clienteDao.findAll();
    }

    public Cliente findByDui(String dui) {
        return clienteDao.findByDui(dui);
    }

    public Cliente saveCliente(Cliente cliente) {
        
        cliente.setFecha_registro(new Date());
        Cliente savedCliente = clienteDao.save(cliente);
        if (savedCliente == null) {
            throw new RuntimeException("El cliente no se pudo guardar.");
        }
        return savedCliente;
    }
    
}
