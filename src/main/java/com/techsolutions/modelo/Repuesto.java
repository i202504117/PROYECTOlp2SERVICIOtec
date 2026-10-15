package com.techsolutions.modelo;

import java.math.BigDecimal;
import jakarta.persistence.*;

@Entity
@Table(name = "tbl_repuesto")
public class Repuesto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_repuesto")
	private int idrepuesto;

	@Column(name = "nombre_repuesto")
	private String nombre;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_marca")
	private Marca marca;

	@Column(name = "stock_repuesto")
	private int stock;

	@Column(name = "precio_compra", precision = 10, scale = 2)
	private BigDecimal precioCompra;

	@Column(name = "precio_venta", precision = 10, scale = 2)
	private BigDecimal precioVenta;

	@ManyToOne
	@JoinColumn(name = "idproveedor")
	private Proveedor proveedor;

	public Repuesto() {
	}

	public Repuesto(int idrepuesto, String nombre, Marca marca, int stock, BigDecimal precioCompra,
			BigDecimal precioVenta, Proveedor proveedor) {
		this.idrepuesto = idrepuesto;
		this.nombre = nombre;
		this.marca = marca;
		this.stock = stock;
		this.precioCompra = precioCompra;
		this.precioVenta = precioVenta;
		this.proveedor = proveedor;
	}

	public int getIdrepuesto() {
		return idrepuesto;
	}

	public void setIdrepuesto(int idrepuesto) {
		this.idrepuesto = idrepuesto;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Marca getMarca() {
		return marca;
	}

	public void setMarca(Marca marca) {
		this.marca = marca;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public BigDecimal getPrecioCompra() {
		return precioCompra;
	}

	public void setPrecioCompra(BigDecimal precioCompra) {
		this.precioCompra = precioCompra;
	}

	public BigDecimal getPrecioVenta() {
		return precioVenta;
	}

	public void setPrecioVenta(BigDecimal precioVenta) {
		this.precioVenta = precioVenta;
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}
}