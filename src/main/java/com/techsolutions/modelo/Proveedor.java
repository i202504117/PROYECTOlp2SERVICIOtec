package com.techsolutions.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_proveedor")
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Proveedor")
    private Integer idproveedor;

    @Column(name = "razonsocial_Proveedor")
    private String razonsocial;

    @Column(name = "telefono_Proveedor")
    private String telefono;

    public Proveedor() {
    }

    public Proveedor(Integer idproveedor, String razonsocial, String telefono) {
        this.idproveedor = idproveedor;
        this.razonsocial = razonsocial;
        this.telefono = telefono;
    }

    public Integer getIdproveedor() {
        return idproveedor;
    }

    public void setIdproveedor(Integer idproveedor) {
        this.idproveedor = idproveedor;
    }

    public String getRazonsocial() {
        return razonsocial;
    }

    public void setRazonsocial(String razonsocial) {
        this.razonsocial = razonsocial;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}