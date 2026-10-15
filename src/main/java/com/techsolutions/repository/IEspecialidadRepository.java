package com.techsolutions.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techsolutions.modelo.Especialidad;

public interface IEspecialidadRepository extends JpaRepository<Especialidad, Integer> {
}