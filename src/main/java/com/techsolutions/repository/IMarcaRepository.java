package com.techsolutions.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techsolutions.modelo.Marca;

public interface IMarcaRepository extends JpaRepository<Marca, Integer> {
}