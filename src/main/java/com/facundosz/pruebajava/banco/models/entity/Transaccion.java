package com.facundosz.pruebajava.banco.models.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;

@Entity
@Table(name = "Transacciones")
public class Transaccion {

  @Id
  @GeneratedValue (strategy = GenerationType.IDENTITY)  
  private int id_transaccion ;

  @Digits(integer = 10, fraction = 2)
  private double valor_monteario; 
  private Date fecha_transaccion; 

  @ManyToOne (optional = false) 
  @JoinColumn (name = "numero_cuenta") 
  private Cuenta cuenta;
  
  @ManyToOne (optional = false)
  @JoinColumn (name = "Id_tipo_transaccion") // Asegúrate de que el nombre de la columna coincida con el de tu base de datos
  private TipoTransaccion tipoTransaccion;
  
public int getId_transaccion() {
    return id_transaccion;
}
public void setId_transaccion(int id_transaccion) {
    this.id_transaccion = id_transaccion;
}
public double getValor_monteario() {
    return valor_monteario;
}
public void setValor_monteario(double valor_monteario) {
    this.valor_monteario = valor_monteario;
}
public Date getFecha_transaccion() {
    return fecha_transaccion;
}
public void setFecha_transaccion(Date fecha_transaccion) {
    this.fecha_transaccion = fecha_transaccion;
}
public Cuenta getNumero_cuenta() {
    return cuenta;
}
public void setNumero_cuenta(Cuenta numero_cuenta) {
    this.cuenta = numero_cuenta;
}
 
  
}
