package com.techsolutions.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_estado_servicio")
public class EstadoServicio {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_estado")
	private Integer id;

	@Column(name = "nombre_estado", nullable = false, unique = true, length = 50)
	private String nombre;

	public EstadoServicio() {
	}

	public EstadoServicio(Integer id, String nombre) {
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