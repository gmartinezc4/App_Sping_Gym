package com.gimapp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tarifas")
public class Tarifas {

	@Id //indicamos que es un id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer Id;
	private String nombreTarifa;
	private String appTarifa;
	private String precioTarifa;
	private String pecioInscripcion;
	
	
	public Tarifas(Integer id, String nombreTarifa, String appTarifa, String precioTarifa, String pecioInscripcion) {
		super();
		this.nombreTarifa = nombreTarifa;
		this.appTarifa = appTarifa;
		this.precioTarifa = precioTarifa;
		this.pecioInscripcion = pecioInscripcion;
	}
	
	public Tarifas() {
		
	}

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		this.Id = id;
	}

	public String getNombreTarifa() {
		return nombreTarifa;
	}

	public void setNombreTarifa(String nombreTarifa) {
		this.nombreTarifa = nombreTarifa;
	}

	public String getAppTarifa() {
		return appTarifa;
	}

	public void setAppTarifa(String appTarifa) {
		this.appTarifa = appTarifa;
	}

	public String getPrecioTarifa() {
		return precioTarifa;
	}

	public void setPrecioTarifa(String precioTarifa) {
		this.precioTarifa = precioTarifa;
	}

	public String getPecioInscripcion() {
		return pecioInscripcion;
	}

	public void setPecioInscripcion(String pecioInscripcion) {
		this.pecioInscripcion = pecioInscripcion;
	}

	@Override
	public String toString() {
		return "Tarifas [Id=" + Id + ", nombreTarifa=" + nombreTarifa + ", appTarifa=" + appTarifa + ", precioTarifa="
				+ precioTarifa + ", pecioInscripcion=" + pecioInscripcion + "]";
	}
	
	
}
