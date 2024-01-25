package com.facundosz.pruebajava.banco.models.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Tipo_de_transacciones")
public class TipoTransaccion {

  @Id
  @GeneratedValue (strategy = GenerationType.IDENTITY)
  private int Id_tipo_transaccion;
  
  @Enumerated(EnumType.STRING)
  private Tipo nombre;
  
  @OneToMany (mappedBy = "tipoTransaccion")
  private List<Transaccion> transacciones;

public int getId_tipo_transaccion() {
    return Id_tipo_transaccion;
}
public void setId_tipo_transaccion(int id_tipo_transaccion) {
    Id_tipo_transaccion = id_tipo_transaccion;
}
public Tipo getNombre_transaccion() {
    return nombre;
}
public void setNombre_transaccion(Tipo nombre) {
    this.nombre = nombre;
}

  
}
