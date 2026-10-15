package com.techsolutions.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techsolutions.modelo.Equipo;

public interface IEquipoRepository extends JpaRepository<Equipo, Integer> {

    List<Equipo> findByClienteIdCliente(Integer idCliente);
}