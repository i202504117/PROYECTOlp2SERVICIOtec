package com.techsolutions.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "tbl_cliente")
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_cliente")
	private Integer idCliente;

	@NotBlank(message = "El DNI no puede estar vacío")
	@Size(min = 8, max = 8, message = "El DNI debe tener exactamente 8 caracteres")
	@Column(name = "dni", nullable = false, unique = true, length = 8)
	private String dni;

	@NotBlank(message = "El nombre no puede estar vacío")
	@Column(name = "nombre", nullable = false, length = 100)
	private String nombre;

	@NotBlank(message = "El apellido no puede estar vacío")
	@Column(name = "apellido", nullable = false, length = 100)
	private String apellido;

	@Column(name = "telefono", length = 15)
	private String telefono;

	@Column(name = "direccion", length = 200)
	private String direccion;


	@Column(name = "activo", nullable = false)
	private Boolean activo = true;

	@OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
	private List<OrdenServicio> ordenesServicio;

	public Cliente() {
	}

	public Cliente(Integer idCliente, String dni, String nombre, String apellido, String telefono, String direccion, Boolean activo) {
		this.idCliente = idCliente;
		this.dni = dni;
		this.nombre = nombre;
		this.apellido = apellido;
		this.telefono = telefono;
		this.direccion = direccion;
		this.activo = activo;
	}

	public Integer getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
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

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public List<OrdenServicio> getOrdenesServicio() {
		return ordenesServicio;
	}

	public void setOrdenesServicio(List<OrdenServicio> ordenesServicio) {
		this.ordenesServicio = ordenesServicio;
	}
}