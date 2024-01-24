package com.facundosz.pruebajava.banco.models.service;

import org.springframework.stereotype.Service;

import com.facundosz.pruebajava.banco.models.dao.ITransaccionDao;

@Service
public class TransaccionService {
    
    private final ITransaccionDao transaccionDao;

    public TransaccionService(ITransaccionDao transaccionDao) {
        this.transaccionDao = transaccionDao;
    }

}
