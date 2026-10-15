package com.techsolutions.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_marca")
public class Marca {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_marca")
	private Integer id;

	@Column(name = "nombre_marca", nullable = false, unique = true, length = 100)
	private String nombre;

	public Marca() {
	}

	public Marca(Integer id, String nombre) {
		this.id = id;
		this.nombre = nombre;
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