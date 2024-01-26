package com.facundosz.pruebajava.banco.controllers;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.facundosz.pruebajava.banco.models.entity.Cliente;
import com.facundosz.pruebajava.banco.models.entity.Cuenta;
import com.facundosz.pruebajava.banco.models.entity.TipoTransaccion;
import com.facundosz.pruebajava.banco.models.entity.Transaccion;
import com.facundosz.pruebajava.banco.models.service.ClienteService;
import com.facundosz.pruebajava.banco.models.service.CuentaService;
import com.facundosz.pruebajava.banco.models.service.TipoTransaccionService;
import com.facundosz.pruebajava.banco.models.service.TransaccionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@CrossOrigin(origins = {"http://localhost:5173" })
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
   public List<TipoTransaccion> geTipos() {
       List<TipoTransaccion> tiposTrans = tipoTranService.getAll();
       return tiposTrans;
   }
   
  @GetMapping("/clientes")
    public List<Cliente> mostrarClientes() {
        List<Cliente> clientes = clienteService.findAll(); 
        return clientes;
    }

    @GetMapping("/cuentas")
    public List<Cuenta> mostrarCuentas() {
        List<Cuenta> Cuentas = cuentaService.findAll(); 
        return Cuentas;
    }
        
   @GetMapping("/getDui/{dui}")
   public ResponseEntity<Cliente> getClientDui(@PathVariable String dui) {
       try {
            Cliente cliente = clienteService.findByDui(dui);

            if (cliente != null) {
                return new ResponseEntity<>(cliente, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
  }
  
  @GetMapping("/cuentas/{dui}")
    public ResponseEntity<List<Cuenta>> getCuentasByDui(@PathVariable String dui) {
        try {
            Cliente cliente = clienteService.findByDui(dui);

            if (cliente != null) {
                List<Cuenta> cuentas = cuentaService.findByCliente(cliente);
                if (!cuentas.isEmpty()) {
                    return new ResponseEntity<>(cuentas, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

  @PostMapping("/registerClient")
    public ResponseEntity<Object> createClient(@RequestBody Cliente newClient) {
    
        if (clienteService.existsByDui(newClient.getDui())) {
            return ResponseEntity.badRequest().body("El DUI ya está registrado");
        }

        Cliente createdClient = clienteService.saveCliente(newClient);
        return ResponseEntity.ok(createdClient);
    }

    @PostMapping("/createAccount/{dui}")
      public ResponseEntity<Object> createAccount(@RequestBody Cuenta newAccount, @PathVariable String dui) {
           
        Cliente cliente = clienteService.findByDui(dui);
       
        if (cliente == null) {
            return ResponseEntity.badRequest().body("El cliente con el DUI proporcionado no existe");
        }
        newAccount.setCliente(cliente);


        Cuenta createdAccount = cuentaService.saveCuenta(newAccount);
        
        if (createdAccount == null) {
            throw new RuntimeException("La cuenta no se pudo guardar.");
        }
        
        return ResponseEntity.ok(createdAccount);
    }

    @PostMapping("/createTransaction/{numero_cuenta}/{tipoTransaccion}/{valorTransaccion}")
    public ResponseEntity<Object> createTransaction(@PathVariable Long numero_cuenta, @PathVariable int tipoTransaccion, @PathVariable BigDecimal valorTransaccion) {
        Transaccion trans = new Transaccion();
        try {
            Cuenta cuenta = cuentaService.findByNumeroCuenta(numero_cuenta);
    
            if (cuenta != null && 'A' == cuenta.getEstado_cuenta()) {
                trans.setCuenta(cuenta);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
    
            TipoTransaccion tipoTransaccionObject = tipoTranService.findById(tipoTransaccion);
                
            if (tipoTransaccionObject != null) {
                if (tipoTransaccionObject.getId_tipo_transaccion() == 2 && cuenta.getSaldo().compareTo(valorTransaccion) >= 0) {
    
                    BigDecimal nuevoSaldo = cuenta.getSaldo().subtract(valorTransaccion);
                    cuenta.setSaldo(nuevoSaldo);
    
                    cuentaService.saveCuenta(cuenta);
                    trans.setTipoTransaccion(tipoTransaccionObject);
    
                } else if (tipoTransaccionObject.getId_tipo_transaccion() == 1) {
                    BigDecimal nuevoSaldo = cuenta.getSaldo().add(valorTransaccion);
                    cuenta.setSaldo(nuevoSaldo);
                    cuentaService.saveCuenta(cuenta);
                    trans.setTipoTransaccion(tipoTransaccionObject);
    
                } else {
                    return new ResponseEntity<>("El valor de la transacción excede el saldo de la cuenta", HttpStatus.BAD_REQUEST);
                }
    
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);      
            }
    
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("Error en el formato de los datos", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error interno del servidor", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    
        // Guardo la transacción
        Transaccion savedTrans = transaccionService.saveTransaccion(trans);
        return ResponseEntity.ok(savedTrans);
    }
    
    
}
