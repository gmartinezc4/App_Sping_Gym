package com.gimapp.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ClasesGym")
public class ClasesGym {

	@Id //indicamos que es un id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nombre;
	private String dias;
	private String hora;
	private String material;
	
	public ClasesGym(Integer id, String nombre, String dias, String hora, String material) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.dias = dias;
		this.hora = hora;
		this.material = material;
	}

	public ClasesGym(String nombre, String dias, String hora, String material) {
		super();
		this.nombre = nombre;
		this.dias = dias;
		this.hora = hora;
		this.material = material;
	}

	public ClasesGym() {
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

	public String getDias() {
		return dias;
	}

	public void setDias(String dias) {
		this.dias = dias;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

}
