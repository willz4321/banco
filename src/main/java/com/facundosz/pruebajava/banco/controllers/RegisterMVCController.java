package com.facundosz.pruebajava.banco.controllers;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.facundosz.pruebajava.banco.models.entity.Cliente;
import com.facundosz.pruebajava.banco.models.entity.Cuenta;
import com.facundosz.pruebajava.banco.models.entity.TipoTransaccion;
import com.facundosz.pruebajava.banco.models.entity.Transaccion;
import com.facundosz.pruebajava.banco.models.service.ClienteService;
import com.facundosz.pruebajava.banco.models.service.CuentaService;
import com.facundosz.pruebajava.banco.models.service.TipoTransaccionService;
import com.facundosz.pruebajava.banco.models.service.TransaccionService;

@Controller
public class RegisterMVCController {

    private final ClienteService clienteService;
    private final CuentaService cuentaService;
    private final TipoTransaccionService tipoTranService;
    private final TransaccionService transaccionService;

    public RegisterMVCController(ClienteService clienteService, CuentaService cuentaService, TipoTransaccionService tipoTranService,
                              TransaccionService transaccionService) {
        this.clienteService = clienteService;
        this.cuentaService = cuentaService;
        this.tipoTranService = tipoTranService;
        this.transaccionService = transaccionService;
    }

    @GetMapping("/")
    public String showBankClientForm() {
        return "bankClient";
    }
    
    @GetMapping("/listClient")
    public String getClients(Model model) {
        List<Cliente> clientes = clienteService.findAll();
        model.addAttribute("clientes", clientes);
        return "listClient";
    }
    
    @GetMapping("/getDui")
    public String getClientDui(@RequestParam String dui, Model model) {
        try {
            Cliente cliente = clienteService.findByDui(dui);
         
            if (cliente != null) {
                model.addAttribute("cuenta", new Cuenta());
                model.addAttribute("cliente", cliente);
                return "cuenta"; 
            } else {
                model.addAttribute("cliente", new Cliente()); 
                return "cliente"; 
            }
        } catch (Exception e) {
          
            return "error"; 
        }
    }

    @GetMapping("/transaccion")
    public String showTransaccionForm(@RequestParam(name = "clienteDui") String clienteDui, Model model) {
        try {
            Cliente cliente = clienteService.findByDui(clienteDui);

            if (cliente != null) {
                List<Cuenta> cuentas = cuentaService.findByCliente(cliente);

                if (!cuentas.isEmpty()) {
                    List<TipoTransaccion> tiposTransaccion = tipoTranService.getAll();

                    model.addAttribute("cuentas", cuentas);
                    model.addAttribute("tiposTransaccion", tiposTransaccion);
                    model.addAttribute("transaccion", new Transaccion());

                    return "operationClient";
                } else {
                
                    return "redirect:/error";
                }
            } else {
                return "redirect:/error";
            }
        } catch (Exception e) {
            return "redirect:/error";
        }
    }
    @PostMapping("/registrar")
    public String registerClient(@ModelAttribute("cliente") Cliente newClient, Model model) {
    
        if (clienteService.existsByDui(newClient.getDui())) {
            model.addAttribute("error", "El DUI ya está registrado");
            return "cliente"; 
        }

        clienteService.saveCliente(newClient);
        return "redirect:/cliente/exito"; // Redirige a una página de éxito o a donde desees
    }

    @PostMapping("/guardarCuenta")
    public String guardarCuenta(@ModelAttribute("cuenta") Cuenta cuenta, @RequestParam(name = "clienteDui") String clienteDui, Model model) {
        System.out.println("datos del cliente");
        System.out.println(clienteDui);

                try {
                Cliente cliente = clienteService.findByDui(clienteDui);
                
                if (cliente == null) {
                    return "El Cliente no está en la base de datos";
        }
            
            cuenta.setCliente(cliente); 
            
            Cuenta savedCuenta = cuentaService.saveCuenta(cuenta); 
    
            model.addAttribute("cuenta", savedCuenta);
            return "redirect:/"; 
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }
    @PostMapping("/procesarTransaccion")
     public String createTransaction(@RequestParam("cuenta") Long numero_cuenta, @RequestParam("tipoTransaccion") int tipoTransaccion, @RequestParam("valorTransaccion") BigDecimal valorTransaccion, Model model) {
            Transaccion trans = new Transaccion();
            try {
                Cuenta cuenta = cuentaService.findByNumeroCuenta(numero_cuenta);

                if (cuenta != null && 'A' == cuenta.getEstado_cuenta()) {
                    trans.setCuenta(cuenta);
                } else {
                    model.addAttribute("error", "Cuenta no encontrada o inactiva");
                    return "error";
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
                        model.addAttribute("error", "El valor de la transacción excede el saldo de la cuenta");
                        return "error";
                    }

                } else {
                    model.addAttribute("error", "Tipo de transacción no encontrado");
                    return "error";      
                }

            } catch (NumberFormatException e) {
                model.addAttribute("error", "Error en el formato de los datos");
                return "error";
            } catch (Exception e) {
                model.addAttribute("error", "Error interno del servidor");
                return "error";
            }

            // Guardo la transacción
            Transaccion savedTrans = transaccionService.saveTransaccion(trans);
            model.addAttribute("transaccion", savedTrans);
            return "redirect:/";
        }

}
