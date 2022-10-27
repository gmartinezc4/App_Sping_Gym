package com.gimapp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="usuarios")
public class FichaUser {

	
	@Id //indicamos que es un id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //usamos una estrategia para auto-incrementar cada vez que hagamos un nuevo producto
	private Integer id;
	private String nombre;
	private String apellido;
	private String sexo;
	private String telefono;
	private String email;
	private String password;
	private String tarifa;
	
	
	public FichaUser(String nombre, String apellido, String sexo, String telefono, String email, String contraseña, String tarifa) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.sexo = sexo;
		this.telefono = telefono;
		this.email = email;
		this.password = contraseña;
		this.tarifa = tarifa;
	}

	public FichaUser() {
		
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

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String contraseña) {
		this.password = contraseña;
	}
	
	public String getTarifa() {
		return tarifa;
	}

	public void setTarifa(String tarifa) {
		this.tarifa = tarifa;
	}

	@Override
	public String toString() {
		return "FichaUser [nombre=" + nombre + ", apellido=" + apellido + ", sexo=" + sexo + ", telefono=" + telefono
				+ ", email=" + email + "]";
	}
	
	
}
