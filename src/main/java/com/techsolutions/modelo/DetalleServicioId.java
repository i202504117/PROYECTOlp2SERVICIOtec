package com.techsolutions.modelo;

import java.io.Serializable;
import java.util.Objects;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class DetalleServicioId implements Serializable {

    @Column(name = "id_servicio")
    private Integer idServicio;

    @Column(name = "id_repuesto")
    private Integer idRepuesto;

    public DetalleServicioId() {}

    public DetalleServicioId(Integer idServicio, Integer idRepuesto) {
        this.idServicio = idServicio;
        this.idRepuesto = idRepuesto;
    }

    public Integer getIdServicio() { return idServicio; }
    public void setIdServicio(Integer idServicio) { this.idServicio = idServicio; }
    public Integer getIdRepuesto() { return idRepuesto; }
    public void setIdRepuesto(Integer idRepuesto) { this.idRepuesto = idRepuesto; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DetalleServicioId)) return false;
        DetalleServicioId that = (DetalleServicioId) o;
        return Objects.equals(idServicio, that.idServicio)
                && Objects.equals(idRepuesto, that.idRepuesto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idServicio, idRepuesto);
    }
}