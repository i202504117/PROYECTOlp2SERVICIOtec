package com.techsolutions.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techsolutions.modelo.EstadoServicio;

public interface IEstadoServicioRepository extends JpaRepository<EstadoServicio, Integer> {
    Optional<EstadoServicio> findByNombre(String nombre);
}