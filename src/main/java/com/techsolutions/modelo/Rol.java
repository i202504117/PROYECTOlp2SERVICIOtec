package com.techsolutions.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="tbl_rol")
public class Rol {
	@Id
	@Column(name="id_Rol")
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int idRol;
	@Column(name="nombre_Rol")
	private String nombre;
	
	public Rol() {
	}

	public Rol(int idRol, String nombre) {
		this.idRol = idRol;
		this.nombre = nombre;
	}

	public int getIdRol() {
		return idRol;
	}

	public void setIdRol(int idRol) {
		this.idRol = idRol;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	
	

}