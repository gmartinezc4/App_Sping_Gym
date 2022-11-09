package com.gimapp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ClasesGym_Usuarios")
public class BbddUsersClases {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Integer idClase;
	private Integer idUser;
	
	public BbddUsersClases(Integer idClase, Integer idUser) {
		super();
		this.idClase = idClase;
		this.idUser = idUser;
	}
	
	public BbddUsersClases() {
		super();
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdClase() {
		return idClase;
	}
	
	public void setIdClase(Integer idClase) {
		this.idClase = idClase;
	}
	
	public Integer getIdUser() {
		return idUser;
	}
	
	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}
	
	
}
