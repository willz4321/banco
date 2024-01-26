package com.facundosz.pruebajava.banco.models.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.facundosz.pruebajava.banco.models.dao.ICuentaDao;
import com.facundosz.pruebajava.banco.models.entity.Cliente;
import com.facundosz.pruebajava.banco.models.entity.Cuenta;

@Service
public class CuentaService {

    private final ICuentaDao cuentaDao;

    public CuentaService(ICuentaDao cuentaDao) {
        this.cuentaDao = cuentaDao;
    }
   
     public List<Cuenta> findAll(){  
        return (List<Cuenta>) cuentaDao.findAll();
    }

     public List<Cuenta> findByCliente(Cliente cliente) {
        return cuentaDao.findByCliente(cliente);
    }

    public Cuenta findByNumeroCuenta(Long id) {
            return cuentaDao.findByNumeroCuenta(id);
        }

    public Cuenta saveCuenta(Cuenta cuenta) {
        
        cuenta.setFecha_apertura(new Date());
        Cuenta savedCuenta = cuentaDao.save(cuenta);
        if (savedCuenta == null) {
            throw new RuntimeException("La cuenta no se pudo guardar.");
        }
        return savedCuenta;
    }
}
