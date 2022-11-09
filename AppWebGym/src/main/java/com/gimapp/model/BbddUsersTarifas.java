package com.gimapp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TarifasGym_Usuarios")
public class BbddUsersTarifas {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Integer idTarifa;
	private Integer idUser;
	
	public BbddUsersTarifas(Integer idTarifa, Integer idUser) {
		super();
		this.idTarifa = idTarifa;
		this.idUser = idUser;
	}
	
	public BbddUsersTarifas() {
		super();
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdTarifa() {
		return idTarifa;
	}
	
	public void setIdTarifa(Integer idTarifa) {
		this.idTarifa = idTarifa;
	}
	
	public Integer getIdUser() {
		return idUser;
	}
	
	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}
	
	
}
