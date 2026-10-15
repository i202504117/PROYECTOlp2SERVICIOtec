package com.techsolutions.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_tecnico")
public class Tecnico {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_Tecnico")
	private int idtecnico;

	@Column(name = "nombre_Tecnico", nullable = false)
	private String nombre;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_especialidad")
	private Especialidad especialidad;

	@Column(name = "telefono_Tecnico", length = 15)
	private String telefono;

	public Tecnico() {
	}

	public Tecnico(int idtecnico, String nombre, Especialidad especialidad, String telefono) {
		this.idtecnico = idtecnico;
		this.nombre = nombre;
		this.especialidad = especialidad;
		this.telefono = telefono;
	}

	public int getIdtecnico() {
		return idtecnico;
	}

	public void setIdtecnico(int idtecnico) {
		this.idtecnico = idtecnico;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Especialidad getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(Especialidad especialidad) {
		this.especialidad = especialidad;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
}