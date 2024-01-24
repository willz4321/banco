package com.facundosz.pruebajava.banco.models.service;

import org.springframework.stereotype.Service;

import com.facundosz.pruebajava.banco.models.dao.ICuentaDao;

@Service
public class CuentaService {

    private final ICuentaDao cuentaDao;

    public CuentaService(ICuentaDao cuentaDao) {
        this.cuentaDao = cuentaDao;
    }
   
    
}
