package com.gimapp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "rol")
public class Rol {

	@Id //indicamos que es un id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //usamos una estrategia para auto-incrementar cada vez que hagamos un nuevo producto
	private Integer id;
	private String nombre;
	
	
	public Rol(Integer id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}
	
	public Rol(String nombre) {
		super();
		this.nombre = nombre;
	}

	public Rol() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
}
