package com.facundosz.pruebajava.banco.models.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.facundosz.pruebajava.banco.models.dao.ITipoTransaccionDao;
import com.facundosz.pruebajava.banco.models.entity.Cuenta;
import com.facundosz.pruebajava.banco.models.entity.Tipo;
import com.facundosz.pruebajava.banco.models.entity.TipoTransaccion;

@Service
public class TipoTransaccionService {
    
    private final ITipoTransaccionDao tipoTransaccionDao;

    public TipoTransaccionService(ITipoTransaccionDao tipoTransaccionDao) {
        this.tipoTransaccionDao = tipoTransaccionDao;
    }

       public List<TipoTransaccion> getAll() {
        return (List<TipoTransaccion>) tipoTransaccionDao.findAll();
    }

    public TipoTransaccion findByNombre(Tipo nombre_transaccion) {
    return tipoTransaccionDao.findByNombre(nombre_transaccion);
   }
   
    public TipoTransaccion findById(int id) {
            return tipoTransaccionDao.findById(id);
        }

    }