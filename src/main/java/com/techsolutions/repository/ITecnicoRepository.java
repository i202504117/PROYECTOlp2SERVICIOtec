package com.techsolutions.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techsolutions.modelo.Tecnico;

public interface ITecnicoRepository extends JpaRepository<Tecnico, Integer> {

    List<Tecnico> findByNombreContainingIgnoreCase(String nombre);

    Tecnico findFirstByNombre(String nombre);
}