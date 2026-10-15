package com.techsolutions.modelo;

import java.math.BigDecimal;
import jakarta.persistence.*;

@Entity
@Table(name = "tbl_detalle_servicio")
public class DetalleServicio {

    @EmbeddedId
    private DetalleServicioId id = new DetalleServicioId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idServicio")
    @JoinColumn(name = "id_servicio")
    private OrdenServicio orden;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idRepuesto")
    @JoinColumn(name = "id_repuesto")
    private Repuesto repuesto;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "precio_unitario", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioUnitario;

    public DetalleServicio() {}

    public DetalleServicio(OrdenServicio orden, Repuesto repuesto, Integer cantidad, BigDecimal precioUnitario) {
        this.orden = orden;
        this.repuesto = repuesto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    public DetalleServicioId getId() { return id; }
    public void setId(DetalleServicioId id) { this.id = id; }
    public OrdenServicio getOrden() { return orden; }
    public void setOrden(OrdenServicio orden) { this.orden = orden; }
    public Repuesto getRepuesto() { return repuesto; }
    public void setRepuesto(Repuesto repuesto) { this.repuesto = repuesto; }
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    public BigDecimal getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(BigDecimal precioUnitario) { this.precioUnitario = precioUnitario; }
}