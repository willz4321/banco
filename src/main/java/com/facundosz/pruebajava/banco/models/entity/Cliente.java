package com.facundosz.pruebajava.banco.models.entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Clientes")
public class Cliente {

  @Id
  @Column (name = "COD_CLIENTE")
  @GeneratedValue (strategy = GenerationType.IDENTITY)
  private int cod_cliente;

  @Column(length = 50)
  @Size(max = 50)
  private String nombre;

  @Column(length = 50)
  @Size(max = 50) 
  private String apellidos; 

  @Column(length = 50)
  @Size(max = 50)
  private String dui; 

  @Temporal(TemporalType.DATE)
  private Date fecha_registro;

  @OneToMany (mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonManagedReference
  private List<Cuenta> cuentas;

public String getNombre() {
    return nombre;
}
public void setNombre(String nombre) {
    this.nombre = nombre;
}
public String getApellidos() {
    return apellidos;
}
public void setApellidos(String apellidos) {
    this.apellidos = apellidos;
}
public String getDui() {
    return dui;
}
public void setDui(String dui) {
    this.dui = dui;
}
public Date getFecha_registro() {
    return fecha_registro;
}
public void setFecha_registro(Date fecha_registro) {
    this.fecha_registro = fecha_registro;
}

public void setCuentas(List<Cuenta> cuentas) {
    this.cuentas = cuentas;
}
public int getCod_cliente() {
    return cod_cliente;
}
public List<Cuenta> getCuentas() {
    return cuentas;
}
  
}
