package com.facundosz.pruebajava.banco.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.facundosz.pruebajava.banco.models.entity.Cliente;
import com.facundosz.pruebajava.banco.models.service.ClienteService;
import com.facundosz.pruebajava.banco.models.service.CuentaService;
import com.facundosz.pruebajava.banco.models.service.TipoTransaccionService;
import com.facundosz.pruebajava.banco.models.service.TransaccionService;
import org.springframework.web.bind.annotation.GetMapping;



@CrossOrigin(origins = {"localhost:3000" })
@RestController
@RequestMapping("/api/banco")
public class ApiController {
     
private final ClienteService clienteService;
private final CuentaService cuentaService;
private final TipoTransaccionService tipoTranService;
private final TransaccionService transaccionService;

    public ApiController(ClienteService clienteService, CuentaService cuentaService, TipoTransaccionService tipoTranService,
            TransaccionService transaccionService) {
        this.clienteService = clienteService;
        this.cuentaService = cuentaService;
        this.tipoTranService = tipoTranService;
        this.transaccionService = transaccionService;
    }

   @GetMapping("/tipos")
   public String geTipos() {
       return "Prueba";
   }
   
  @GetMapping("/clientes")
public List<Cliente> mostrarClientes() {
    List<Cliente> clientes = clienteService.findAll(); 
    return clientes;
}

}
