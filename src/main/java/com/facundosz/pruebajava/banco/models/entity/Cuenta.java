package com.facundosz.pruebajava.banco.models.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Cuentas")
public class Cuenta {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(length = 10)
  private Long numeroCuenta; 

  @Column(length = 50)
  @Size(max = 50)
  private String nombre_cuenta; 

  @Digits(integer = 10, fraction = 2)
  private BigDecimal monto_apertura;

  @Temporal(TemporalType.DATE)
  private Date fecha_apertura;

  @Digits(integer = 10, fraction = 2)
  private BigDecimal saldo; 

  @Column(length = 1)
  private char estado_cuenta;

  @ManyToOne(optional = false) 
  @JoinColumn(name = "COD_CLIENTE") 
  @JsonBackReference
  private Cliente cliente;
  
  @OneToMany(mappedBy = "id_transaccion")
  private List<Transaccion> transacciones;

public Cliente getCliente() {
    return cliente;
}
public void setCliente(Cliente cliente) {
    this.cliente = cliente;
}
public Long getNumero_cuenta() {
    return numeroCuenta;
}
public String getNombre_cuenta() {
    return nombre_cuenta;
}
public void setNombre_cuenta(String nombre_cuenta) {
    this.nombre_cuenta = nombre_cuenta;
}
public BigDecimal getMonto_apertura() {
    return monto_apertura;
}
public void setMonto_apertura(BigDecimal monto_apertura) {
    this.monto_apertura = monto_apertura;
}
public Date getFecha_apertura() {
    return fecha_apertura;
}
public void setFecha_apertura(Date fecha_apertura) {
    this.fecha_apertura = fecha_apertura;
}
public BigDecimal getSaldo() {
    return saldo;
}
public void setSaldo(BigDecimal saldo) {
    this.saldo = saldo;
}
public char getEstado_cuenta() {
    return estado_cuenta;
}
public void setEstado_cuenta(char estado_cuenta) {
    this.estado_cuenta = estado_cuenta;
}
public Cuenta orElseThrow(Object object) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'orElseThrow'");
} 

  
}
