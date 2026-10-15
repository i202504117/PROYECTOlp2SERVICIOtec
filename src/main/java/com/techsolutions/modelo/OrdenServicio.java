package com.techsolutions.modelo;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "tbl_orden_servicio")
public class OrdenServicio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_servicio")
	private Integer idorden;

	@NotNull(message = "La fecha del servicio no puede ser nula")
	@Column(name = "fecha_servicio", nullable = false)
	private Date fecha;

	@NotBlank(message = "El problema reportado no puede estar vacío")
	@Column(name = "problema_servicio", nullable = false, length = 255)
	private String problema;

	@NotNull(message = "El costo de mano de obra no puede ser nulo")
	@PositiveOrZero(message = "El costo debe ser mayor o igual a cero")
	@Column(name = "costo_mano_obra", nullable = false, precision = 10, scale = 2)
	private BigDecimal costoManoObra;

	@NotNull(message = "La orden debe estar asociada a un cliente")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_cliente", nullable = false)
	private Cliente cliente;

	@NotNull(message = "La orden debe tener un técnico asignado")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_tecnico", nullable = false)
	private Tecnico tecnico;

	@NotNull(message = "La orden debe tener un equipo")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_equipo", nullable = false)
	private Equipo equipo;

	@NotNull(message = "La orden debe tener un estado")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_estado", nullable = false)
	private EstadoServicio estado;

	@OneToMany(mappedBy = "orden", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<DetalleServicio> detalles = new ArrayList<>();

	public OrdenServicio() {
	}

	// Helper para mantener ambos lados sincronizados
	public void agregarDetalle(Repuesto repuesto, int cantidad, BigDecimal precioUnitario) {
		DetalleServicio d = new DetalleServicio(this, repuesto, cantidad, precioUnitario);
		detalles.add(d);
	}

	// Total calculado (no se guarda en BD)
	public BigDecimal getTotal() {
		BigDecimal repuestos = detalles.stream()
				.map(d -> d.getPrecioUnitario().multiply(BigDecimal.valueOf(d.getCantidad())))
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		return costoManoObra.add(repuestos);
	}

	public Integer getIdorden() {
		return idorden;
	}

	public void setIdorden(Integer idorden) {
		this.idorden = idorden;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getProblema() {
		return problema;
	}

	public void setProblema(String problema) {
		this.problema = problema;
	}

	public BigDecimal getCostoManoObra() {
		return costoManoObra;
	}

	public void setCostoManoObra(BigDecimal costoManoObra) {
		this.costoManoObra = costoManoObra;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Tecnico getTecnico() {
		return tecnico;
	}

	public void setTecnico(Tecnico tecnico) {
		this.tecnico = tecnico;
	}

	public Equipo getEquipo() {
		return equipo;
	}

	public void setEquipo(Equipo equipo) {
		this.equipo = equipo;
	}

	public EstadoServicio getEstado() {
		return estado;
	}

	public void setEstado(EstadoServicio estado) {
		this.estado = estado;
	}

	public List<DetalleServicio> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<DetalleServicio> detalles) {
		this.detalles = detalles;
	}
}