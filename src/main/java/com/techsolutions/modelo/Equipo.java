package com.techsolutions.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_equipo")
public class Equipo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_equipo")
	private Integer id;

	@Column(name = "tipo_equipo", nullable = false, length = 80)
	private String tipo;

	@Column(name = "modelo_equipo", length = 100)
	private String modelo;

	@Column(name = "serie_equipo", length = 100)
	private String serie;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_marca")
	private Marca marca;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_cliente", nullable = false)
	private Cliente cliente;

	public Equipo() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public Marca getMarca() {
		return marca;
	}

	public void setMarca(Marca marca) {
		this.marca = marca;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
}