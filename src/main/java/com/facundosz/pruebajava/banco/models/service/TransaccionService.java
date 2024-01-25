package com.facundosz.pruebajava.banco.models.service;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.facundosz.pruebajava.banco.models.dao.ITransaccionDao;
import com.facundosz.pruebajava.banco.models.entity.Cuenta;
import com.facundosz.pruebajava.banco.models.entity.Transaccion;

@Service
public class TransaccionService {
    
    private final ITransaccionDao transaccionDao;

    public TransaccionService(ITransaccionDao transaccionDao) {
        this.transaccionDao = transaccionDao;
    }

   public Transaccion saveTransaccion(Transaccion trans) {
        
        trans.setFecha_transaccion(new Date());
        Transaccion savedTrans = transaccionDao.save(trans);
        if (savedTrans == null) {
            throw new RuntimeException("La cuenta no se pudo guardar.");
        }
        return savedTrans;
    }
}
