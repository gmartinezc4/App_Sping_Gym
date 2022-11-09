package com.gimapp.model;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="usuarios", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
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
	private Integer idDatosBancarios;
	
//	@ManyToMany(fetch  = FetchType.EAGER, cascade = CascadeType.ALL)
//	@JoinTable(name = "usuarios_roles", 
//				joinColumns = @JoinColumn(name = "usuario_id", referencedColumnName = "id"),
//				inverseJoinColumns = @JoinColumn(name = "rol_id", referencedColumnName = "id")
//			)
//	private Collection<Rol> roles;
//	

	
	
	public FichaUser(Integer id, String nombre, String apellido, String sexo, String telefono, String email,
			String password, String tarifa, Integer idDatosBancarios) { //Collection<Rol> roles
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.sexo = sexo;
		this.telefono = telefono;
		this.email = email;
		this.password = password;
		this.tarifa = tarifa;
		this.idDatosBancarios = idDatosBancarios;
	}

	public FichaUser(String nombre, String apellido, String sexo, String telefono, String email, 
			String contraseña, String tarifa, Integer idDatosBancarios) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.sexo = sexo;
		this.telefono = telefono;
		this.email = email;
		this.password = contraseña;
		this.tarifa = tarifa;
		this.idDatosBancarios = idDatosBancarios;
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

	public Integer getIdDatosBancarios() {
		return idDatosBancarios;
	}

	public void setIdDatosBancarios(Integer idDatosBancarios) {
		this.idDatosBancarios = idDatosBancarios;
	}

	@Override
	public String toString() {
		return "FichaUser [nombre=" + nombre + ", apellido=" + apellido + ", sexo=" + sexo + ", telefono=" + telefono
				+ ", email=" + email + "]";
	}
	
	
}
